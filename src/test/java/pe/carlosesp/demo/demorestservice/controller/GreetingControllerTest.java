package pe.carlosesp.demo.demorestservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pe.carlosesp.demo.demorestservice.domain.Greeting;
import pe.carlosesp.demo.demorestservice.service.GreetingService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(GreetingController.class)
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService service;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void getGreetingWithoutName_ShouldReturnDefaultGreeting() throws Exception {
        Greeting greeting = new Greeting(1L, "Hello, World!");
        given(service.getGreeting(anyString())).willReturn(greeting);

        this.mockMvc.perform(get("/greeting").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("content").value("Hello, World!"))
                .andDo(document("greeting", responseFields(
                        fieldWithPath("id").description("Id of the request"),
                        fieldWithPath("content").description("Greeting content")
                )));
    }

    @Test
    public void getGreetingWithName_ShouldReturnGreetingWithName() throws Exception {
        Greeting greeting = new Greeting(1L, "Hello, Carlos!");
        given(service.getGreeting(anyString())).willReturn(greeting);

        this.mockMvc.perform(get("/greeting").param("name", "Carlos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("content").value("Hello, Carlos!"))
                .andDo(document("greeting-param",
                        requestParameters(
                                parameterWithName("name").description("The name to include in the greeting").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of the request"),
                                fieldWithPath("content").description("Greeting content"))
                ));
    }

}
