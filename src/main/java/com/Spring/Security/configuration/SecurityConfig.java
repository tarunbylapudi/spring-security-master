package com.Spring.Security.configuration;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.Spring.Security.filter.JWTTokenGeneratorFilter;
import com.Spring.Security.filter.JWTTokenValidatorFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService detailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config=new CorsConfiguration();
				config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setExposedHeaders(Arrays.asList("Authorization"));
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		});
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable().addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);
		http.authorizeRequests((requests) -> requests.antMatchers("/account").hasAnyRole("ADMIN","USER").antMatchers("/contact").authenticated().antMatchers("/").authenticated().antMatchers("/h2-console/**").permitAll());
		http.formLogin();
		http.httpBasic();
		http.headers().frameOptions().sameOrigin();
		//.ignoringAntMatchers("/h2-console/**").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		InMemoryUserDetailsManager userdetailsService =new InMemoryUserDetailsManager();
//		
//		UserDetails user= User.withUsername("user").password("user").authorities("user").build();
//		UserDetails user1=User.withUsername("admin").password("admin").authorities("admin").build();
//		
//		userdetailsService.createUser(user);
//		userdetailsService.createUser(user1);
//		
//		auth.userDetailsService(userdetailsService);
//		
//	}

	
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
