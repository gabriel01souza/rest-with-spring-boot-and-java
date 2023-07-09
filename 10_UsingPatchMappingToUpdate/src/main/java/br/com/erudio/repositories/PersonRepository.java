package br.com.erudio.repositories;

import br.com.erudio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p SET p.inabled = false WHERE p.id =:id")
    Person disablePerson(@Param("id") Long id);

}