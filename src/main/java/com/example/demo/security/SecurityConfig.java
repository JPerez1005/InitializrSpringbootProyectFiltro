package com.example.demo.security;


import com.example.demo.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author perez
 */

@Configuration//Definimos esta clase como una configuracion
@EnableWebSecurity// habilita la seguridad web en la aplicación
public class SecurityConfig {

    //traemos los datos detalles del usuario
    @Autowired
    private CustomerDetailsService customerDetailsService;

    //traemos las autenticaciones y autorizaciones
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }//es un codificador de contraseñas
    
    /*SecurityFilterChain es un metodo de configuracion de Bean
    y el contenedeor de spring debe ocuparse de su creacion y configuracion
    HttpSecurity: es el parametro que recibe SecurityFilterChain que es esencial
    para configurar la seguridad, porque proporciona metodos para configurar 
    diversos aspectos de la seguridad de spring security*/
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        /*la linea 57 configura la politica CORS para permitir solicitudes
            desde cualquier origen, dentro se permite todos los metodos HTTP y
            encabezados debido a que applyPermitDefaultValues es una configuracion
            Predeterminada*/
        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                /*el metodo and es para encadenar o enlazar diferentes
                    configuraciones*/
                .and()
                /*se deshabilita la proteccion csrf lo que significa que no
                    requiere un token csrf en las solicitudes*/
                .csrf().disable()
                /*authorizeHttpRequests: se empezará a configurar las reglas
                    de autorización.*/
                .authorizeHttpRequests()
                /*requestMatchers especifica rutas*/
                .requestMatchers("/user/agregar_usuario", "/user/ingresar", "/persona/forgotPassword","/login","/swagger-ui/index.html")
                /*permitAll indica que las rutas mencionadas anteriormente no
                necesitan la autenticación*/
                .permitAll()
                /*indica que cualquier otra solicitud que no coincida con las rutas
                del requestMatchers si necesitan una autenticación*/
                .anyRequest().authenticated()
                .and()
                /*permite configurar como manejar las exceptiones relacionadas*/
                .exceptionHandling()
                .and().sessionManagement()
                /*Esto establece la política de gestión de sesiones en "sin estado"
                lo que significa que Spring Security no creará ni gestionará
                sesiones para las solicitudes.*/
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /*agregamos el filtro personalizado (jwtFilter) antes del filtro estandar*/
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();//devuelve el objeto SecurityFilterChain configurado
    }
    
    /*metodo esencial para manejar la autenticación en springsecurity, se puede inyectar
    en cualquier otra clas donde sea necesaria la gestion de la autenticación*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
