package com.example.livroapp.controller;

import com.example.livroapp.model.Livro;
import com.example.livroapp.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository repo;

    public LivroController(LivroRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Livro> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Livro buscar(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com id " + id));
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {
        Livro salvo = repo.save(livro);
        return ResponseEntity.created(URI.create("/livros/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        return repo.findById(id).map(existing -> {
            existing.setTitulo(livro.getTitulo());
            existing.setQtdPaginas(livro.getQtdPaginas());
            existing.setPublicacao(livro.getPublicacao());
            return repo.save(existing);
        }).orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Livro não encontrado com id " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
