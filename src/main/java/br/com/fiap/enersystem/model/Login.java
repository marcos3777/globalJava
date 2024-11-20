package br.com.fiap.enersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Login {

    private long id;
    private String cnpj;

    @JsonIgnore //usado para ignorar o campo na serialização
    private String senha;


    private Empresa empresa;
    private String status;

    public Login() {
    }

    public Login(long id, String cnpj, String senha, Empresa empresa, String status) {
        this.id = id;
        this.cnpj = cnpj;
        this.senha = senha;
        this.empresa = empresa;
        this.status = status;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
