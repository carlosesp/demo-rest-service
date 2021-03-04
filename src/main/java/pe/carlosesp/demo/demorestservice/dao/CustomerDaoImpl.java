package pe.carlosesp.demo.demorestservice.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private JdbcTemplate jdbcTemplate;

    public CustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers",
                (rs, rowNum) -> new Customer(
                        rs.getLong("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                ));
    }

    @Override
    public Customer save(Customer customer) {
        int newCustomerId = jdbcTemplate.update("INSERT INTO customers (firstName, lastName) VALUES(?,?)",
                customer.getFirstName(), customer.getLastName());
        customer.setId(newCustomerId);
        return customer;
    }
}
