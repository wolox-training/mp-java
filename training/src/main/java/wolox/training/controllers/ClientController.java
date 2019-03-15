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
    private ClientRepository clientRepository;


    @GetMapping
    public Iterable findAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client findOne(@PathVariable Long id) {
        return clientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client addBook(@RequestBody Book book, @PathVariable Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        client.addBook(book);
        return client;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        clientRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Client updateBook(@RequestBody Client client, @PathVariable Long id) {
        if (client.getId() != id) {
            throw new UserIdMismatchException();
        }
        clientRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return clientRepository.save(client);
    }
}