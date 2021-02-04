package bestellung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BestellungApplication {

  public static void main(String[] args) {
    SpringApplication.run(BestellungApplication.class, args);
  }




}
