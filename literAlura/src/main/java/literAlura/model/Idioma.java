package literAlura.model;

public enum Idioma {

    PT("pt", "Português"),
    EN("en", "Inglês"),
    ES("es", "Espanhol"),
    FR("fr", "Francês"),
    DE("de", "Alemão"),
    IT("it", "Italiano"),
    NL("nl", "Holandês"),
    EO("eo", "Esperanto"),
    RU("ru", "Russo"),
    JA("ja", "Japonês"),
    KO("ko", "Coreano"),
    ZH("zh", "Chinês"),
    AR("ar", "Árabe"),
    HI("hi", "Hindi"),
    UR("ur", "Urdu"),
    FA("fa", "Persa"),
    GU("gu", "Guzerate"),
    KN("kn", "Canarês"),
    ML("ml", "Malaiala"),
    OR("or", "Oriá"),
    TE("te", "Telugu"),
    TA("ta", "Tâmil");

    private final String codigo;
    private final String descricao;

    Idioma(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Idioma fromCodigo(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para o código fornecido: " + codigo);
    }

    public static Idioma fromDescricao(String descricao) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.descricao.equalsIgnoreCase(descricao)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para a descrição fornecida: " + descricao);
    }
}
