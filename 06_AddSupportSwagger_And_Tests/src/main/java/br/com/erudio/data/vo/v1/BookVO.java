package br.com.erudio.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonPropertyOrder({ })
public class BookVO extends RepresentationModel<BookVO> implements Serializable {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;

    private String author;

    private LocalDateTime lauchDate;

    private BigDecimal price;

    private String title;

    public BookVO() {}

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getLauchDate() {
        return lauchDate;
    }

    public void setLauchDate(LocalDateTime lauchDate) {
        this.lauchDate = lauchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookVO booksVO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(key, booksVO.key) && Objects.equals(author, booksVO.author) && Objects.equals(lauchDate, booksVO.lauchDate) && Objects.equals(price, booksVO.price) && Objects.equals(title, booksVO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, author, lauchDate, price, title);
    }
}
