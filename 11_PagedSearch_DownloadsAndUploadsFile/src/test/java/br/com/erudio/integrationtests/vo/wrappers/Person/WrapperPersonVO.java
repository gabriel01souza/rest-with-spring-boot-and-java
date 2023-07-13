package br.com.erudio.integrationtests.vo.wrappers.Person;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class WrapperPersonVO implements Serializable {

    @JsonProperty("_embedded")
    private PersonEmbedderdVO embedderdVO;


    public WrapperPersonVO() {
    }

    public PersonEmbedderdVO getEmbeddedVO() {
        return embedderdVO;
    }

    public void setEmbedderdVO(PersonEmbedderdVO embedderdVO) {
        this.embedderdVO = embedderdVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WrapperPersonVO)) return false;
        WrapperPersonVO that = (WrapperPersonVO) o;
        return Objects.equals(embedderdVO, that.embedderdVO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(embedderdVO);
    }
}
