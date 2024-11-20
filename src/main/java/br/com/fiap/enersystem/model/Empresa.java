package br.com.fiap.enersystem.model;

import java.io.Serializable;

public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String nome;
    private String cnpj;
    private String estado;
    private String email;
    private int kwh;
    private String tipoEnergia;

    public Empresa(long id, String nome, String cnpj, String email, String estado, int kwh, String tipoEnergia) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.estado = estado;
        this.kwh = kwh;
        this.tipoEnergia = tipoEnergia;
    }



    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + estado + '\'' +
                ", kwh=" + kwh +
                ", tipoEnergia='" + tipoEnergia + '\'' +
                '}';
    }

    public Empresa() {

    }


    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getKwh() {
        return kwh;
    }

    public void setKwh(int kwh) {
        this.kwh = kwh;
    }

    public String getTipoEnergia() {
        return tipoEnergia;
    }

    public void setTipoEnergia(String tipoEnergia) {
        this.tipoEnergia = tipoEnergia;
    }


}
