package africa.semicolon.wollet.repositories;

import africa.semicolon.wollet.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomersById(Long id);
}
