package com.restcontrol.restcontrol_TC2.domain.entity;

import java.util.UUID;

public class TipoUsuario {
    private UUID id;
    private String nome;

    public TipoUsuario() {
    }

    public TipoUsuario(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static TipoUsuario create(UUID id, String nome) throws IllegalArgumentException {
        if (nome == null) {
            throw new IllegalArgumentException("Faltam dados.");
        }

        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(id);
        tipoUsuario.setNome(nome);

        return tipoUsuario;
    }
}
