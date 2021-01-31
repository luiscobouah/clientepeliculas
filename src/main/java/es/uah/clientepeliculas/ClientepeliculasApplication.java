package es.uah.clientepeliculas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientepeliculasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientepeliculasApplication.class, args);
    }

    @Bean
    public RestTemplate template() {
        RestTemplate template = new RestTemplate();
        return template;
    }

}
