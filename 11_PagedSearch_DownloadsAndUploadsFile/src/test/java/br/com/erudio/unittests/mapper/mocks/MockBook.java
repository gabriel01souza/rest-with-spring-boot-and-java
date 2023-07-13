package br.com.erudio.unittests.mapper.mocks;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.model.Book;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);

    }
    public BookVO mockVO() {
        return mockVO(0);
    }

    public Book mockEntity(Integer id) {
        Book book = new Book();
        book.setId(id.longValue());
        book.setAuthor("Author test");
        book.setPrice(BigDecimal.valueOf(10));
        book.setTitle("Title test");
        book.setLaunchDate(LocalDateTime.of(2020, 1, 1,5,0));
        return book;
    }

    public BookVO mockVO(Integer id) {
        BookVO book = new BookVO();
        book.setKey(id.longValue());
        book.setAuthor("Author test");
        book.setPrice(BigDecimal.valueOf(10));
        book.setTitle("Title test");
        book.setLauchDate(LocalDateTime.of(2020, 1,1,5,0));
        return book;
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<BookVO>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
}
