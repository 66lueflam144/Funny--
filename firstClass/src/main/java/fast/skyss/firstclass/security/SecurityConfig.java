package fast.skyss.firstclass.security;

import fast.skyss.firstclass.entity.basicEntity.User_x;
import fast.skyss.firstclass.repository.UserRepository;
import fast.skyss.firstclass.service.dataService.Sto;
import fast.skyss.firstclass.service.dataService.User_Service;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final User_Service userService;

    public SecurityConfig(UserRepository userRepository, User_Service userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void createDeanUser() {
        if (!userRepository.existsByUsername("dean")) {
            userService.createDeanUser("dean", "die4u", "dean@whocares.com");
        }
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User_x user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String role = username.equals("dean") ? "DEAN" : "USER";
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(role)
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }



//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/outtime/login", "/outtime/register", "/error", "/api/users/register").permitAll()
//                        .requestMatchers("/api/posts/public").permitAll()
//                        .requestMatchers("/api/users/**").authenticated()
//                        .requestMatchers("/api/posts/**").authenticated()
//                        .requestMatchers("/off_line/login").permitAll()
//                        .requestMatchers("/off_line/**").hasRole("DEAN")
//                        .anyRequest().authenticated()
//                )
//                .securityMatchers((matchers) -> matchers
//                        .requestMatchers("/outtime/login", "/outtime/home/**"))
//                .formLogin(form -> form
//                        .loginPage("/outtime/login")
//                        .loginProcessingUrl("/outtime/login")
//                        .defaultSuccessUrl("/outtime/home", true)
//                        .failureUrl("/outtime/login?error=true")
//                        .permitAll()
//                )
//                .securityMatchers((matchers) -> matchers
//                        .requestMatchers("/off_line/**"))
//                .formLogin(form -> form
//                        .loginPage("/off_line/dean-on-line")
//                        .loginProcessingUrl("/off_line/dean-on-line")
//                        .defaultSuccessUrl("/off_line/re-connect", true)
//                        .failureUrl("/off_line/dean-on-line?error=true")
//                        .permitAll()
//                )
//
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .permitAll());
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain outtimeFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/outtime/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/outtime/login", "/outtime/register").permitAll()
                        .requestMatchers("/outtime/api/users/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/outtime/login")
                        .loginProcessingUrl("/outtime/login")
                        .defaultSuccessUrl("/outtime/home", true)
                        .failureUrl("/outtime/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/outtime/logout")
                        .logoutSuccessUrl("/outtime/login")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/posts/public").permitAll()
                        .requestMatchers("/api/users/profile").authenticated()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/posts/users/{userId}/posts").authenticated()
                        .requestMatchers("/api/posts/**").authenticated()
                        .anyRequest().authenticated()
                );

        return http.build();
    }


    @Bean
    public SecurityFilterChain offlineFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/off_line/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/off_line/login").permitAll()
                        .requestMatchers("/off_line/users/{userId}/posts").authenticated()
                        .requestMatchers("/off_line/**").hasRole("DEAN")
                )
                .formLogin(form -> form
                        .loginPage("/off_line/dean-on-line")
                        .loginProcessingUrl("/off_line/dean-on-line")
                        .defaultSuccessUrl("/off_line/re-connect", true)
                        .failureUrl("/off_line/dean-on-line?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/off_line/logout")
                        .logoutSuccessUrl("/off_line/login")
                        .permitAll());

        return http.build();
    }



}
