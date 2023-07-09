package br.com.erudio.services;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    private PersonRepository repository;

    @Test
    public void findById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/person/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
    }

    @Test
    public void disablePerson() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/person/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
        Assertions.assertEquals(Boolean.FALSE, result.isEnabled());
    }

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void create() {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/person/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
    }

    @Test
    public void createWithObjectNull() {
        Exception ex = Assertions.assertThrowsExactly(RequiredObjectIsNullException.class,  () -> {
           service.create(null);
        });

        Assertions.assertEquals("It's not allowed to persist a null object!", ex.getMessage());
    }

    @Test
    public void updateWithObjectNull() {
        Exception ex = Assertions.assertThrowsExactly(RequiredObjectIsNullException.class,  () -> {
            service.update(null);
        });

        Assertions.assertEquals("It's not allowed to persist a null object!", ex.getMessage());
    }

    @Test
    public void update() {
        Person entity = input.mockEntity(1);
        Person persisted =  input.mockEntity(1);
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertEquals("links: [</api/person/v1/1>;rel=\"self\"]", result.toString());
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
    }

    @Test
    public void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    public void findAll() {
        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var result = service.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(14, result.size());

        PersonVO vo1 = result.get(0);

        Assertions.assertNotNull(vo1.getKey());
        Assertions.assertNotNull(vo1.getLinks());
        Assertions.assertEquals("links: [</api/person/v1/0>;rel=\"self\"]", vo1.toString());
        Assertions.assertEquals("Addres Test0", vo1.getAddress());
        Assertions.assertEquals("First Name Test0", vo1.getFirstName());
        Assertions.assertEquals("Last Name Test0", vo1.getLastName());
        Assertions.assertEquals("Male", vo1.getGender());

        PersonVO vo7 = result.get(6);

        Assertions.assertNotNull(vo7.getKey());
        Assertions.assertNotNull(vo7.getLinks());
        Assertions.assertEquals("links: [</api/person/v1/6>;rel=\"self\"]", vo7.toString());
        Assertions.assertEquals("Addres Test6", vo7.getAddress());
        Assertions.assertEquals("First Name Test6", vo7.getFirstName());
        Assertions.assertEquals("Last Name Test6", vo7.getLastName());
        Assertions.assertEquals("Male", vo7.getGender());
    }

}
