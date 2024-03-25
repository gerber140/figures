package pl.kurs.figures;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.repository.ShapeRepository;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(TestAuditingConfiguration.class)
public class AuditingTest {
    @Autowired
    private AuditorAware<String> auditorAware;

    @Autowired
    private ShapeRepository shapeRepository;

    @Test
    public void testCurrentAuditor() {
        String currentAuditor = auditorAware.getCurrentAuditor().get();
        assertEquals("testUser", currentAuditor);
    }

    @Test
    public void shapeRepositoryTest() {
        Square square = new Square(5.0);
        shapeRepository.save(square);

        List<Shape> shapeList = shapeRepository.findAll();
        Shape result = shapeList.get(0);
        assertEquals("testUser", result.getCreatedBy());
    }
}
