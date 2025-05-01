/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // Configure the SecurityFilterChain (no need for @EnableWebSecurity)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/monitoring/**").hasRole("ADMIN") // Secure the monitoring page
                .anyRequest().permitAll() // Allow other endpoints without authentication
                .and()
                .httpBasic(); // Optional: HTTP Basic Authentication

        return http.build();
    }

    // Password encoder for storing passwords securely
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    // Optionally, you can add custom authentication configuration if needed
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**"); // Ignore static resources
    }
}

*/
