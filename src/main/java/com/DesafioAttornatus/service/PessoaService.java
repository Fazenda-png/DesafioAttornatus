package com.DesafioAttornatus.service;

import com.DesafioAttornatus.entity.Endereco;
import com.DesafioAttornatus.entity.Pessoa;

import java.util.List;

public interface PessoaService {
    Pessoa salvarPessoa(Pessoa pessoa);

    Pessoa modificarPessoa(Pessoa pessoa, Long id);

    Pessoa buscarPessoaPorId(Long id);

    List<Pessoa> buscarListaPessoas();

    Endereco salvarEndereco(Endereco endereco, Long id);

    List<Endereco> buscasListaEndereco(Long id);

    Endereco buscarEnderecoPrincial(Long id);
}
