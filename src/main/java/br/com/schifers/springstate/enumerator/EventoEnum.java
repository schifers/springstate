package br.com.schifers.springstate.enumerator;

public enum EventoEnum {
    CANCELAR("1"),
    DEFERIR("2"),
    DISTRIBUIR("3"),
    ENVIAR("4"),
    INICIAR_ANALISE("5");

    private String codigo;

    public static EventoEnum recuperarEventoPorCodigo(String codigo) {
        for (EventoEnum e : values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }

    EventoEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }
}
