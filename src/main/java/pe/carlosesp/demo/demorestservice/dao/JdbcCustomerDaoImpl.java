package pe.carlosesp.demo.demorestservice.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pe.carlosesp.demo.demorestservice.domain.Customer;

import java.util.List;

@Repository
public class JdbcCustomerDaoImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer",
                customerRowMapper);
    }

    @Override
    public Customer save(Customer customer) {
        int newCustomerId = jdbcTemplate.update(
                "INSERT INTO customer (first_name, last_name) VALUES(?,?)",
                customer.getFirstName(), customer.getLastName());
        customer.setId(newCustomerId);
        return customer;
    }

    @Override
    public Customer findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from customer where id = ?",
                customerRowMapper,
                id);
    }

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        Customer newCustomer = new Customer();
        newCustomer.setId(rs.getLong("id"));
        newCustomer.setFirstName(rs.getString("first_name"));
        newCustomer.setLastName(rs.getString("last_name"));
        return newCustomer;
    };
}
