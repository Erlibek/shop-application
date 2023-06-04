package erli.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeHttpRequests(authorizationConfigurer -> {
            authorizationConfigurer.requestMatchers("/cart/**").authenticated();
            authorizationConfigurer.requestMatchers("/order/**").authenticated();
            authorizationConfigurer.requestMatchers("/profile/**").authenticated();


            authorizationConfigurer.requestMatchers("/moderation/**").hasRole("admin");
            authorizationConfigurer.requestMatchers("/admin/**").hasRole("admin");
            authorizationConfigurer.anyRequest().permitAll();
        });

        http
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/products");
        return http.build();
    }
}
