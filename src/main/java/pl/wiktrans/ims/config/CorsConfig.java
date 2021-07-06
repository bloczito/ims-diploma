package pl.wiktrans.ims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*"); // e.g. http://domain1.com
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("/**");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
