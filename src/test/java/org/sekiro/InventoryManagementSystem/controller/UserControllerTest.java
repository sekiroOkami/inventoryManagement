package org.sekiro.InventoryManagementSystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAllUsers_WithAdminAuthority_ShouldReturn200() throws Exception {
        // Arrange
        String endpoint = "/api/users/all";
        int expectedStatus = 200;
        String expectedMessage = "success";

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint)).andReturn();

        // Assert
        int actualStatus = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();
        assertAll(
                () -> assertThat(actualStatus).isEqualTo(expectedStatus),
                () -> assertThat(content).contains("\"status\":200"),
                () -> assertThat(content).contains("\"message\":\"" + expectedMessage + "\"")
        );
    }


    @Test
    @WithMockUser
    void testGetAllUsers_WithNoAuthority_ShouldReturn500() throws Exception {
        // Arrange
        String endpoint = "/api/users/all";
        int expectedStatus = 500;

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andReturn();

        // Assert
        int actualStatus = result.getResponse().getStatus();
        assertAll(
                () -> assertThat(actualStatus).isEqualTo(expectedStatus)
        );
    }

}