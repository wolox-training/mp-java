package wolox.training.models;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String Title;

    @Column(nullable = false)
    private String Author;

    @Column(nullable = false)
    private String Image;

    @Column(nullable = false)
    private String Subtitle;

    @Column(nullable = false)
    private String Publisher;

    @Column(nullable = false)
    private String Year;

    @Column(nullable = false)
    private int Pages;

    @Column(nullable = false)
    private String isbn;

    @Column()
    private String Genre;

    public Book() {
    }

    public Book(long id, String title, String author, String image, String subtitle, String publisher, String year, int pages, String isbn, String genre) {
        this.id = id;
        Title = title;
        Author = author;
        Image = image;
        Subtitle = subtitle;
        Publisher = publisher;
        Year = year;
        Pages = pages;
        this.isbn = isbn;
        Genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public int getPages() {
        return Pages;
    }

    public void setPages(int pages) {
        Pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
