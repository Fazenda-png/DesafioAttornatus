package com.DesafioAttornatus.entity;

import com.DesafioAttornatus.entity.enums.Principal;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String logradouro;
    private String CEP;
    private Integer numero;
    private String cidade;

    private Principal principal;
    @ManyToOne
    @JoinColumn(name="pessoa")
    private Pessoa pessoa;


    public Endereco() {
    }

    public Endereco(Long id, String logradouro, String CEP, Integer numero, String cidade, Principal principal, Pessoa pessoa) {
        this.id = id;
        this.logradouro = logradouro;
        this.CEP = CEP;
        this.numero = numero;
        this.cidade = cidade;
        this.principal = principal;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa_id) {
        this.pessoa = pessoa_id;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}
