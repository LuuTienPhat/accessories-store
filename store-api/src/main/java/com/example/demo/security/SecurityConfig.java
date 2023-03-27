package com.example.demo.security;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Constants;
import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder btBCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(btBCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CustomAuthenticationFilter customAuthenticationFilter = 
				new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/users/login");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/users/login/**", "/users/token/refresh/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority(Constants.ROLE_MANAGER, Constants.ROLE_CUSTOMER);
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/users/**").hasAnyAuthority(Constants.ROLE_MANAGER, Constants.ROLE_CUSTOMER);
//		http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/save/**").hasAnyAuthority(Constants.ROLE_MANAGER);
		http.authorizeRequests().antMatchers("/users/**").hasAnyAuthority(Constants.ROLE_MANAGER);
		
		//http.authorizeRequests().antMatchers(HttpMethod.GET, "/enwords/**").permitAll();
		//http.authorizeRequests().antMatchers(HttpMethod.GET, "/enwords/simplified/**").permitAll();
		//http.authorizeRequests().antMatchers("/enwords/**").hasAnyAuthority("ROLE_ADMIN_WORD", Constants.ROLE_MANAGER);
		//http.authorizeRequests().antMatchers("/partofspeeches/**").hasAnyAuthority("ROLE_ADMIN_WORD", Constants.ROLE_MANAGER);
		//http.authorizeRequests().antMatchers("/meanings/**").hasAnyAuthority("ROLE_ADMIN_WORD", Constants.ROLE_MANAGER);
		//http.authorizeRequests().antMatchers("/examples/**").hasAnyAuthority("ROLE_ADMIN_WORD", Constants.ROLE_MANAGER);
		
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/orders").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER, Constants.ROLE_CUSTOMER);
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/orders/**").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER, Constants.ROLE_CUSTOMER);
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/products").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/products/**").permitAll();		
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/carts/**").permitAll();	
		//http.authorizeRequests().antMatchers(HttpMethod.POST, "/carts/**").permitAll();	
		//http.authorizeRequests().antMatchers(HttpMethod.GET,"/products").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER, Constants.ROLE_CUSTOMER);
		//http.authorizeRequests().antMatchers(HttpMethod.GET,"/products/**").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER, Constants.ROLE_CUSTOMER);
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/categories/**").permitAll();
		http.authorizeRequests().antMatchers("/categories/**").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER);
		http.authorizeRequests().antMatchers("/orders/**").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER);
		http.authorizeRequests().antMatchers("/products/**").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER);
		http.authorizeRequests().antMatchers("/invoices/**").hasAnyAuthority(Constants.ROLE_SALER, Constants.ROLE_MANAGER);
		http.authorizeRequests().antMatchers("/uploads/**").permitAll();
		
		http.authorizeRequests().antMatchers("/feedbacks").permitAll();
		
		http.authorizeRequests().anyRequest().permitAll();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
