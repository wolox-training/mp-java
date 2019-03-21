package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import wolox.training.exceptions.BookAlreadyOwnedException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();




    public Client() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Preconditions.checkArgument(username != null && !username.isEmpty());
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {

        this.birthdate = Preconditions.checkNotNull(birthdate);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Preconditions.checkNotNull(password);;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books) ;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(username, client.username) &&
                Objects.equals(birthdate, client.birthdate) &&
                Objects.equals(books, client.books);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, birthdate, books);
    }
}

