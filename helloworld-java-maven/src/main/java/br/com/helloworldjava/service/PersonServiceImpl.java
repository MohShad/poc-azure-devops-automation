package br.com.helloworldjava.service;

import br.com.helloworldjava.dto.RequestPersonDTO;
import br.com.helloworldjava.model.Person;
import br.com.helloworldjava.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Long createPerson(RequestPersonDTO requestPersonDTO) {

        Person person = new Person();
        person.setNome(requestPersonDTO.getName());
        person.setEmail(requestPersonDTO.getEmail());
        person.setCreatedAt(new Date());
        personRepository.save(person);
        return person.getId();
    }
}
