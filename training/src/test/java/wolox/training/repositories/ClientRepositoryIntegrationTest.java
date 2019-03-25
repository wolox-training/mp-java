package wolox.training.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.models.Book;
import wolox.training.models.Client;
import wolox.training.utils.mocks.BookMock;
import wolox.training.utils.mocks.ClientMock;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BookRepository bookRepository;


    @Test
    public void whenCreateClientWithBook_thenCheck() {
        // given
        Book book = BookMock.createBook();
        Client client = ClientMock.createClient();

        // when
        bookRepository.save(book);
        entityManager.flush();
        client.addBook(book);
        clientRepository.save(client);
        entityManager.flush();

        Optional<Client> found = clientRepository.findById(book.getId());
        // then
        assertThat(found.equals(client));

    }

    @Test(expected = BookAlreadyOwnedException.class)
    public void whenAddOwnedBook_thenError() {
        // given
        Book book = BookMock.createBook();
        Client client = ClientMock.createClient();

        // when
        bookRepository.save(book);
        entityManager.flush();
        client.addBook(book);
        clientRepository.save(client);
        entityManager.flush();
        // then
        client.addBook(book);
        clientRepository.save(client);
        entityManager.flush();
    }


    @Test(expected = PersistenceException.class)
    public void whenCreateClient_thenThrowError()   {
        // given
        Client client = new Client();
        // when
        clientRepository.save(client);
        entityManager.flush();
    }


    @Test
    public void whenFindByUsername_thenReturnClient() {
       // given
        Client client = ClientMock.createClient();

        entityManager.persist(client);
        entityManager.flush();

        // when
        Client found = clientRepository.findByUsername(client.getUsername());

        // then
        assertThat(found.getUsername())
                .isEqualTo(client.getUsername());

    }

    @Test
    public void whenGetAll_thenReturnClients() {
        Map mapParameters = new HashMap();
        mapParameters.put("username", "maximiliano");
        mapParameters.put("birthdate", LocalDate.of(1993, Month.DECEMBER, 28));

        // given
        Client foundClient = ClientMock.createClient(mapParameters);
        mapParameters.clear();


        mapParameters.put("username", "paula");
        mapParameters.put("birthdate", LocalDate.of(1990, Month.JULY, 31));
        Client otherClient = ClientMock.createClient(mapParameters);
        entityManager.persist(foundClient);
        entityManager.persist(otherClient);
        entityManager.flush();
        LocalDate from = LocalDate.of(1900, Month.DECEMBER, 28);
        LocalDate to = LocalDate.of(2000, Month.DECEMBER, 28);
        Sort sort = new Sort(Sort.Direction.DESC, "username");
        Pageable pageRequest = new PageRequest(0, 20, sort);

        // when
        Page<Client> page = clientRepository.getAll("mIlI", from, to, pageRequest);
        // then
        assertThat(page.getContent().size() == 1 && page.getContent().get(0).equals(foundClient));

        // when
        page = clientRepository.getAll("mIlI", null, null, pageRequest);
        // then
        assertThat(page.getContent().size() == 1 && page.getContent().get(0).equals(foundClient));

        // when
        page = clientRepository.getAll("", from, to, pageRequest);
        // then
        assertThat(page.getContent().size() == 2);
    }
}

