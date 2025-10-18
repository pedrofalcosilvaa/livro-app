package com.example.livroapp.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Embeddable
public class Publicacao {

    @NotBlank(message = "Autor é obrigatório")
    @Size(max = 25, message = "Autor não pode ultrapassar 25 caracteres")
    private String autor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPublicacao;

    private String editora;

    public Publicacao() {}

    public Publicacao(String autor, LocalDate dataPublicacao, String editora) {
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.editora = editora;
    }

    // getters and setters

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }
}
