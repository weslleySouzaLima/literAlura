package literAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "autores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer anoNascimento;

    private Integer anoFalecimento;

    @ManyToOne
    private Livro livro;

    public Autor() {}

    public Autor(DadosAutor dados, Livro livro) {
        this.nome = formatarNome(dados.nome());
        this.anoNascimento = dados.anoNascimento();
        this.anoFalecimento = dados.anoFalecimento();
        this.livro = livro;
    }

    private String formatarNome(String nome) {
        if (nome.contains(",")) {
            String[] partes = nome.split(", ");
            return partes[1] + " " + partes[0];
        }
        return nome;
    }

    // getters e setters
    // toString
}
