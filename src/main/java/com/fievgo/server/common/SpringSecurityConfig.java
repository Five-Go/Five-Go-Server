//package com.fievgo.server.common;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
//@RequiredArgsConstructor
//public class SpringSecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> request
////                                .requestMatchers("/login", "/registration", "/sing/*").permitAll()
////                                .anyRequest().authenticated()
//                                .anyRequest().permitAll()
//                )
//                .formLogin(login -> login
//                                .loginPage("/login")
////                        .permitAll()
//                )
//                .logout(withDefaults());
//
//        return http.build();
//    }
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails user = User.builder()
////                .username("user")
////                .password(passwordEncoder().encode("password"))
////                .roles("USER")
////                .build();
////
////        return new InMemoryUserDetailsManager(user);
////    }
//
//}
