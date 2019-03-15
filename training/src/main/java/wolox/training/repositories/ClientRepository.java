package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wolox.training.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);
}
