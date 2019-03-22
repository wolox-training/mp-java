package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wolox.training.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByAuthor( String author );
    Book findByIsbn( String isbn );
    @Query("SELECT b FROM Book b WHERE (:genre is null or b.genre = :genre) and (:publisher is null or b.publisher = :publisher) and (:year is null or b.year = :year)")
    List<Book> findByGenreAndPublisherAndYear(@Param("genre") String genre, @Param("publisher") String publisher, @Param("year") String year);

    @Query("SELECT b FROM Book b WHERE (:genre is null or b.genre = :genre) and " +
            "(:publisher is null or b.publisher = :publisher) and (:year is null or b.year = :year) and " +
            "(:author is null or b.author = :author) and (:pages is null or b.pages = :pages) and " +
            "(:title is null or b.title = :title) and (:subtitle is null or b.subtitle = :subtitle) and " +
            "(:isbn is null or b.isbn = :isbn) and (:image is null or b.image = :image)")
    List<Book> getAll(@Param("genre") String genre, @Param("publisher") String publisher, @Param("year") String year,
                      @Param("author") String author, @Param("pages") Integer pages, @Param("title") String title,
                      @Param("subtitle") String subtitle, @Param("isbn") String isbn,
                      @Param("image") String image);
}
