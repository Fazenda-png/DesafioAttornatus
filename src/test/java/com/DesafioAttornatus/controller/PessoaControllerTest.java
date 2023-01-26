package com.DesafioAttornatus.controller;

import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;
import com.DesafioAttornatus.entity.enums.Principal;
import com.DesafioAttornatus.repository.EnderecoRepository;
import com.DesafioAttornatus.repository.PessoaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PessoaControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @BeforeEach
    void up(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));
        pessoaRepository.save(pessoa);
        Endereco endereco = new Endereco();
        endereco.setPessoa(pessoa);
        endereco.setCEP("03451000");
        endereco.setLogradouro("Rua Mariano Cursino de Moura");
        endereco.setCidade("São Paulo");
        endereco.setPrincipal(Principal.Sim);
        endereco.setNumero(420);
        enderecoRepository.save(endereco);
    }

    @Test
    @DisplayName("Deverá salvar a pessoa")
    void cadastrarPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));

        String pessoaTeste = mapper.writeValueAsString(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pessoas/salvar")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(pessoaTeste)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deverá atualizar alguns dados da pessoa")
    void atualizarPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));
        pessoaRepository.save(pessoa);

        Pessoa pessoaAtualizar = new Pessoa();
        pessoaAtualizar.setNome("Teste 2");
        pessoaAtualizar.setDataNascimento(LocalDate.of(1999, 6, 7));

        String pessoaTesteAtualizar = mapper.writeValueAsString(pessoaAtualizar);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/pessoas/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(pessoaTesteAtualizar)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deverá buscar a pessoa pelo id")
    void buscarPessoa() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/buscar/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deverá retornar uma lista de pessoas")
    void buscarPessoas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/buscarPessoas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deverá salvar um endereço para uma pessoa")
    void cadastroEndereco() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste 1");
        pessoa.setDataNascimento(LocalDate.of(1999, 6, 7));
        Endereco endereco = new Endereco();
        endereco.setPessoa(pessoa);
        endereco.setCEP("03451000");
        endereco.setLogradouro("Rua Mariano Cursino de Moura");
        endereco.setCidade("São Paulo");
        endereco.setPrincipal(Principal.Sim);
        endereco.setNumero(420);

        String enderecoTeste = mapper.writeValueAsString(endereco);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pessoas/salvar/endereco/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(enderecoTeste)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deverá buscar o endereços pelo id")
    void buscasEndereco() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/endereco/buscarEnderecos/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deverá retornar o endereço setado como principal")
    void buscarPrincial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/endereco/buscarPrincial/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}