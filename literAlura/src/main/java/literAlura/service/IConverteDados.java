package literAlura.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> obterLista(String json, Class<T> classe);
}
