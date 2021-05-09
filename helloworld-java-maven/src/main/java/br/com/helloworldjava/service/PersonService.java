package br.com.helloworldjava.service;

import br.com.helloworldjava.dto.RequestPersonDTO;

public interface PersonService {

    Long createPerson(RequestPersonDTO requestPersonDTO);
}
