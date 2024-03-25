package pl.kurs.figures;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import pl.kurs.figures.config.AuditorAwareImpl;

import java.util.Optional;

@EnableAutoConfiguration
@ContextConfiguration
@Profile("test")
@EnableJpaAuditing
public class TestAuditingConfiguration {
    @Bean
    @Primary
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("testUser");
    }
}
