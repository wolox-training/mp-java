package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Client;
import wolox.training.repositories.ClientRepository;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository ClientRepository;


    @GetMapping
    public Iterable findAll() {
        return ClientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client findOne(@PathVariable Long id) {
        return ClientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client addBook(@RequestBody Book book, @PathVariable Long id) {
        Client client = ClientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        client.addBook(book);
        return client;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return ClientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ClientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        ClientRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Client updateBook(@RequestBody Client client, @PathVariable Long id) {
        if (client.getId() != id) {
            throw new UserIdMismatchException();
        }
        ClientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ClientRepository.save(client);
    }
}