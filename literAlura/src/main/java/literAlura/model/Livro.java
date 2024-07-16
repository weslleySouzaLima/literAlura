package literAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;

    private String idioma;

    private Integer numeroDownloads;

    public Livro() {}

    public Livro(DadosLivros dados) {
        this.titulo = dados.titulo();
        this.autores = dados.autores().stream()
                .map(autor -> new Autor(autor, this))
                .collect(Collectors.toList());
        this.idioma = String.valueOf(dados.idiomas().get(0));
        this.numeroDownloads = dados.numeroDownloads();
    }

    // getters e setters
    // toString
}
