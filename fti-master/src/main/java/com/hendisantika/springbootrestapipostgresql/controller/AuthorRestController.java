package com.hendisantika.springbootrestapipostgresql.controller;
import com.hendisantika.springbootrestapipostgresql.entity.Author;
import com.hendisantika.springbootrestapipostgresql.entity.Book;
import com.hendisantika.springbootrestapipostgresql.repository.AuthorRepository;
import com.hendisantika.springbootrestapipostgresql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Collection;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-rest-api-postgresql
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-18
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
    private static final Logger LOGGER = LogManager.getLogger(AuthorRestController.class);

    @Autowired
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        LOGGER.info("Adding author: {}", author);
        Author savedAuthor = repository.save(author);
        LOGGER.info("Author added successfully: {}", savedAuthor);
        return new ResponseEntity<>(repository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        LOGGER.info("Retrieving all authors");
        Collection<Author> authors = repository.findAll();
        LOGGER.info("Found {} authors", authors.size());
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorWithId(@PathVariable Long id) {
        LOGGER.info("Retrieving author with id: {}", id);
        Optional<Author> authorOpt = repository.findById(id);
        if (authorOpt.isPresent()) {
            LOGGER.info("Author found with id {}: {}", id, authorOpt.get());
            return new ResponseEntity<>(authorOpt.get(), HttpStatus.OK);
        } else {
            LOGGER.warn("Author not found with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<Collection<Author>> findAuthorWithName(@RequestParam(value = "name") String name) {
        LOGGER.info("Searching for authors with name: {}", name);
        Collection<Author> authors = repository.findByName(name);
        LOGGER.info("Found {} authors with name: {}", authors.size(), name);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthorFromDB(@PathVariable("id") long id, @RequestBody Author author) {

        LOGGER.info("Updating author with id: {}", id);
        Optional<Author> currentAuthorOpt = repository.findById(id);
        if (currentAuthorOpt.isPresent()) {
            Author currentAuthor = currentAuthorOpt.get();
            currentAuthor.setName(author.getName());
            currentAuthor.setSurname(author.getSurname());
            currentAuthor.setEmail(author.getEmail());
            currentAuthor.setTags(author.getTags());
            Author updatedAuthor = repository.save(currentAuthor);
            LOGGER.info("Author updated successfully: {}", updatedAuthor);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            LOGGER.warn("Author not found with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorWithId(@PathVariable Long id) {
        LOGGER.info("Deleting author with id: {}", id);
        repository.deleteById(id);
        LOGGER.info("Author deleted successfully with id: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllAuthors() {
        LOGGER.info("Deleting all authors");
        repository.deleteAll();
        LOGGER.info("All authors deleted successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }

