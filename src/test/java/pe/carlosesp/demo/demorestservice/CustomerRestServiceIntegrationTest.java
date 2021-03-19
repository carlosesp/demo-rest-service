package pe.carlosesp.demo.demorestservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRestServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private static TestRestTemplate testRestTemplate;

    @BeforeAll
    static void before() {
        testRestTemplate = new TestRestTemplate("user1", "secret1");
    }

    @Test
    public void getCustomerById() {
        long id = 1;
        ResponseEntity<Customer> entity = testRestTemplate
                .getForEntity(
                        "http://localhost:" + this.port + "/customers/" + id, Customer.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isNotNull();
        assertThat(entity.getBody().getFirstName()).isEqualTo("John");
        assertThat(entity.getBody().getLastName()).isEqualTo("Doe");
    }

    @Test
    public void getAllCustomers() {
        ResponseEntity<List<Customer>> entity = testRestTemplate
                .exchange(
                        "http://localhost:" + this.port + "/customers",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Customer>>() {
                        }
                );

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isNotNull();
        assertThat(entity.getBody()).hasSize(1);
        assertThat(entity.getBody().get(0).getFirstName()).isEqualTo("John");
        assertThat(entity.getBody().get(0).getLastName()).isEqualTo("Doe");
    }

}
