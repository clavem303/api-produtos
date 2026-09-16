package br.edu.fiap.api.controller.dto;

import br.edu.fiap.api.entity.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representação pública devolvida pela API para Categoria.
 *
 * @param id identificador da categoria
 * @param nome nome da categoria
 * @param descricao descrição da categoria
 */
public record CategoriaResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "Informática") String nome,
        @Schema(example = "Produtos e acessórios de informática") String descricao) {

    /**
     * Converte a entidade de domínio para o contrato HTTP.
     *
     * @param categoria entidade a converter
     * @return representação segura para o consumidor
     */
    public static CategoriaResponse de(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }
}
