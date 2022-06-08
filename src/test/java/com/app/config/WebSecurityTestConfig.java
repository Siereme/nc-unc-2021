package com.app.config;

import com.app.model.role.Role;
//import com.app.model.user.User;
import com.app.model.user.User;
import com.app.repository.UserRepository;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@TestConfiguration
public class WebSecurityTestConfig {


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder)
//                .withUser("spring")
//                .password(encoder.encode("secret"))
//                .roles("ADMIN");
//    }


//    @Autowired
//    UserRepository userRepository;
//
//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService() {
////        Role roleAdmin = new Role();
////        User basicUser = new User("admin1", "admin1", "password");
////        UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
////                new SimpleGrantedAuthority("ROLE_USER"),
////                new SimpleGrantedAuthority("PERM_FOO_READ")
////        ));
//
////        User admin = new User("admin1", "admin1", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//
//        User admin = userRepository.findById(2);
//        admin.setEmail("admin1@mail.com");
//        return new InMemoryUserDetailsManager(Collections.singletonList(
//                admin
//        ));
//    }
}
