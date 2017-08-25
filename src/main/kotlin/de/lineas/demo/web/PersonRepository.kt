package de.lineas.demo.web

import de.lineas.demo.model.Person
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "person", path = "person")
interface PersonRepository : PagingAndSortingRepository<Person, Long> {

}