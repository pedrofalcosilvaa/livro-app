package com.example.livroapp.handler;

import com.example.livroapp.handler.ErroResposta;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErroResposta> handleValidation(MethodArgumentNotValidException ex) {
        BindingResult br = ex.getBindingResult();
        List<String> erros = br.getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());
        ErroResposta resp = new ErroResposta(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Erro de validação", erros);
        return ResponseEntity.badRequest().body(resp);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErroResposta> handleNotFound(EntityNotFoundException ex) {
        ErroResposta resp = new ErroResposta(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErroResposta> handleBadRequest(HttpMessageNotReadableException ex) {
        ErroResposta resp = new ErroResposta(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Corpo da requisição inválido", List.of(ex.getMessage()));
        return ResponseEntity.badRequest().body(resp);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErroResposta> handleAll(Exception ex) {
        ErroResposta resp = new ErroResposta(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }
}
