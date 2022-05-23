package challengers.findog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://localhost:3001")
                .allowedOrigins("https://findog.co.kr")
                .allowedOrigins("https://findog.co.kr:3000")
                .allowedOrigins("https://findog.co.kr:3001")
                .allowedOrigins("https://main--celebrated-sawine-8ea9c1.netlify.app")
                .allowedOrigins("https://main--celebrated-sawine-8ea9c1.netlify.app:3000")
                .allowedOrigins("https://main--celebrated-sawine-8ea9c1.netlify.app:3001")
                .allowedOrigins("")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}