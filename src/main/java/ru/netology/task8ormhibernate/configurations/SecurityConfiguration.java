package ru.netology.task8ormhibernate.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    private final DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        final var guest = User.withUsername("guest")
                .password(encoder().encode("gpass"))
                .roles("READ")
                .build();
        final var operator = User.withUsername("operator")
                .password(encoder().encode("opass"))
                .roles("WRITE")
                .build();
        final var admin = User.withUsername("admin")
                .password(encoder().encode("apass"))
                .roles("READ", "WRITE", "DELETE")
                .build();
        if (!jdbcUserDetailsManager.userExists(guest.getUsername())) jdbcUserDetailsManager.createUser(guest);
        if (!jdbcUserDetailsManager.userExists(operator.getUsername())) jdbcUserDetailsManager.createUser(operator);
        if (!jdbcUserDetailsManager.userExists(admin.getUsername())) jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }
}