package com.DesafioAttornatus.controller;

import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;
import com.DesafioAttornatus.exception.ResourceNotFoundException;
import com.DesafioAttornatus.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public ResponseEntity<Pessoa> cadastrarPessoa(@RequestBody Pessoa pessoa){
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvarPessoa(pessoa));
    }

    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Pessoa> atualizarPessoa(@RequestBody Pessoa pessoa, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.modificarPessoa(pessoa, id));
    }

    @RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarPessoaPorId(id));
    }

    @RequestMapping(value = "/buscarPessoas", method = RequestMethod.GET)
    public ResponseEntity<List<Pessoa>> buscarPessoas(){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarListaPessoas());
    }

    @RequestMapping(value = "/salvar/endereco/{id}", method = RequestMethod.POST)
    public ResponseEntity<Endereco>  cadastroEndereco(@RequestBody Endereco endereco, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.salvarEndereco(endereco, id));
    }

    @RequestMapping(value = "/endereco/buscarEnderecos/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Endereco>> buscasEndereco(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscasListaEndereco(id));
    }

    @RequestMapping(value = "/endereco/buscarPrincial/{id}", method = RequestMethod.GET)
    public ResponseEntity<Endereco>  buscarPrincial(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarEnderecoPrincial(id));
    }
}
