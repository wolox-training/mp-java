package wolox.training.repositories;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;
import wolox.training.utils.mocks.BookMock;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;



    @Test
    public void whenCreateBook_thenCheck() {
        // given
        Book book = BookMock.createBook();

        // when
        bookRepository.save(book);
        entityManager.flush();
        Optional<Book> found = bookRepository.findById(book.getId());
        // then
        assertThat(found.equals(book));
    }


    @Test(expected = PersistenceException.class)
    public void whenCreateBook_thenThrowError()   {
        // given
        Book book = new Book();
        // when
        bookRepository.save(book);
        entityManager.flush();
    }


    @Test
    public void whenFindByAuthor_thenReturnBook() {
       // given
        Book book = BookMock.createBook();
        entityManager.persist(book);
        entityManager.flush();

        // when
        Book found = bookRepository.findByAuthor(book.getAuthor());

        // then
        assertThat(found.getAuthor())
                .isEqualTo(book.getAuthor());

    }

    @Test
    public void whenFindByISBN_thenReturnBook() {
        // given
        Book book = BookMock.createBook();
        entityManager.persist(book);
        entityManager.flush();

        // when
        Book found = bookRepository.findByIsbn(book.getIsbn());

        // then
        assertThat(found.getIsbn())
                .isEqualTo(book.getIsbn());

    }

     @Test
    public void whenFindByGenreAndPublisherAndYear_thenReturnBook() {
        // given

        Book foundBook = BookMock.createBook();
        Book otherBook = BookMock.createBook();
        entityManager.persist(foundBook);
        entityManager.persist(otherBook);
        entityManager.flush();

        // when
        List<Book> founds = bookRepository.findByGenreAndPublisherAndYear(foundBook.getGenre(),foundBook.getPublisher(),foundBook.getYear());

        // then
        assertThat(founds.size() == 1 );
        assertThat(founds.get(0).equals(foundBook));

        // when
        founds = bookRepository.findByGenreAndPublisherAndYear(foundBook.getGenre(),foundBook.getPublisher(), null);

        // then
        assertThat(founds.size() == 1 );
        assertThat(founds.get(0).equals(foundBook));


        // when
        founds = bookRepository.findByGenreAndPublisherAndYear(foundBook.getGenre(),null,foundBook.getYear());

        // then
        assertThat(founds.size() == 1 );
        assertThat(founds.get(0).equals(foundBook));

         // when
         founds = bookRepository.findByGenreAndPublisherAndYear(null,foundBook.getPublisher(),foundBook.getYear());

         // then
         assertThat(founds.size() == 1 );
         assertThat(founds.get(0).equals(foundBook));

    }

    @Test
    public void whenGetAllWithFilters_thenReturnBook() {

        // given
        Book foundBook = BookMock.createBook();
        Map mapParameters = new HashMap();
        Book otherBook = BookMock.createBook();
        otherBook.setGenre(foundBook.getGenre());
        entityManager.persist(foundBook);
        entityManager.persist(otherBook);
        entityManager.persist( BookMock.createBook());
        entityManager.flush();

        // when
        List<Book> founds = bookRepository.getAll(foundBook.getGenre(),foundBook.getPublisher(),foundBook.getYear(),
                foundBook.getAuthor(),foundBook.getPages(),foundBook.getTitle(),foundBook.getSubtitle(),foundBook.getIsbn(),
                foundBook.getImage());

        // then
        assertThat(founds.size() == 1 );
        assertThat(founds.get(0).equals(foundBook));

        // when
        founds = bookRepository.getAll(foundBook.getGenre(),null,null, null, null,
                null, null, null, null);
        // then
        assertThat(founds.size() == 2 );



        // when
        founds = bookRepository.getAll(null,null,null, null, null,
                null, null,  null, null);

        // then
        assertThat(founds.size() == 3 );

    }
}
