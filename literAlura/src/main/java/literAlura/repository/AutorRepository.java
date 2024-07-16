package literAlura.repository;

import literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByAnoFalecimentoLessThanEqual(Integer ano);

    List<Autor> findByNomeContainingIgnoreCase(String nome);

    List<Autor> findByAnoFalecimentoGreaterThanEqual(Integer ano);
}
