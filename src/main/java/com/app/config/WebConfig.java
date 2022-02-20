package com.app.config;

import com.app.config.converter.IdToEntityConverter;
import com.app.config.converter.imp.IdToActorConverter;
import com.app.config.converter.imp.IdToDirectorConverter;
import com.app.config.converter.imp.IdToFilmConverter;
import com.app.config.converter.imp.IdToGenreConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.app" })
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    IdToFilmConverter idToFilmConverter;
    @Autowired
    IdToActorConverter idToActorConverter;
    @Autowired
    IdToDirectorConverter idToDirectorConverter;
    @Autowired
    IdToGenreConverter idToGenreConverter;


    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");

        return bean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/films").setViewName("films");
        registry.addViewController("/films/handle").setViewName("film-handle");
        registry.addViewController("/actors/handle").setViewName("actor-handle");
        registry.addViewController("/directors/handle").setViewName("director-handle");
        registry.addViewController("/genres/handle").setViewName("genre-handle");
        registry.addViewController("/genres").setViewName("genres");
        registry.addViewController("/directors").setViewName("directors");
        registry.addViewController("/serialize").setViewName("serialize");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCacheControl(CacheControl.noCache());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idToFilmConverter);
        registry.addConverter(idToActorConverter);
        registry.addConverter(idToDirectorConverter);
        registry.addConverter(idToGenreConverter);
    }
}