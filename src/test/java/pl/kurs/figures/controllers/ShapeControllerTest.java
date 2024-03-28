package pl.kurs.figures.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import pl.kurs.figures.command.CreateShapeCommand;
import pl.kurs.figures.command.Type;
import pl.kurs.figures.security.entity.Role;
import pl.kurs.figures.security.entity.User;
import pl.kurs.figures.security.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

//    @BeforeEach
//    public void setup() {
//        user = setupUser();
//        this.postman = MockMvcBuilders
//                .webAppContextSetup(this.webApplicationContext)
//                .build();
//    }
//
//    public User setupUser() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        Role userRole = Role.USER;
//        User testUser = new User("user", bCryptPasswordEncoder.encode("user"), userRole);
//        return userRepository.save(testUser);
//    }


    @BeforeEach
    public void setup() {
        user = setupUser("user", Role.USER);
        this.postman = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
    }

    public User setupUser(String username, Role role) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User testUser = new User(username, bCryptPasswordEncoder.encode("user"), role);
        return userRepository.save(testUser);
    }

    private void authenticateAsAdmin() {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("adminUser", null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateAsTestUser(String username) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldReturnExceptionWhenInvalidShapeParameters() throws Exception {
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0, 7.0));

        addShape(command).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldCreateShapeAndReturnShapeDto() throws Exception {
        // given
        CreateShapeCommand command = new CreateShapeCommand(Type.SQUARE, List.of(5.0));
        String expectedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when & then
        addShape(command)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("SQUARE"))
                .andExpect(jsonPath("$.createdBy").value("user"))
                .andExpect(jsonPath("$.createdAt").value(expectedDateTime));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldSearchShapesByCriteria() throws Exception {
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(5.0)));
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(15.0)));
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(25.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 7.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)));


        postman.perform(get("/api/v1/shapes")
                .param("type", "SQUARE")
                .param("sideFrom", "10")
                .param("sideTo", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].side").value(15.0));

    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldSearchShapesAsUser() throws Exception {
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(5.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 7.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)));

        authenticateAsTestUser("otherUser");

        addShape(new CreateShapeCommand(Type.SQUARE, List.of(10.0)));
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(12.0)));

        postman.perform(get("/api/v1/shapes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type").value("SQUARE"))
                .andExpect(jsonPath("$[0].side").value(10.0))
                .andExpect(jsonPath("$[1].type").value("SQUARE"))
                .andExpect(jsonPath("$[1].side").value(12.0));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void shouldSearchShapesAsAdmin() throws Exception {
        addShape(new CreateShapeCommand(Type.SQUARE, List.of(5.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 7.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)));

        String username ="otherUser";
        authenticateAsTestUser(username);

        addShape(new CreateShapeCommand(Type.SQUARE, List.of(15.0)));
        addShape(new CreateShapeCommand(Type.RECTANGLE, List.of(5.0, 7.0)));
        addShape(new CreateShapeCommand(Type.CIRCLE, List.of(5.0)));

        authenticateAsAdmin();

        postman.perform(get("/api/v1/shapes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));

    }

    public ResultActions addShape(CreateShapeCommand command) throws Exception {
        return postman.perform(post("/api/v1/shapes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)));
    }
}