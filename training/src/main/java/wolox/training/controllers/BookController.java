package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.IBookRepositoryDAO;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private IBookRepositoryDAO IBookRepositoryDAO;



    @GetMapping
    public Iterable findAll() {
        return IBookRepositoryDAO.findAll();
    }

    /*@GetMapping("/author/{bookAuthor}")
    public Book findByAuthor(@PathVariable String bookAuthor) {
        return IBookRepositoryDAO.findByAuthor(bookAuthor);
    }*/

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return IBookRepositoryDAO.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return IBookRepositoryDAO.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        IBookRepositoryDAO.findById(id)
                .orElseThrow(BookNotFoundException::new);
        IBookRepositoryDAO.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        IBookRepositoryDAO.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return IBookRepositoryDAO.save(book);
    }
}