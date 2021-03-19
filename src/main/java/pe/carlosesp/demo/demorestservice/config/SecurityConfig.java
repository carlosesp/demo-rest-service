package pe.carlosesp.demo.demorestservice.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("user1")
                .password(encoder.encode("secret1"))
                .roles("USER");

        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("endpoint1")
                .password(encoder.encode("secretendpoint1"))
                .roles("ENDPOINT_ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/actuator/shutdown")
                .and()
                .authorizeRequests()
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class, HealthEndpoint.class)).hasRole("ENDPOINT_ADMIN")
                .antMatchers("/customers/**").hasRole("USER")
                .antMatchers("/greeting/**").permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                .httpBasic();
    }
}
