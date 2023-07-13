package br.com.erudio.services;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;

	@Autowired
	PagedResourcesAssembler<PersonVO> assembler;

	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

		logger.info("Finding all people!");

		Page<Person> personPage = repository.findAll(pageable);
		Page<PersonVO> personsVOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personsVOsPage.map(p -> p.add(linkTo((methodOn(PersonController.class).findById(p.getKey()))).withSelfRel()));

		Link link = linkTo(methodOn(PersonController.class)
				.findAll(pageable
						.getPageNumber(), pageable.getPageSize(), "asc"
				)).withSelfRel();

		return assembler.toModel(personsVOsPage, link);
	}

	public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstname, Pageable pageable) {

		logger.info("Finding all people!");

		Page<Person> personPage = repository.findPersonPagedByName(firstname, pageable);
		Page<PersonVO> personsVOsPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personsVOsPage.map(p -> p.add(linkTo((methodOn(PersonController.class).findById(p.getKey()))).withSelfRel()));

		Link link = linkTo(methodOn(PersonController.class)
				.findAll(pageable
						.getPageNumber(), pageable.getPageSize(), "asc"
				)).withSelfRel();

		return assembler.toModel(personsVOsPage, link);
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo((methodOn(PersonController.class).findById(id))).withSelfRel());
		return  vo;
	}

	@Transactional
	public PersonVO disablePerosn(Long id) {

		logger.info("Finding one person!");
		repository.disablePerson(id);
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo((methodOn(PersonController.class).findById(id))).withSelfRel());
		return  vo;
	}

	public PersonVO create(PersonVO person) {

		if (Objects.isNull(person)) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo((methodOn(PersonController.class).findById(vo.getKey()))).withSelfRel());
		return vo;
	}

	public PersonVO update(PersonVO person) {

		if (Objects.isNull(person)) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Updating one person!");

		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo((methodOn(PersonController.class).findById(vo.getKey()))).withSelfRel());
		return vo;
	}

	public void delete(Long id) {

		logger.info("Deleting one person!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
