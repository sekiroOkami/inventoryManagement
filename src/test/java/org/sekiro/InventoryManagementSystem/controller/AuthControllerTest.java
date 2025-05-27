package org.sekiro.InventoryManagementSystem.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class) // create a Spring context with AuthController bean
class AuthControllerTest {

    // injects a MockMvc instance for simulating HTTP requests
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    // Test data
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final int EXPECTED_STATUS = 200;
    private static final String EXPECTED_MESSAGE = "success";

    record TestUser(String registerJson, String loginJson) {}

    private static Stream<TestUser> provideTestUsers() {
        return Stream.of(
                new TestUser(
                        """
                                {
                                    "email": kuro@gmail.com",
                                    "password": "kuro123",
                                    "phoneNumber": "123456789",
                                    "role": "ADMIN"
                                }
                                """,
                        """
                                {
                                    "email": "kuro@gmail.com",
                                    "password": "kuro123"
                                }"""
                ),
                new TestUser(
                        """
                        {
                            "email": "sekiro@gmail.com",
                            "password": "sekiro456",
                            "phoneNumber": "987654321",
                            "role": "USER"
                        }
                        """,
                        """
                        {
                            "email": "sekiro@gmail.com",
                            "password": "sekiro456"
                        }
                        """
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestUsers")
    void testRegisterUser_ValidRequest_ShouldReturn200(TestUser testUser) throws Exception {
        // Arrange
        Response mockResponse = Response.builder()
                .status(200)
                .message("success")
                .build();

        when(userService.registerUser(any())).thenReturn(mockResponse);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(testUser.registerJson))
                .andReturn();

        // Assert
        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
        System.out.println("content = " + content);
        assertAll(
                ()-> assertThat(actualStatus).isEqualTo(EXPECTED_STATUS),
                () -> assertThat(content).contains("\"status\":200"),
                () -> assertThat(content).contains("\"message\":\"" + EXPECTED_MESSAGE + "\"")
        );

    }


}