package wolox.training.models;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private String isbn;
    
    private String genre;

    @ManyToMany(mappedBy = "books")
    private List<Client> clients;


    public Book() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title);
        Preconditions.checkArgument(!title.isEmpty());
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author);
        Preconditions.checkArgument(!author.isEmpty());
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image);
        Preconditions.checkArgument(!image.isEmpty());
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle);
        Preconditions.checkArgument(!subtitle.isEmpty());
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher);
        Preconditions.checkArgument(!publisher.isEmpty());
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year);
        Preconditions.checkArgument(!year.isEmpty());
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn);
        Preconditions.checkArgument(!isbn.isEmpty());
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
