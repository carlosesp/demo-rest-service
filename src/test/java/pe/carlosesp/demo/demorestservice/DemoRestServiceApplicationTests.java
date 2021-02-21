package pe.carlosesp.demo.demorestservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import pe.carlosesp.demo.demorestservice.domain.Greeting;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.server.port=9090"})
class DemoRestServiceApplicationTests {

    @LocalServerPort
    private int port;

    @Value("${management.server.port}")
    private int mgtPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToController() {
        ResponseEntity<Greeting> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/greeting", Greeting.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getId()).isNotZero();
        assertThat(entity.getBody().getContent()).isEqualTo("Hello, World!");
    }

    @Test
    public void shouldReturn200WhenSendingRequestToActuatorHealthEndpoint() {
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgtPort + "/actuator/health", Map.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().get("status")).isEqualTo("UP");
        assertThat(entity.getBody().get("components")).isNotNull();
    }

    @Test
    public void shouldReturn400WhenSendingRequestToActuatorBeansEndpoint() {
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgtPort + "/actuator/beans", Map.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
