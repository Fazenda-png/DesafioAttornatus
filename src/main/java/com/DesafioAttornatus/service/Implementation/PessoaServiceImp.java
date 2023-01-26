package com.DesafioAttornatus.service.Implementation;

import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;
import com.DesafioAttornatus.exception.ResourceNotFoundException;
import com.DesafioAttornatus.repository.EnderecoRepository;
import com.DesafioAttornatus.repository.PessoaRepository;
import com.DesafioAttornatus.service.PessoaService;
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
        Optional<Pessoa> pessoaAtualiza = pessoaRepository.findById(id);
        if (pessoaAtualiza.isPresent()){
            atualizarDados(pessoaAtualiza.get(), pessoa);
            return pessoaRepository.save(pessoaAtualiza.get());
        } else{
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
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            adicionarEndereco(endereco, pessoa.get());
            return endereco;
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    private void adicionarEndereco(Endereco endereco, Pessoa pessoa) {
        List<Endereco> enderecoList = new ArrayList<>();
        endereco.setPessoa(pessoa);
        enderecoList.add(endereco);
        pessoaRepository.save(pessoa);
        enderecoRepository.saveAll(enderecoList);
    }

    @Override
    public List<Endereco> buscasListaEndereco(Long id) {
        Pessoa pessoa = pessoaRepository.getOne(id);
        if (!pessoa.getEndereco().isEmpty()) {
            return pessoa.getEndereco();
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public Endereco buscarEnderecoPrincial(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            return pessoa.get().getEndereco().stream().filter(enderecoUnico -> enderecoUnico.getPrincipal().getCode().equals("Sim")).findAny().get();
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
