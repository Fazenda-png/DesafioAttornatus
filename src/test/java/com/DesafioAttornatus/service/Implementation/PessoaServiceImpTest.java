package com.DesafioAttornatus.service.Implementation;

import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;
import com.DesafioAttornatus.exception.ResourceNotFoundException;
import com.DesafioAttornatus.repository.EnderecoRepository;
import com.DesafioAttornatus.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceImpTest {

    @InjectMocks
    PessoaServiceImp pessoaServiceImp;
    @Mock
    PessoaRepository pessoaRepository;
    @Mock
    EnderecoRepository enderecoRepository;

    @Captor
    ArgumentCaptor<Pessoa> pessoaArgumentCaptor;


    @Test
    @DisplayName("Deverá salvar a pessoa com sucesso")
    void salvarPessoaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));

        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        pessoaServiceImp.salvarPessoa(pessoa);

        Mockito.verify(pessoaRepository).save(pessoa);
    }

    @Test
    @DisplayName("Deverá modificar os dados(Nome) da pessoa com sucesso")
    void modificarNomePessoaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));

        Pessoa pessoaAtualizar = new Pessoa();
        pessoaAtualizar.setNome("Teste Atualizado");
        pessoaAtualizar.setDataNascimento(LocalDate.of(1999, 6, 7));

        Mockito.when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        pessoaServiceImp.modificarPessoa(pessoaAtualizar, pessoa.getId());

        Mockito.verify(pessoaRepository).save(pessoaArgumentCaptor.capture());
        Pessoa pessoaAtualizada = pessoaArgumentCaptor.getValue();

        Assertions.assertThat(pessoaAtualizar.getNome()).isEqualTo(pessoaAtualizada.getNome());
    }

    @Test
    @DisplayName("Deverá modificar os dados(Data de nascimento) da pessoa com sucesso")
    void modificarDataNascimentoPessoaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));

        Pessoa pessoaAtualizar = new Pessoa();
        pessoaAtualizar.setNome("Teste 1");
        pessoaAtualizar.setDataNascimento(LocalDate.of(2001, 6, 10));

        Mockito.when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        pessoaServiceImp.modificarPessoa(pessoaAtualizar, pessoa.getId());

        Mockito.verify(pessoaRepository).save(pessoaArgumentCaptor.capture());
        Pessoa pessoaAtualizada = pessoaArgumentCaptor.getValue();

        Assertions.assertThat(pessoaAtualizar.getDataNascimento()).isEqualTo(pessoaAtualizada.getDataNascimento());
    }

    @Test
    @DisplayName("Deverá retornar um pessoa com base no seu id")
    void buscarPessoaPorIdComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));

        Mockito.when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));

        pessoaServiceImp.buscarPessoaPorId(pessoa.getId());

        Mockito.verify(pessoaRepository).findById(pessoa.getId());
    }

    @Test
    @DisplayName("Deverá retornar todas as pessoas armazenadas no banco")
    void buscarListaPessoasComSucesso() {
        List<Pessoa> pessoaList = new ArrayList<>();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));
        pessoaList.add(pessoa);

        Mockito.when(pessoaRepository.findAll()).thenReturn(pessoaList);

        pessoaServiceImp.buscarListaPessoas();

        Mockito.verify(pessoaRepository).findAll();
    }

    @Test
    @DisplayName("Deverá salvar o endereço da pessoa com sucesso")
    void salvarEnderecoComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));
        List<Endereco> enderecoList = new ArrayList<>();
        Endereco endereco = new Endereco();
        endereco.setPessoa(pessoa);
        endereco.setCEP("03451000");
        endereco.setLogradouro("Rua Mariano Cursino de Moura");
        endereco.setCidade("São Paulo");
        endereco.setNumero(420);
        enderecoList.add(endereco);

        Mockito.when(pessoaRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
        Mockito.when(enderecoRepository.saveAll(enderecoList)).thenReturn(enderecoList);

        pessoaServiceImp.salvarEndereco(endereco, pessoa.getId());

        Mockito.verify(enderecoRepository).saveAll(enderecoList);
    }

    @Test
    @DisplayName("Deverá retornar uma exception (valido para todos os metodos pois todos realizam essa verificação)")
    void pessoaNaoEstaPresente(){
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            pessoaServiceImp.buscarPessoaPorId(1L);
        });

        Assertions.assertThat(resourceNotFoundException.getMessage()).isEqualTo("Pessoa com a identificação: " + 1L + " não foi encontrada");
    }

}