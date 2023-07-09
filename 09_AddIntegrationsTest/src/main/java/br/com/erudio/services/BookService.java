package br.com.erudio.services;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookVO.class.getName());

    @Autowired
    private BooksRepository repository;


    public BookVO findById(Long id) {

        logger.info("Finding a book");

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        BookVO book = DozerMapper.parseObject(entity, BookVO.class);
        book.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return book;
    }


    public List<BookVO> findAll() {

        logger.info("Finding all books");

        List<BookVO> books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books.forEach(book -> book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel()));
        return books;
    }

    public BookVO create(BookVO book) {

        if (Objects.isNull(book)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Adding a new book");
        Book entity = DozerMapper.parseObject(book, Book.class);
        BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {
        if (Objects.isNull(book)) {
            throw new RequiredObjectIsNullException();
        }
        logger.info("Updating book");
        Book entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLauchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new RequiredObjectIsNullException();
        }

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        logger.info("Deleting book");
        repository.delete(book);
    }
}
