package ru.naumov.FabricAppBoot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumov.FabricAppBoot.models.Person;
import ru.naumov.FabricAppBoot.models.Task;
import ru.naumov.FabricAppBoot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    public Optional<Person> findByFullName(String name){
        return peopleRepository.findByFullName(name);
    }


    public List<Task> getTasksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent())
            return person.get().getTasks();
        else
            return Collections.emptyList();
    }
}
