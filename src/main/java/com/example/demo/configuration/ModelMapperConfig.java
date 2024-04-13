package com.example.demo.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureModelMapper(modelMapper);
        return modelMapper;
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.addConverter(new Converter<Date, String>() {
            @Override
            public String convert(MappingContext<Date, String> context) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return context.getSource() == null ? null : dateFormat.format(context.getSource());
            }
        });
        modelMapper.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(MappingContext<String, Date> context) {
                try {
                    return context.getSource() == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(context.getSource());
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }
}