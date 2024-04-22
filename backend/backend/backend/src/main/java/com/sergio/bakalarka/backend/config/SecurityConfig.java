package com.sergio.bakalarka.backend.config;

import com.sergio.bakalarka.backend.repository.UserDao;
import com.sergio.bakalarka.backend.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final JwtTokenFilter jwtTokenFilter;
    private final UserDao userDao;

    // TODO: secure endpoints

    private static final String PRODUCTS_ENDPOINT = "/api/products/**";
    private static final String USERS_ENDPOINT = "/api/profile/**";

    // TODO: todododo
    private static final String ADMIN_ENDPOINT = "/api/user/**";
    private static final String LOGIN_ENDPOINT = "/api/auth/**";
    private static final String SECTIONS_ENDPOINT = "/api/sections/**";
    private static final String ADDITIONALSERVICES_ENDPOINT = "/api/addservices/**";
    private static final String ATTRACTIONS_ENDPOINT = "/api/attractions/**";
    private static final String TICKETS_ENDPOINT = "/api/tickets/**";
    private static final String PAYMENTS_ENDPOINT = "/api/payments/**";
    private static final String EMPLOYEES_ENDPOINT = "/api/employees/**";
    private static final String SCHEDULES_ENDPOINT = "/api/schedules/**";
    private static final String AQUAPARK_ENDPOINT = "/api/digitaldynasty/**";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDao::getUserByUsername);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        logger.info("Configuring HTTP security...");

        httpSecurity.cors().and().csrf().disable();
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/sections/add").permitAll()
                .antMatchers(PRODUCTS_ENDPOINT).permitAll()
                //TODO
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(SECTIONS_ENDPOINT).permitAll()
                .antMatchers(ADDITIONALSERVICES_ENDPOINT).permitAll()
                .antMatchers(ATTRACTIONS_ENDPOINT).permitAll()
                .antMatchers(USERS_ENDPOINT).permitAll()
                .antMatchers(TICKETS_ENDPOINT).permitAll()
                .antMatchers(PAYMENTS_ENDPOINT).permitAll()
                .antMatchers(EMPLOYEES_ENDPOINT).permitAll()
                .antMatchers(SCHEDULES_ENDPOINT).permitAll()
                .antMatchers(AQUAPARK_ENDPOINT).permitAll()
                .antMatchers("/api/profile/users/*/avatar").permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("admin") // todo: check User implements UserDetails
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class);

        logger.info("HTTP security configured successfully.");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        logger.info("CORS configuration initialized.");
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.info("BCryptPasswordEncoder bean initialized.");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        logger.info("Authentication manager bean initialized.");
        return super.authenticationManagerBean();
    }

}


