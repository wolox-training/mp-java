package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Client;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);

    @Query("SELECT c FROM Client c WHERE (:username is null or  lower(c.username) like lower(concat('%', :username,'%')) ) and " +
            "(cast(:from AS date) is null or c.birthdate >= :from) and " +
            " (cast(:to AS date) is null or c.birthdate <= :to)")
    Page<Client> getAll(@Param("username") String username, @Param("from")  LocalDate from, @Param("to") LocalDate to, Pageable pageable);


}
