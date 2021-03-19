package pe.carlosesp.demo.demorestservice.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ComponentScan
class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    void findAll() {
        List<Customer> customers = customerDao.findAll();

        assertThat(customers).isNotNull().hasSize(1);

        Customer firstCustomer = customers.get(0);
        assertThat(firstCustomer).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(firstCustomer).hasFieldOrPropertyWithValue("firstName", "John");
        assertThat(firstCustomer).hasFieldOrPropertyWithValue("lastName", "Doe");
    }

    @Test
    void findAById() {
        Customer customer = customerDao.findById(1L);

        assertThat(customer).isNotNull();
        assertThat(customer).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(customer).hasFieldOrPropertyWithValue("firstName", "John");
        assertThat(customer).hasFieldOrPropertyWithValue("lastName", "Doe");
    }

    @Test
    void save() {
        Customer customerRequest = new Customer();
        customerRequest.setFirstName("Jane");
        customerRequest.setLastName("Doe");

        Customer savedCustomer = customerDao.save(customerRequest);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedCustomer).hasFieldOrPropertyWithValue("firstName", "Jane");
        assertThat(savedCustomer).hasFieldOrPropertyWithValue("lastName", "Doe");
    }
}