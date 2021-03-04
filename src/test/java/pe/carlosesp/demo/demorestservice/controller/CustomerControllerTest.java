package pe.carlosesp.demo.demorestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pe.carlosesp.demo.demorestservice.domain.Customer;
import pe.carlosesp.demo.demorestservice.service.CustomerService;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private RestDocumentationResultHandler documentationHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentationContextProvider) {
        this.documentationHandler = document("customer/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .alwaysDo(this.documentationHandler)
                .build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        given(customerService.findAll()).willReturn(Collections.singletonList(
                new Customer(1, "John", "Doe")));

        this.mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("[0].id").description("Customer's Id"),
                                fieldWithPath("[0].firstName").description("First Name"),
                                fieldWithPath("[0].lastName").description("Last Name")
                        )
                ));

        verify(customerService, times(1)).findAll();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void saveCustomer() throws Exception {
        Customer customerRequest = new Customer();
        customerRequest.setFirstName("Jane");
        customerRequest.setLastName("Doe");

        given(customerService.saveCustomer(any(Customer.class))).willReturn(
                new Customer(101, "Jane", "Doe"));

        String body = objectMapper.writeValueAsString(customerRequest);

        this.mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(is(notNullValue())))
                .andExpect(jsonPath("firstName").value("Jane"))
                .andExpect(jsonPath("lastName").value("Doe"))
                .andDo(this.documentationHandler.document(
                        relaxedRequestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("firstName").description("First Name of the customer"),
                                fieldWithPath("lastName").description("Last Name of the customer")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Customer's Id"),
                                fieldWithPath("firstName").description("First Name"),
                                fieldWithPath("lastName").description("Last Name")
                        )
                ));

        verify(customerService, times(1)).saveCustomer(any(Customer.class));
        verifyNoMoreInteractions(customerService);
    }

}
