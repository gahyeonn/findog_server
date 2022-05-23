package challengers.findog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3001", "http://localhost:3000",
                        "https://findog.co.kr", "https://www.findog.co.kr",
                        "http://findog.co.kr", "http://www.findog.co.kr",
                        "https://findog.co.kr:3000", "https://www.findog.co.kr:3000",
                        "http://findog.co.kr:3000", "http://www.findog.co.kr:3000",
                        "https://findog.co.kr:3001", "https://www.findog.co.kr:3001",
                        "http://findog.co.kr:3001", "http://www.findog.co.kr:3001",
                        "https://main--celebrated-sawine-8ea9c1.netlify.app",
                        "http://main--celebrated-sawine-8ea9c1.netlify.app",
                        "https://main--celebrated-sawine-8ea9c1.netlify.app:3000",
                        "http://main--celebrated-sawine-8ea9c1.netlify.app:3000",
                        "https://main--celebrated-sawine-8ea9c1.netlify.app:3001",
                        "http://main--celebrated-sawine-8ea9c1.netlify.app:3001")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
