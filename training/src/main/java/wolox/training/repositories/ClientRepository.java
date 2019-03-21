package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Client;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);


    List<Client> findByUsernameContainingIgnoreCaseAndBirthdateBetween(String username, LocalDate from, LocalDate to);
}
