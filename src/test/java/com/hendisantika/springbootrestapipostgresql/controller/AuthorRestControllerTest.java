import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skerdians.cloudlab.entities.Author;
import org.skerdians.cloudlab.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorRestControllerTest {

    @InjectMocks
    AuthorController authorController;

    @Mock
    AuthorService authorService;

    @Test
    public void testAddAuthor() {
        Author author = new Author();
        author.setId(1L);

        when(authorService.addAuthor(any(Author.class))).thenReturn(author);

        ResponseEntity<Author> response = authorController.addAuthor(author);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(new Author(), new Author());

        when(authorService.getAllAuthors()).thenReturn(authors);

        ResponseEntity<Collection<Author>> response = authorController.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testGetAuthorWithId() {
        Author author = new Author();
        author.setId(1L);

        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));

        ResponseEntity<Author> response = authorController.getAuthorWithId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testGetAuthorWithInvalidId() {
        Long invalidId = 100L; 
        when(authorService.getAuthorById(invalidId)).thenReturn(Optional.empty());

        ResponseEntity<Author> response = authorController.getAuthorWithId(invalidId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testFindAuthorWithName() {
        List<Author> authors = Arrays.asList(new Author(), new Author());

        when(authorService.findAuthorByName(anyString())).thenReturn(authors);

        ResponseEntity<Collection<Author>> response = authorController.findAuthorWithName("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    public void testUpdateAuthorFromDB() {
        Author author = new Author();
        author.setId(1L);

        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(author);

        ResponseEntity<Author> response = authorController.updateAuthorFromDB(1L, author);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testUpdateAuthorFromDBWithInvalidId() {
        long invalidId = 999L;
        Author author = new Author();
        author.setId(invalidId);
        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(null);

        ResponseEntity<Author> response = authorController.updateAuthorFromDB(invalidId, author);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorService, times(1)).updateAuthor(anyLong(), any(Author.class));
    }

    @Test
    public void testDeleteAuthorWithId() {
        doNothing().when(authorService).deleteAuthorById(anyLong());

        ResponseEntity<Void> response = authorController.deleteAuthorWithId(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAllAuthors() {
        doNothing().when(authorService).deleteAllAuthors();

        ResponseEntity<Void> response = authorController.deleteAllAuthors();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}