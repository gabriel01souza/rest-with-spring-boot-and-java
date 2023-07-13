package br.com.erudio.integrationtests.vo.wrappers.Person;

import br.com.erudio.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class PersonEmbedderdVO implements Serializable {

    @JsonProperty("personVOList")
    private List<PersonVO> people;

    public PersonEmbedderdVO() {
    }

    public List<PersonVO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonVO> people) {
        this.people = people;
    }
}
