package pl.kurs.figures.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import pl.kurs.figures.TestAuditingConfiguration;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.security.entity.Role;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.repository.UserRepository;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestAuditingConfiguration.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ShapeControllerTest {
    private MockMvc postman;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;


    @BeforeEach
    public void setup() {
        user = setupUser();
        this.postman = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
    }

    public User setupUser() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Role userRole = Role.USER;
        User testUser = new User("user", bCryptPasswordEncoder.encode("user"), userRole);
        return userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldCreateShapeAndReturnShapeDto() throws Exception {
        // given
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0));

        // when & then
        postman.perform(post("/api/v1/shapes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void addShape() throws Exception {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0));
        SquareDTO expectedSquareDTO = new SquareDTO();
        expectedSquareDTO.setSide(5);
        expectedSquareDTO.setType(Type.SQUARE);

        postman.perform(post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated());
    }

    @Test
    void searchShapes() {
    }
}