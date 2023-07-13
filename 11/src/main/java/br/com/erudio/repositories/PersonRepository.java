package br.com.erudio.repositories;

import br.com.erudio.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    Person disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT ('%', :firstName,'%'))")
    Page<Person> findPersonPagedByName(@Param("firstName") String firstName, Pageable pageable);

}