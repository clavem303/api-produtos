package br.edu.fiap.api.repository;

import br.edu.fiap.api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Porta de persistência das categorias.
 *
 * <p>O Spring Data JPA cria a implementação em tempo de execução.</p>
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
