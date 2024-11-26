package fast.skyss.firstclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fast.skyss.firstclass")
public class FirstClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstClassApplication.class, args);
    }

}
