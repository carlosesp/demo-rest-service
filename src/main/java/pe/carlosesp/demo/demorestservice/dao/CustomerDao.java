package pe.carlosesp.demo.demorestservice.dao;

import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> findAll();

    Customer save(Customer customer);

    Customer findById(Long id);
}
