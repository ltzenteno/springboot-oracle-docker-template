package org.zent.oracledb.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.zent.oracledb.dto.CreateUserDTO;
import org.zent.oracledb.dto.UserDTO;
import org.zent.oracledb.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-containers")
public class UserControllerTests {

    @Container
    static OracleContainer oracleContainer = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:21-slim-faststart"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", oracleContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", oracleContainer::getDriverClassName);
        dynamicPropertyRegistry.add("spring.datasource.username", oracleContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", oracleContainer::getPassword);
    }

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUser() throws Exception {
        CreateUserDTO requestDTO = CreateUserDTO.builder()
                .name("pepito")
                .email("pepito@chido.com")
                .password("secret")
                .build();

        String requestString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString)
        ).andExpect(status().isCreated());

        MvcResult mvcResult = resultActions.andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();

        UserDTO responseDTO = objectMapper.readValue(responseAsString, objectMapper.getTypeFactory().constructType(UserDTO.class));

        Assertions.assertNotNull(responseDTO.getId());
        Assertions.assertEquals(requestDTO.getName(), responseDTO.getName());
        Assertions.assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
    }
}
