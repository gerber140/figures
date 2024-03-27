package pl.kurs.figures.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import pl.kurs.figures.TestAuditingConfiguration;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.criteria.ShapeSearchCriteria;
import pl.kurs.figures.dto.ShapeDTO;
import pl.kurs.figures.dto.SquareDTO;
import pl.kurs.figures.model.Circle;
import pl.kurs.figures.model.Rectangle;
import pl.kurs.figures.model.Shape;
import pl.kurs.figures.model.Square;
import pl.kurs.figures.repository.ShapeRepository;
import pl.kurs.figures.security.entity.Role;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldSearchShapesByCriteria() throws Exception {
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(5.0)));
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(15.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 7.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)));


        postman.perform(get("/api/v1/shapes")
                .param("type", "SQUARE")
                .param("sideFrom", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].side").value(15.0));

    }

    public ResultActions addShape(CreateShapeCommand command) throws Exception {
        return postman.perform(post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)));
    }
}