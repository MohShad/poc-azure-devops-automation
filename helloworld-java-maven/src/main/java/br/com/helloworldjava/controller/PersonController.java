package br.com.helloworldjava.controller;

import br.com.helloworldjava.dto.RequestPersonDTO;
import br.com.helloworldjava.dto.ResponsePersonDTO;
import br.com.helloworldjava.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/createPerson")
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Register person", produces = "application/json")
    @ApiResponse(code = 200, message = "Person created with success.")
    @PostMapping
    public Object registerPerson(@ApiParam(value = "Person object") @RequestBody RequestPersonDTO requestPersonDTO) {
        Long id = personService.createPerson(requestPersonDTO);
        return new ResponseEntity( new ResponsePersonDTO(true, id, "Person created with success"), HttpStatus.CREATED);
    }
}
