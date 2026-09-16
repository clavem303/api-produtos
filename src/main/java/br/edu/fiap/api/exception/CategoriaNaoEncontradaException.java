package br.edu.fiap.api.exception;

/**
 * Indica que não existe categoria com o identificador informado.
 */
public class CategoriaNaoEncontradaException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem adequada para o contrato HTTP.
     *
     * @param id identificador procurado
     */
    public CategoriaNaoEncontradaException(Long id) {
        super("Categoria nao encontrada: " + id);
    }

    /**
     * Cria a exceção com uma mensagem personalizada.
     *
     * @param message mensagem explicativa
     */
    public CategoriaNaoEncontradaException(String message) {
        super(message);
    }
}
