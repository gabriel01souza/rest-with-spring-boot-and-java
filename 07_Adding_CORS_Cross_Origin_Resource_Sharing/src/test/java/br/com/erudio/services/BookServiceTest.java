package br.com.erudio.services;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BooksRepository;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    private BooksRepository repository;

    @BeforeEach
    public void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findById() {
        Book book = input.mockEntity(1);

        Mockito.when(repository.findById(book.getId())).thenReturn(Optional.of(book));

        BookVO result = service.findById(book.getId());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/books/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Author test", result.getAuthor());
        Assertions.assertEquals(BigDecimal.valueOf(10), result.getPrice());
        LocalDateTime dataExpected = LocalDateTime.of(2020, 1, 1, 5, 0);
        Assertions.assertEquals(dataExpected, result.getLauchDate());
        Assertions.assertEquals("Title test", result.getTitle());
    }

    @Test
    public void create() {
        Book entity = input.mockEntity(1);
        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/books/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Author test", result.getAuthor());
        Assertions.assertEquals(BigDecimal.valueOf(10), result.getPrice());
        LocalDateTime dataExpected = LocalDateTime.of(2020, 1, 1, 5, 0);
        Assertions.assertEquals(dataExpected, result.getLauchDate());
        Assertions.assertEquals("Title test", result.getTitle());
    }

    @Test
    public void createWithObjectNull() {
        Exception ex = Assertions.assertThrowsExactly(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        Assertions.assertEquals("It's not allowed to persist a null object!", ex.getMessage());
    }

    @Test
    public void updateWithObjectNull() {
        Exception ex = Assertions.assertThrowsExactly(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        Assertions.assertEquals("It's not allowed to persist a null object!", ex.getMessage());
    }

    @Test
    public void update() {
        Book entity = input.mockEntity(1);
        Book persisted = input.mockEntity(1);
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/books/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Author test", result.getAuthor());
        Assertions.assertEquals(BigDecimal.valueOf(10), result.getPrice());
        LocalDateTime dataExpected = LocalDateTime.of(2020, 1, 1, 5, 0);
        Assertions.assertEquals(dataExpected, result.getLauchDate());
        Assertions.assertEquals("Title test", result.getTitle());
    }

    @Test
    public void delete() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    public void findAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(14, result.size());

        BookVO vo1 = result.get(0);

        Assertions.assertNotNull(vo1);
        Assertions.assertNotNull(vo1.getKey());
        Assertions.assertNotNull(vo1.getLinks());
        Assertions.assertEquals("links: [</api/books/v1/0>;rel=\"self\"]", vo1.toString());
        Assertions.assertEquals("Author test", vo1.getAuthor());
        Assertions.assertEquals(BigDecimal.valueOf(10), vo1.getPrice());
        LocalDateTime dataExpected = LocalDateTime.of(2020, 1, 1, 5, 0);
        Assertions.assertEquals(dataExpected, vo1.getLauchDate());
        Assertions.assertEquals("Title test", vo1.getTitle());

        BookVO vo7 = result.get(6);

        Assertions.assertNotNull(vo7);
        Assertions.assertNotNull(vo7.getKey());
        Assertions.assertNotNull(vo7.getLinks());
        Assertions.assertEquals("links: [</api/books/v1/6>;rel=\"self\"]", vo7.toString());
        Assertions.assertEquals("Author test", vo7.getAuthor());
        Assertions.assertEquals(BigDecimal.valueOf(10), vo7.getPrice());
        LocalDateTime dataExpected2 = LocalDateTime.of(2020, 1, 1, 5, 0);
        Assertions.assertEquals(dataExpected2, vo7.getLauchDate());
        Assertions.assertEquals("Title test", vo7.getTitle());
    }
}
