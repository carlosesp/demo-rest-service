package pe.carlosesp.demo.demorestservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.carlosesp.demo.demorestservice.dao.CustomerDao;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void findAll() {
        List<Customer> mockCustomers = Collections.singletonList(
                new Customer(1, "John", "Doe"));
        given(customerDao.findAll()).willReturn(mockCustomers);

        List<Customer> customers = customerService.findAll();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getFirstName()).isEqualTo("John");
        assertThat(customers.get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    void saveCustomer() {
        Customer customerRequest = new Customer();
        customerRequest.setFirstName("Jane");
        customerRequest.setLastName("Doe");

        given(customerDao.save(any(Customer.class))).willReturn(
                new Customer(101, "Jane", "Doe"));

        Customer savedCustomer = customerService.saveCustomer(customerRequest);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotZero();
        assertThat(savedCustomer.getId()).isEqualTo(101L);
        assertThat(savedCustomer.getFirstName()).isEqualTo("Jane");
        assertThat(savedCustomer.getLastName()).isEqualTo("Doe");
    }
}