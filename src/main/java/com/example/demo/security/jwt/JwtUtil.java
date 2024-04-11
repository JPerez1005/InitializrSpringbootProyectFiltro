package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author perez
 * 
 * Esta clase sirve para crear el token, firmarlo
 * extraer el usuario del token, la fecha de expiracion
 * y ver los claims yhacer la validacion del mismo token
 */

@Service
public class JwtUtil {
    
    //quien conozca esta clave pueda crear y validar tokens válidos.
    private String secret="springboot";
    
    /*===================Metodo Generico===================*/
    //El tipo generico T puede manejar distintos tipos de datos
    //Token: el token JWT del que se extraen los reclamos o los claims
    //ClaimsResolver: Puede tomar cualquier objeto tipo claims(reclamos del token)
    //y los convierta a tipo T
    public <T> T extractClaims(String token,Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token)/*Extraemos todos los reclamos*/;
        /*Ahora tomamos esos reclamos y ahora los convertimos a tipo T y retornamos*/
        return claimsResolver.apply(claims);/*retirnamos en T variable generica*/
    }
    
    /*Con ese token se extrae el nombre del usuario retornamos en String pero
    la funcion extractClaims devuelve un generico*/
    public String extractUsername(String token){
        //Claims::getSubject= lo toma como referencia que queremos extraer
        //el sujeto o nombre de usuario
        return extractClaims(token,Claims::getSubject);
    }
    
    /*es muy parecida a la funcion anterior pero aqui extraemos el tiempo de expiracion
    del token con Claims::getExpiration*/
    public Date extractExpiration(String token){
        //Fecha en la que el token expira
        return extractClaims(token,Claims::getExpiration);
    }
    
    /*Tener toda la informacion de ese token ya no solo ni el nombre ni fecha
    toda la informacion relacionada la extraemos, recibimos el parametro token*/
    public Claims extractAllClaims(String token){
        //Jwts.parser(): analiza y verifica tokens
        //.setSigningKey(secret): con esto establecemos la clave para verificar la firma del token
        //.parseClaimsJws(token): analiza el token jwt y devuelve un objeto Jws<Claims>
        //Jws<Claims>= eso es lo que representa el token analizado y verificado
        //.getBody(): devuelve los reclamos del token como objeto 'Claims'
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        //es decir devolvemos un objeto de clave-valor
    }
    
    //validamos a partir de la expiracion del token
    private Boolean isTokenExpired(String token){
        /*before se usa para comparar la informacion extraida = extractExpiration(token)
        que devuelve una fecha para compararla con la fecha actual*/
        return extractExpiration(token).before(new Date());
        //es decir para verificar si el token ah expirado o no
    }
    
    /*Aquí recibimos los reclamos junto con el subject que generalmente representa
    al usuario con un numero de id o algo así, y apartir de los reclamos creamos
    el token*/
    public String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder()/*construimos el token con los reclamos y sujeto proporcionado*/
                .setClaims(claims)//estamos mandando los reclamos al builder
                .setSubject(subject)//estamos mandando el sujeto al builder
                .setIssuedAt(new Date(System.currentTimeMillis()))/*Fecha de emision*/
                .setExpiration(new Date(System.currentTimeMillis()+100*60*60*10))/*fecha de expiración*/
                .signWith(SignatureAlgorithm.HS256, secret)/*firmamos el token*/
                .compact();/*compactamos el token en forma de cadena para devolverlo*/
    }
    
    public String generateToken(String username,String role){
        Map<String,Object> claims=new HashMap<>();//creamos el objeto reclamos
        claims.put("role",role);//colocamos datos en ese reclamo
        /*mandamos esos datos a que los conviertan en un token*/
        return createToken(claims,username);
    }
    
    //Validamos el token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);//extraemos el nombre de usuario
        //validamos si el token no esta expirado y si los datos de usuario del token
        //concuerdan con los datos del detalle de usuario
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        //si se logran estas dos partes entonces el token ya está validado y arroje true
    }
    
}
