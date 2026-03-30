package br.com.manutencaoIndustrial.enums;

public enum StatusMaquina {
    OPERACIONAL("Operacional"),
    EMMANUTENCAO("Em manutenção");

    private final String name;

    StatusMaquina(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
