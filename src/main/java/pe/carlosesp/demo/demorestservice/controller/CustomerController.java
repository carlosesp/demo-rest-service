package pe.carlosesp.demo.demorestservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.carlosesp.demo.demorestservice.domain.Customer;
import pe.carlosesp.demo.demorestservice.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = this.customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer request) {
        Customer savedCustomer = customerService.saveCustomer(request);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

}
