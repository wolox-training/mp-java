package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByAuthor( String author );
    Book findByIsbn( String isbn );
    List<Book> findByGenreAndPublisherAndYear(String genre, String publisher, String year);
}

