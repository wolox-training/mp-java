package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class ClientController {

    @Autowired
    private ClientRepository ClientRepository;


    /*@GetMapping
    public Iterable findAll() {
        return BookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return BookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return BookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        BookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        BookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        BookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return BookRepository.save(book);
    }*/
}