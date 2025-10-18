package com.example.livroapp.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 40, message = "Título não pode ultrapassar 40 caracteres")
    private String titulo;

    @NotNull(message = "Quantidade de páginas é obrigatória")
    @Positive(message = "Quantidade de páginas deve ser um número positivo")
    private Integer qtdPaginas;

    @Embedded
    @Valid
    private Publicacao publicacao;

    public Livro() {}

    public Livro(String titulo, Integer qtdPaginas, Publicacao publicacao) {
        this.titulo = titulo;
        this.qtdPaginas = qtdPaginas;
        this.publicacao = publicacao;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(Integer qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }
}
