package com.gkemayo.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig  {

	private static final String[] SECURED_URLs = {"/rest/book/api/**"};
	private static final String[] UN_SECURED_URLs = {
            "/rest/category/api/**",
            "/rest/customer/api/**"
    };

	
	
	@Bean
	public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers(UN_SECURED_URLs).permitAll()
            .requestMatchers(SECURED_URLs).hasRole("ADMIN")
            .anyRequest().authenticated()
        );
        return http.build();
		
		
    

    }

}
