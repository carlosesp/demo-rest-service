package pe.carlosesp.demo.demorestservice.service;

import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer saveCustomer(Customer customer);
}
