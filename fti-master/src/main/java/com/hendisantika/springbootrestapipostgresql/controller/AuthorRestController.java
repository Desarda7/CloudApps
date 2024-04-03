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

    @Autowired
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(repository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorWithId(@PathVariable Long id) {
        return new ResponseEntity<Author>(repository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<Collection<Author>> findAuthorWithName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(repository.findByName(name), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthorFromDB(@PathVariable("id") long id, @RequestBody Author author) {

        Optional<Author> currentAuthorOpt = repository.findById(id);
        Author currentAuthor = currentAuthorOpt.get();
        currentAuthor.setName(author.getName());
        currentAuthor.setSurname(author.getSurname());
        currentAuthor.setEmail(author.getEmail());
        currentAuthor.setTags(author.getTags());

        return new ResponseEntity<>(repository.save(currentAuthor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthorWithId(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllAuthors() {
        repository.deleteAll();
    }
}