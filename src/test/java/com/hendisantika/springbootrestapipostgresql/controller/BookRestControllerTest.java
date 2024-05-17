import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookRestControllerTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookRestController controller;

    @Test
    void addBook() {
        Book book = new Book("title", "description", Collections.emptyList());
        when(repository.save(book)).thenReturn(book);

        ResponseEntity<Book> response = controller.addBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void getAllBooks() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<Book>> response = controller.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void getBookWithId() {
        Book book = new Book("title", "description", Collections.emptyList());
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = controller.getBookWithId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void getBookWithId_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = controller.getBookWithId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findBookWithName() {
        when(repository.findByName("name")).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<Book>> response = controller.findBookWithName("name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }
}