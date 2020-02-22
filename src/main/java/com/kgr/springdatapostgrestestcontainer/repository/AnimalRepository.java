package com.kgr.springdatapostgrestestcontainer.repository;

import com.kgr.springdatapostgrestestcontainer.entity.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalRepository extends CrudRepository<Animal, Long> {
}
