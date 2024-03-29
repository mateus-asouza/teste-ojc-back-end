package com.ms.personApi.controller;

import com.ms.personApi.dto.request.CustomerDto;
import com.ms.personApi.entity.Customer;
import com.ms.personApi.exception.PersonNotFoundException;
import com.ms.personApi.service.UserService;
import lombok.AllArgsConstructor;
import com.ms.personApi.dto.response.messageResponseDTO;
import com.ms.personApi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/customer")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private CustomerService customerService;//injeção de dependência interface PersonService.
    private UserService userService;

    /*
       Metodo que recebe requisições post, mapea o jason enviado na requisição em um objeto tipo PersonDto
       e retorna a chamada do metodo creatPerson da interface personService passando o objeto personDto
       como parametro e caso sucesso, retorna o status CREATED do enum HttpsStatus.
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createPerson(@RequestBody CustomerDto customerDto) {

        return customerService.createCustomer(customerDto);
    }

    @GetMapping(value = "/search")
    public Page<Customer> search(@RequestParam(required = false) String dataCriacao,
                                 @RequestParam(required = false) String nomeOrRazaoSocial,
                                 @RequestParam(required = false) String cpfOrCnpj,
                                 @RequestParam String isCpfOrCnpj,
                                 @RequestParam(value = "page",
                                         required = false,
                                         defaultValue = "0") int page,
                                 @RequestParam(value = "size",
                                         required = false,
                                         defaultValue = "10") int size) {

        if(Objects.equals(isCpfOrCnpj, "cpf")){
        return customerService.serachPhysicalCustomer(dataCriacao,nomeOrRazaoSocial,cpfOrCnpj,page,size);
        }else if(Objects.equals(isCpfOrCnpj, "cnpj")){

         return customerService.serachLegalCustomer(dataCriacao,nomeOrRazaoSocial,cpfOrCnpj,page,size);

        }
        return customerService.findAll(page,size);
    }

    //Retorna uma lista com todos os registros.
    @GetMapping
    public List<CustomerDto> getCustomers() {
        return customerService.listAll();
    }

    //Pesquisa um registro pelo id e retorna o registro com o id informado.
    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) throws PersonNotFoundException {
        return customerService.getCustomerById(id);
    }

    //Atualiza um registro correspondente ao id informado.
    @PutMapping("/{id}")
    public messageResponseDTO updateCustomerById(@PathVariable Long id, @RequestBody @Valid CustomerDto customerDto) throws PersonNotFoundException {
        return customerService.updateCustomerById(id, customerDto);
    }

    //Deleta um registro correspondente ao id informado.
    @DeleteMapping("/{id}")
    public messageResponseDTO deleteCustomerById(@PathVariable Long id) throws PersonNotFoundException {
        return customerService.deleteCustomerById(id);
    }

}
