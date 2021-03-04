package pe.carlosesp.demo.demorestservice.service;

import org.springframework.stereotype.Service;
import pe.carlosesp.demo.demorestservice.dao.CustomerDao;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }
}
