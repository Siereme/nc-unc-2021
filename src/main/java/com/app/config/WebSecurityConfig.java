package com.app.config;

import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    @Autowired
    UserRepository repository;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                // only for unauthorized
                .antMatchers("/registration").not().fullyAuthenticated()
                // only for admin
                .antMatchers("/admin/**").hasRole("ADMIN")
                // for all users
                .antMatchers("/films/**").permitAll()
                .antMatchers("/", "/resources/**").permitAll()
                //
                .anyRequest().authenticated()
                .and()
                // log in
                .formLogin()
                .loginPage("/login")
                // to main page after successful log in
                .defaultSuccessUrl("/films/all", true)
//                .defaultSuccessUrl("/serialize/", true)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/films");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(repository).passwordEncoder(bCryptPasswordEncoder());
    }
}