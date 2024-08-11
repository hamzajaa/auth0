package bootiful.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(ors -> ors.jwt(Customizer.withDefaults()))
                .build();
    }
}

@Controller
@ResponseBody
class GreetingsController {

    @GetMapping("/hello")
    Map<String, String> hello(Principal principal) {
        return Map.of("messages", "Hello, " + principal.getName() + ", from the backend API!");
    }
}