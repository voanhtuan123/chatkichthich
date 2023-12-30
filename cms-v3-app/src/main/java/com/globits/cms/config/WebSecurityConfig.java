package com.globits.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.globits.security.CustomUserDetailsService;
import com.globits.security.basicauth.AppBasicAuthenticationEntryPoint;
import com.globits.security.jwt.AuthEntryPointJwt;
import com.globits.security.jwt.AuthTokenFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;
import java.util.ArrayList;
//import com.globits.spring.security.jwt.security.jwt.AuthEntryPointJwt;
//import com.globits.spring.security.jwt.security.jwt.AuthTokenFilter;
//import com.globits.spring.security.jwt.security.services.UserDetailsServiceImpl;
import java.util.Arrays;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
@ComponentScan(basePackages = { "com.globits.core.rest", "com.globits.security.rest",
		"com.globits.cms.rest"})
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomUserDetailsService userDetailsService;
	@Autowired
	private Environment env;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Autowired
	private AppBasicAuthenticationEntryPoint authenticationEntryPoint;
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
	
	@Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
	
	@Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
//			.antMatchers("/api/test/**").permitAll()
//			.anyRequest().authenticated();
//
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
	
	@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        //.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/public/**").permitAll()
        .antMatchers("/oauth/**").permitAll()
        .antMatchers("/api/**").authenticated()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint);
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	http.addFilterBefore(corsFilter(), CorsFilter.class);
    return http.build();
  }
	
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// Don't do this in production, use a proper list of allowed origins
		// config.setAllowedOrigins(Collections.singletonList("*"));
		// config.setAllowedOriginPatterns(Collections.singletonList("*"));

		String allowedHeaders = env.getProperty("endpoints.cors.allowed-headers");
		String allowedMethods = env.getProperty("endpoints.cors.allowed-methods");
		String allowedOrigins = env.getProperty("endpoints.cors.allowed-origins");
		String allowedCredentials = env.getProperty("endpoints.cors.allow-credentials");

		List<String> allowedOriginList = new ArrayList<String>(Arrays.asList(allowedOrigins.split(",")));
		List<String> allowedHeaderList = new ArrayList<String>(Arrays.asList(allowedHeaders.split(",")));
		List<String> allowedMethodList = new ArrayList<String>(Arrays.asList(allowedMethods.split(",")));
		config.setAllowedOriginPatterns(allowedOriginList);
		// config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedHeaders(allowedHeaderList);

		// config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS",
		// "DELETE", "PATCH"));
		config.setAllowedMethods(allowedMethodList);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
