package javaproject.com.expensetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager udm = new JdbcUserDetailsManager(dataSource);
        
        // ✅ Corrected SQL queries for authentication
        udm.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        udm.setAuthoritiesByUsernameQuery("SELECT username, CONCAT('ROLE_', role) FROM users WHERE username = ?");	
        
        return udm;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsManager(null)); // Uses JdbcUserDetailsManager
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            // ✅ USER MANAGEMENT
            .requestMatchers(HttpMethod.GET, "/users/all").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/users/get/**").hasAnyRole("ADMIN","USER")
            .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
            .requestMatchers(HttpMethod.PUT, "/users/update/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/users/delete/**").hasRole("ADMIN")

            // ✅ EXPENSE MANAGEMENT
            .requestMatchers(HttpMethod.GET, "/expenses/getAll").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/expenses/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/expenses/get/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/expenses/add").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PUT, "/expenses/update/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/expenses/delete/**").hasRole("ADMIN")

            // ✅ PAYMENT METHOD MANAGEMENT
            .requestMatchers(HttpMethod.GET, "/payment-methods/all").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/payment-methods/get/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/payment-methods/add").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/payment-methods/update/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/payment-methods/delete/**").hasRole("ADMIN")
            
            // ✅ CATEGORY MANAGEMENT
            .requestMatchers(HttpMethod.GET, "/api/categories/all").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/categories/view/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/categories/add").hasAnyRole("ADMIN","USER")
            .requestMatchers(HttpMethod.PUT, "/api/categories/update/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/categories/delete/**").hasRole("ADMIN")

            // ✅ Allow other requests (optional)
            .anyRequest().permitAll()
        );

        // ✅ Register authentication provider
        http.authenticationProvider(authenticationProvider());

        // ✅ Enable basic authentication
        http.httpBasic(Customizer.withDefaults());

        // ✅ Enable form-based login for frontend
        http.formLogin(Customizer.withDefaults());

        // ✅ Disable CSRF for APIs (cross-site request forgery)
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
