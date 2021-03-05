package pe.carlosesp.demo.demorestservice.repository;

import org.springframework.data.repository.CrudRepository;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
}
