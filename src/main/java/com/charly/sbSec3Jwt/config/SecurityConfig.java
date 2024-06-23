package com.charly.sbSec3Jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity 
@EnableMethodSecurity					
@RequiredArgsConstructor
public class SecurityConfig { 
							 
	
	@Autowired
	private final JwtFilter jwtFilter;
	
	@Autowired
	private final AuthenticationProvider authenticationProvider; 
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{ 

		
		httpSecurity.csrf(csrf ->  csrf.disable())
					.authorizeHttpRequests(auth -> auth.requestMatchers(publicEndpoints())
													    	.permitAll()
													    	.anyRequest()
													    	.authenticated()
													    	)
					.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationProvider(authenticationProvider)
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build(); 
	}
	
	private RequestMatcher publicEndpoints() {
		return new OrRequestMatcher ( new AntPathRequestMatcher("/api/saludos/saludarPublico"),
									  new AntPathRequestMatcher("/api/auth/register"), 
									  new AntPathRequestMatcher("/api/auth/authenticate")  
									  );															  
	}				

}
