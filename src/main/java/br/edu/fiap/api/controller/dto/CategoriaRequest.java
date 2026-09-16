package br.edu.fiap.api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dados aceitos nas operações de criação e atualização de categoria.
 *
 * @param nome nome obrigatório da categoria
 * @param descricao descrição opcional da categoria
 */
public record CategoriaRequest(
        @Schema(description = "Nome da categoria", example = "Informática")
        @NotBlank @Size(max = 80) String nome,
        @Schema(description = "Descrição da categoria", example = "Produtos e acessórios de informática")
        @Size(max = 255) String descricao
) {
}
