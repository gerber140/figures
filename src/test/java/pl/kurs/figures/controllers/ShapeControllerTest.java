package pl.kurs.figures.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.security.entity.Role;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
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
    public void setup() throws Exception {
        user = setupUser();
        this.postman = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();

        addShape(new CreateShapeCommand(Type.SQUARE, List.of(5.0)));
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(10.0)));
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(15.0)));

        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 10.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(10.0, 15.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(15.0, 20.0)));

        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(10.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(15.0)));
    }

    public User setupUser() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Role userRole = Role.USER;
        User testUser = new User("testUser", bCryptPasswordEncoder.encode("user"), userRole);
        return userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    void shouldReturnExceptionWhenInvalidShapeParameters() throws Exception {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0, 7.0));

        addShape(command).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    void shouldCreateShapeAndReturnShapeDto() throws Exception {
        // given
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0));
        String expectedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when & then
        addShape(command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("SQUARE"))
                .andExpect(jsonPath("$.createdBy").value("testUser"))
                .andExpect(jsonPath("$.createdAt").value(expectedDateTime));
    }


    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    void shouldSearchSquareBySide() throws Exception {

        postman.perform(get("/api/v1/shapes")
                .param("type", "SQUARE")
                .param("sideFrom", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].side").value(15.0));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    void shouldSearchRectangleByArea() throws Exception {
        postman.perform(get("/api/v1/shapes")
                        .param("type", "RECTANGLE")
                        .param("areaFrom", "100")
                        .param("areaTo", "200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("RECTANGLE"))
                .andExpect(jsonPath("$[0].area").value(150));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    void shouldSearchCircleByPerimeter() throws Exception {
        postman.perform(get("/api/v1/shapes")
                        .param("type", "CIRCLE")
                        .param("perimeterFrom", "60")
                        .param("perimeterTo", "80"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.type").value("CIRCLE"))
                .andExpect(jsonPath("$[0].radius").value(10));
    }

    public ResultActions addShape(CreateShapeCommand command) throws Exception {
        return postman.perform(post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)));
    }
}