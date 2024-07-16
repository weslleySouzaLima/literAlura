package literAlura;

import literAlura.principal.Principal;
import literAlura.repository.AutorRepository;
import literAlura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final LivroRepository livroRepositorio;
	private final AutorRepository autorRepositorio;

	@Autowired
    public LiteraluraApplication(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(livroRepositorio, autorRepositorio);
		principal.iniciar();
	}
}
