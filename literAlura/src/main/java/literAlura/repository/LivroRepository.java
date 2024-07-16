package literAlura.repository;

import literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.List;

@Component
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIdioma(String idioma);

    Integer countByIdioma(String idioma);

    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();

    Livro findByTitulo(String tituloLivro);
}
