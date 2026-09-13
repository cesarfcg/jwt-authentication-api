    package com.fernando.spring_security_jwt.Security;

    import com.nimbusds.jose.jwk.JWK;
    import com.nimbusds.jose.jwk.JWKSet;
    import com.nimbusds.jose.jwk.RSAKey;
    import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
    import com.nimbusds.jose.jwk.source.JWKSource;
    import com.nimbusds.jose.proc.SecurityContext;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.oauth2.jwt.JwtDecoder;
    import org.springframework.security.oauth2.jwt.JwtEncoder;
    import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
    import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import java.security.interfaces.RSAPrivateKey;
    import java.security.interfaces.RSAPublicKey;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    import java.util.List;

    @EnableWebSecurity
    @Configuration
    public class SecurityConfig {
        @Value("${jwt.public.key}")
        private RSAPublicKey publicKey;
        @Value("${jwt.private.key}")
        private RSAPrivateKey privateKey;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
                    .cors(cors -> {})
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                .authorizeHttpRequests(auth -> auth.requestMatchers("/register","/swagger-ui/**").permitAll()
                            .requestMatchers("/admin", "/users").hasAuthority("SCOPE_ADMIN")
                            .requestMatchers(
                                "/auth/login",
                                "/auth/register").permitAll()
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
            return http.build();
        }
    //    @Bean
    //    public UserDetailsService userDetailsService() {
    //        UserDetails user = User.builder()
    //                .username("user")
    //                .password(passwordEncoder().encode("password"))
    //                .roles("ADMIN")
    //                .build();
    //        return new InMemoryUserDetailsManager(user);
    //    }
        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
        @Bean
        JwtDecoder jwtDecoder(){
            return NimbusJwtDecoder.withPublicKey(publicKey).build();
        }
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedOrigins(
                    List.of("http://localhost:5173")
            );

            configuration.setAllowedMethods(
                    List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
            );

            configuration.setAllowedHeaders(
                    List.of("*")
            );

            UrlBasedCorsConfigurationSource source =
                    new UrlBasedCorsConfigurationSource();

            source.registerCorsConfiguration("/**", configuration);

            return source;
        }
        @Bean
        JwtEncoder jwtEncoder(){
            JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
            JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
            return new NimbusJwtEncoder(jwks);
        }
    }
