package br.edu.fiap.api.controller;

import br.edu.fiap.api.controller.dto.CategoriaRequest;
import br.edu.fiap.api.controller.dto.CategoriaResponse;
import br.edu.fiap.api.entity.Categoria;
import br.edu.fiap.api.exception.CategoriaNaoEncontradaException;
import br.edu.fiap.api.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Camada web que expõe o contrato HTTP de categorias.
 */
@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias")
public class CategoriaController {

    private final CategoriaRepository repository;

    /**
     * Cria o controller com o repositório de categorias.
     *
     * @param repository repositório de categorias
     */
    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todas as categorias cadastradas.
     *
     * @return representações das categorias cadastradas
     */
    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    public List<CategoriaResponse> listar() {
        return repository.findAll().stream().map(CategoriaResponse::de).toList();
    }

    /**
     * Busca uma categoria pelo ID.
     *
     * @param id identificador recebido na URI
     * @return representação da categoria
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public CategoriaResponse buscar(
            @Parameter(description = "Identificador da categoria", example = "1")
            @PathVariable Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        return CategoriaResponse.de(categoria);
    }

    /**
     * Cria uma categoria e informa sua URI no cabeçalho {@code Location}.
     *
     * @param request corpo JSON validado
     * @return resposta 201 com a categoria criada
     */
    @PostMapping
    @Operation(summary = "Criar categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CategoriaResponse> criar(
            @Valid @RequestBody CategoriaRequest request) {
        Categoria salva = repository.save(new Categoria(request.nome(), request.descricao()));
        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salva.getId())
                .toUri();
        return ResponseEntity.created(localizacao).body(CategoriaResponse.de(salva));
    }

    /**
     * Atualiza os dados editáveis de uma categoria.
     *
     * @param id identificador recebido na URI
     * @param request novo estado validado
     * @return representação atualizada
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public CategoriaResponse atualizar(
            @Parameter(description = "Identificador da categoria", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest request) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        categoria.atualizar(request.nome(), request.descricao());
        return CategoriaResponse.de(repository.save(categoria));
    }

    /**
     * Exclui uma categoria.
     *
     * @param id identificador recebido na URI
     * @return resposta 204 sem corpo
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria excluída"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "Identificador da categoria", example = "1")
            @PathVariable Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
        repository.delete(categoria);
        return ResponseEntity.noContent().build();
    }
}
