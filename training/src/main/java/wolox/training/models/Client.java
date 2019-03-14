package wolox.training.models;

import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private LocalDate birthdate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_client",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "client_id",
                    referencedColumnName = "id"))
    private List<Book> books = new ArrayList<>();

    public Client() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

   public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableCollection(books) ;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public void addBook(Book book) {
        if (books.contains(book)) throw new BookAlreadyOwnedException();
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

}

