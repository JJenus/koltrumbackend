package com.koltrum.koltrum.security;

import com.koltrum.koltrum.model.ROLE;
import com.koltrum.koltrum.model.Role;
import com.koltrum.koltrum.model.Setting;
import com.koltrum.koltrum.repository.AppUserRepo;
import com.koltrum.koltrum.repository.RoleRepo;
import com.koltrum.koltrum.service.AppUserService;
import com.koltrum.koltrum.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecuritySetup {
    @Autowired
    private AppUserRepo userRepo;
    @Autowired
    private JWTFilter filter;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private SettingsService settingsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public void createRoles(){
        try{
            Setting setting = settingsService.getSettings();
            if (setting == null){
                setting = Setting.builder()
                        .language("EN")
                        .currency("USD")
                        .currencySymbol("$")
                        .build();
                settingsService.saveSetting(setting);
            }

            if (!roleRepo.findByName(ROLE.ADMIN.name()).isPresent())
                roleRepo.save(new Role(ROLE.ADMIN.name()));

            if (!roleRepo.findByName(ROLE.USER.name()).isPresent())
                roleRepo.save(new Role(ROLE.USER.name()));

        }catch (Exception err){
            // pass
            err.printStackTrace();
        }
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors().disable()
                .authorizeHttpRequests()
                .antMatchers("*")
                .permitAll()
//                .anyRequest()
//                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .userDetailsService(appUserService)
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"
                        )
                )
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
