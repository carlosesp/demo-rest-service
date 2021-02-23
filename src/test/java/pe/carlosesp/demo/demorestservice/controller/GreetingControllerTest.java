package pe.carlosesp.demo.demorestservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.carlosesp.demo.demorestservice.domain.Greeting;
import pe.carlosesp.demo.demorestservice.service.GreetingService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GreetingController.class)
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService service;

    @Test
    public void getGreetingWithoutName_ShouldReturnDefaultGreeting() throws Exception {
        Greeting greeting = new Greeting(1L, "Hello, World!");
        given(service.getGreeting(anyString())).willReturn(greeting);

        this.mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("content").value("Hello, World!"));
    }

    @Test
    public void getGreetingWithName_ShouldReturnGreetingWithName() throws Exception {
        Greeting greeting = new Greeting(1L, "Hello, Carlos!");
        given(service.getGreeting(anyString())).willReturn(greeting);

        this.mockMvc.perform(get("/greeting").param("name", "Carlos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("content").value("Hello, Carlos!"));
    }

}
