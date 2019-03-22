package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @GetMapping
    public Iterable findAll(@RequestParam(required = false) String genre, @RequestParam(required = false) String publisher,
                                    @RequestParam(required = false) String year, @RequestParam(required = false) String author,
                                    @RequestParam(required = false) Integer pages, @RequestParam(required = false) String title,
                                    @RequestParam(required = false) String subtitle, @RequestParam(required = false) String isbn,
                                    @RequestParam(required = false) String image) {
        return bookRepository.getAll(genre,publisher,year,author,pages,title,subtitle,isbn,image);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @GetMapping("/isbn/{isbn}")
    public Book findOneByISBN(@PathVariable String isbn) {
        Book book = null;

        book =  bookRepository.findByIsbn(isbn);
        if (book == null){
            book = openLibraryService.bookInfo(isbn);
            if (book == null) throw new BookNotFoundException();
            bookRepository.save(book);
        }

        return book;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
}