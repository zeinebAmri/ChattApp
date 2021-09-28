package com.kth.chatapp.config;

//This Program is used to ensure that the Spring application is secure.

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kth.chatapp.model.CustomUserDetailsService;
@EnableWebSecurity // to enable spring security support
@Configuration //indicates that the class has @Bean definition methods
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    
   
    @Bean
    //UserDetailsService loads user-specific data
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
     
    @Bean
    //Uses the BCrypt hashing function to encode passwords.
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    //DAO= Data Access Object
    //DaoAuthenticationProvider retrieves the user details from read-only user Dao(UserDetailsService).
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        //Encode and validate passwords
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
 
    @Override
    //Creates a local authentication manager
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//authenticationProvider processes an authentication request
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Override
    //This method defines which URL paths should be secured.
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .requiresChannel()
	        .anyRequest()
	        .requiresSecure()
	        .and()
        	.authorizeRequests()
            .antMatchers("/chat_rooms").authenticated() // require authentication
            .antMatchers("/chat_room/**").authenticated()// require authentication
            .anyRequest().permitAll()
            .and()
            .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/chat_rooms")
                .permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll();
    }
     
     
     
}
