package Tienda.Web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "Tienda")     // <-- clave
@EntityScan("Tienda.domain")
@EnableJpaRepositories("Tienda")
public class TiendaAndreyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TiendaAndreyApplication.class, args);
    }
}
