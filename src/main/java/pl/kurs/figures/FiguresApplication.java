package pl.kurs.figures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class FiguresApplication {
	public static void main(String[] args) {
		SpringApplication.run(FiguresApplication.class, args);
	}

}
