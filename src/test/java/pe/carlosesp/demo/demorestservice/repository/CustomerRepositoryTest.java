package pe.carlosesp.demo.demorestservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findByLastName() {
        List<Customer> customers = customerRepository.findByLastName("Doe");

        assertThat(customers).isNotNull();
        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getLastName()).isEqualTo("Doe");
        assertThat(customers.get(0).getFirstName()).isEqualTo("John");
        assertThat(customers.get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void findById() {
        Optional<Customer> customer = customerRepository.findById(1L);

        assertThat(customer.isPresent()).isTrue();
        assertThat(customer.get().getId()).isEqualTo(1L);
        assertThat(customer.get().getLastName()).isEqualTo("Doe");
        assertThat(customer.get().getFirstName()).isEqualTo("John");
    }

}
