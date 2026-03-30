package br.com.manutencaoIndustrial.enums;

public enum StatusOrdemManutencao {
    PENDENTE("Pendente"),
    EXECUTADA("Executada"),
    CANCELADA("Cancelada");

    private final String name;

    StatusOrdemManutencao(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
