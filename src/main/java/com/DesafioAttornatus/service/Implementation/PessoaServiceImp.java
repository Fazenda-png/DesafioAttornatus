package com.DesafioAttornatus.service;

import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;
import com.DesafioAttornatus.exception.ResourceNotFoundException;
import com.DesafioAttornatus.repository.EnderecoRepository;
import com.DesafioAttornatus.repository.PessoaRepository;
import com.DesafioAttornatus.service.PessoaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImp implements PessoaService {


    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;


    @Autowired
    public PessoaServiceImp(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa modificarPessoa(Pessoa pessoa, Long id) {
        try {
            Pessoa pessoaAtualiza = pessoaRepository.findById(id).get();
            atualizarDados(pessoaAtualiza, pessoa);
            return pessoaRepository.save(pessoaAtualiza);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void atualizarDados(Pessoa pessoaAtualiza, Pessoa pessoa) {
        pessoaAtualiza.setNome(pessoa.getNome());
        pessoaAtualiza.setDataNascimento(pessoa.getDataNascimento());
    }

    @Override
    public Pessoa buscarPessoaPorId(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public List<Pessoa> buscarListaPessoas() {
        return pessoaRepository.findAll();
    }

    @Override
    public Endereco salvarEndereco(Endereco endereco, Long id) {
        try {
            Pessoa pessoa = pessoaRepository.findById(id).get();
            adicionarEndereco(endereco, pessoa);
            return endereco;
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private List<Endereco> adicionarEndereco(Endereco endereco, Pessoa pessoa) {
        List<Endereco> enderecoList = new ArrayList<>();
        endereco.setPessoa(pessoa);
        enderecoList.add(endereco);
        pessoaRepository.save(pessoa);
        return enderecoRepository.saveAll(enderecoList);
    }

    @Override
    public List<Endereco> buscasListaEndereco(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        return pessoa.getEndereco();
    }

    @Override
    public Endereco buscarEnderecoPrincial(Long id) {
        List<Endereco> enderecoList = enderecoRepository.findAll();
        return enderecoList.stream().filter(enderecoUnico -> enderecoUnico.getPrincipal().getCode().equals("Sim")).findAny().get();
    }
}
