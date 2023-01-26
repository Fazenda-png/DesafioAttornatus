package com.DesafioAttornatus.controller;

import com.DesafioAttornatus.dto.PessoaDTO;
import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;
import com.DesafioAttornatus.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public Pessoa cadastrarPessoa(@RequestBody Pessoa pessoa){
        return pessoaService.salvarPessoa(pessoa);
    }

    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.PATCH)
    public Pessoa atualizarPessoa(@RequestBody Pessoa pessoa, @PathVariable Long id){
        return pessoaService.modificarPessoa(pessoa, id);
    }

    @RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
    public Pessoa buscarPessoa(@PathVariable Long id){
        return pessoaService.buscarPessoaPorId(id);
    }

    @RequestMapping(value = "/buscarPessoas", method = RequestMethod.GET)
    public List<Pessoa> buscarPessoas(){
        return pessoaService.buscarListaPessoas();
    }

    @RequestMapping(value = "/salvar/endereco/{id}", method = RequestMethod.POST)
    public Endereco cadastroEndereco(@RequestBody Endereco endereco, @PathVariable Long id){
        return pessoaService.salvarEndereco(endereco, id);
    }

    @RequestMapping(value = "/endereco/buscarEnderecos/{id}", method = RequestMethod.GET)
    public List<Endereco> buscasEndereco(@PathVariable Long id){
        return pessoaService.buscasListaEndereco(id);
    }

    @RequestMapping(value = "/endereco/buscarPrincial/{id}", method = RequestMethod.GET)
    public Endereco buscarPrincial(@PathVariable Long id){
        return pessoaService.buscarEnderecoPrincial(id);
    }
}
