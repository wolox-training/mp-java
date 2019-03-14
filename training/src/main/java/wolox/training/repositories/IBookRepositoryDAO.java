package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

public interface IBookRepositoryDAO extends JpaRepository<Book, Long> {
    Book findByAuthor( String author );
    /*@Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
    Book retrieveByAuthor(@Param("author") String author);*/
}
