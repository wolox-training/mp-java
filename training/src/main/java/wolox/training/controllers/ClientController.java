package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import wolox.training.exceptions.ClientIdMismatchException;
import wolox.training.exceptions.ClientNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Client;
import wolox.training.repositories.ClientRepository;

import java.security.Principal;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping
    public Iterable findAll(@RequestParam(required = false) String username, @RequestParam(required = false) LocalDate from, @RequestParam(required = false) LocalDate to) {
        return clientRepository.getAll(username,from,to);
    }

    @GetMapping("/{id}")
    public Client findOne(@PathVariable Long id) {
        return clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }

    @GetMapping("/me")
    public Client me(Authentication authentication) {
        return clientRepository.findByUsername(authentication.getName());
    }

    @PostMapping("/{id}")
    public Client addBook(@RequestBody Book book, @PathVariable Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
        client.addBook(book);
        return client;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
        clientRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Client updateBook(@RequestBody Client client, @PathVariable Long id) {
        if (client.getId() != id) {
            throw new ClientIdMismatchException();
        }
        clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
        return clientRepository.save(client);
    }
}