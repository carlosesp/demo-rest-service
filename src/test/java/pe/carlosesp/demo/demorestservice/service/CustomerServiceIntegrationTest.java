package pe.carlosesp.demo.demorestservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void findAll() {
        List<Customer> customers = customerService.findAll();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getFirstName()).isEqualTo("John");
        assertThat(customers.get(0).getLastName()).isEqualTo("Doe");
    }
}