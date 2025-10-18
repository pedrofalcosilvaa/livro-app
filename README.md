# Livro App - Spring Boot + H2

Import this project into VS Code (or your IDE of choice).  
Commands:

- To build: `mvn clean package`
- To run: `mvn spring-boot:run`  (or run the main class LivroAppApplication)
- Once running, open: http://localhost:8080/  (frontend)
- H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:livrosdb)

The API endpoints:
- GET  /livros
- GET  /livros/{id}
- POST /livros
- PUT  /livros/{id}
- DELETE /livros/{id}

Validation:
- titulo: obrigatório, max 40 chars
- qtdPaginas: obrigatório, positivo
- publicacao.autor: obrigatório, max 25 chars

