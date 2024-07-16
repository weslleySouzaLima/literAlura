package literAlura.principal;

import com.fasterxml.jackson.databind.ObjectMapper;
import literAlura.model.*;
import literAlura.repository.AutorRepository;
import literAlura.repository.LivroRepository;
import literAlura.service.ConsumoApi;
import literAlura.service.ConverterDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final String GUTENDEX_URL = "https://gutendex.com/books?search=";

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverterDados converterDados;

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
    }

    public void iniciar() {
        exibirMenu();
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        switch (opcao) {
            case 1:
                listarLivrosPorIdioma();
                break;
            case 2:
                listarTop10Livros();
                break;
            case 3:
                buscarLivroPorTitulo();
                break;
            case 4:
                buscarAutoresPorNome();
                break;
            case 5:
                buscarAutoresPorPeriodo();
                break;
            case 6:
                buscarAutoresVivos();
                break;
            case 7:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
        }
    }

    private void exibirMenu() {
        System.out.println("### Biblioteca Virtual ###");
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Listar livros por idioma");
        System.out.println("2 - Listar top 10 livros por downloads");
        System.out.println("3 - Buscar livro por título");
        System.out.println("4 - Buscar autores por nome");
        System.out.println("5 - Buscar autores por período de vida");
        System.out.println("6 - Listar autores ainda vivos");
        System.out.println("7 - Sair");
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Digite o idioma (ex: pt, en, es): ");
        String idioma = scanner.nextLine();

        List<Livro> livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma selecionado.");
        } else {
            livros.forEach(livro -> System.out.println(livro.toString()));
        }
    }

    private void listarTop10Livros() {
        List<Livro> topLivros = livroRepository.findTop10ByOrderByNumeroDownloadsDesc();
        if (topLivros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            topLivros.forEach(livro -> System.out.println(livro.toString()));
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        Livro livro = livroRepository.findByTitulo(titulo);
        if (livro == null) {
            System.out.println("Livro não encontrado com o título fornecido.");
        } else {
            System.out.println(livro.toString());
        }
    }

    private void buscarAutoresPorNome() {
        System.out.print("Digite o nome do autor: ");
        String nome = scanner.nextLine();

        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado com o nome fornecido.");
        } else {
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void buscarAutoresPorPeriodo() {
        System.out.print("Digite o ano inicial do período (0 para qualquer): ");
        int inicio = scanner.nextInt();
        System.out.print("Digite o ano final do período (0 para qualquer): ");
        int fim = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        if (inicio == 0 && fim == 0) {
            System.out.println("Você precisa especificar ao menos um ano.");
            return;
        }

        List<Autor> autores;
        if (inicio == 0) {
            autores = autorRepository.findByAnoFalecimentoLessThanEqual(fim);
        } else if (fim == 0) {
            autores = autorRepository.findByAnoFalecimentoGreaterThanEqual(inicio);
        } else {
            autores = autorRepository.findByAnoFalecimentoLessThanEqual(fim);
            autores.retainAll(autorRepository.findByAnoFalecimentoGreaterThanEqual(inicio));
        }

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado dentro do período especificado.");
        } else {
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void buscarAutoresVivos() {
        List<Autor> autores = autorRepository.findByAnoFalecimentoGreaterThanEqual(2024);
        if (autores.isEmpty()) {
            System.out.println("Não há autores conhecidos vivos.");
        } else {
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void buscarLivrosPorApi(String termoBusca) {
        String url = GUTENDEX_URL + termoBusca;
        String respostaApi = consumoApi.obterDados(url);
        List<DadosLivros> dadosLivros = converterDados.obterLista(respostaApi, DadosLivros.class);

        if (dadosLivros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o termo de busca.");
        } else {
            dadosLivros.forEach(dados -> {
                Livro livro = new Livro(dados);
                livroRepository.save(livro);
                System.out.println("Livro salvo no banco de dados: " + livro.toString());
            });
        }
    }
}
