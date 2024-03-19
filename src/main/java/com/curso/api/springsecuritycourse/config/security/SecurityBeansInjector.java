package com.curso.api.springsecuritycourse.config.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.curso.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.curso.api.springsecuritycourse.persistence.repository.UserRepository;

@Configuration
public class SecurityBeansInjector {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Este metodo elige la estrategia de autenticacion(En este caso la estrategia es la de autenticacion por base de datos [usuario y contraseña])
    //Se crea una instancia de DaoAuthenticationProvider y se le setea el password codificado y el user details service (Para que busque el usuario en la base de datos y pueda comparar las contraseñas)
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService());
        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return (username) ->{
            return userRepository.findByUsername(username)
                .orElseThrow(()-> new ObjectNotFoundException("User not found with username: "+username));
        };
    }

}
