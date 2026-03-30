package br.com.manutencaoIndustrial;

import br.com.manutencaoIndustrial.dao.*;
import br.com.manutencaoIndustrial.enums.StatusMaquina;
import br.com.manutencaoIndustrial.enums.StatusOrdemManutencao;
import br.com.manutencaoIndustrial.model.*;
import br.com.manutencaoIndustrial.util.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static final Scanner SC = new Scanner(System.in);
    static final MaquinaDAO MAQUINADAO = new MaquinaDAO();
    static final OrdemManutencaoDAO ORDEMMANUTENCAODAO  = new OrdemManutencaoDAO();
    static final OrdemPecaDAO ORDEMPECADAO = new OrdemPecaDAO();
    static final PecaDAO PECADAO = new PecaDAO();
    static final TecnicoDAO TECNICODAO = new TecnicoDAO();
    public static void main(String[] args) {inicio();}
    public static void inicio() {
        while (true) {
            System.out.println("""
                    
                    1 - Cadastrar Máquina
                    2 - Cadastrar Técnico
                    3 - Cadastrar Peça
                    4 - Criar Ordem de Manutenção
                    5 - Associar Peças à Ordem
                    6 - Executar Manutenção
                    0 - Sair
                    
                    Insira a opção desejada:
                    """);
            int opcao = SC.nextInt();
            switch (opcao) {
                case 1: {
                    cadastrarMaquina();
                    break;
                }
                case 2: {
                    cadastrarTecnico();
                    break;
                }
                case 3: {
                    cadastrarPeca();
                    break;
                }
                case 4: {
                    criarOrdemManutencao();
                    break;
                }
                case 5: {
                    associarPecaOrdem();
                    break;
                }
                case 6: {
                    //executarManutencao();
                    break;
                }
                case 0: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("ERRO!!!");
                }
            }
        }
    }

    public static void cadastrarMaquina() {
        System.out.println("Tem certeza que deseja cadastrar uma nova máquina? \n" +
                "1 - SIM \n" +
                "2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                String nome;
                do {
                    System.out.println("Insira o nome da máquina: ");
                    nome = SC.nextLine();
                    if (nome == null) {
                        System.out.println("O nome é obrigatório!");
                    }
                } while (nome == null);
                String setor;
                do {
                    System.out.println("Insira o nome do setor da máquina: ");
                    setor = SC.nextLine();
                    if (setor == null) {
                        System.out.println("O setor é obrigatório!");
                    }
                } while (setor == null);
                String status = StatusMaquina.OPERACIONAL.getName();
                Maquina maquina = new Maquina(nome, setor, status);
                try {
                    if (MAQUINADAO.verificarEntrada(maquina)) {
                        System.out.println("Esta máquina ja existe neste setor!");
                        cadastrarMaquina();
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                try {
                    System.out.println(
                        "\nNOME: " + nome +
                        "\nSETOR: " + setor +
                        "\n<>------------------<>\n" +
                        "\nTem certeza que deseja cadastrar esta máquina?" +
                        "\n1 - SIM" +
                        "\n2 - NÃO"
                        );
                    int confirma = SC.nextInt();
                    switch (confirma) {
                        case 1: {
                            MAQUINADAO.cadastrarMaquina(maquina);
                            System.out.println("Máquina cadastrada com sucesso!");
                            break;
                        }
                        case 2: {
                            System.out.println("Retornando ao menu...");
                            return;
                        }
                        default: {
                            System.out.println("ERRO!!!");
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void cadastrarTecnico() {
        System.out.println("Tem certeza que deseja cadastrar um novo técnico? \n" +
                "1 - SIM \n" +
                "2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                String nome;
                do {
                    System.out.println("Insira o nome do técnico: ");
                    nome = SC.nextLine();
                    if (nome == null) {
                        System.out.println("O nome é obrigatório!");
                    }
                } while (nome == null);
                System.out.println("Insira a especialidade do técnico (Caso não tenha insira '0'): ");
                String especialidade = SC.nextLine();
                if (especialidade.equals("0")) {
                    especialidade = "N/A";
                }
                Tecnico tecnico = new Tecnico(nome, especialidade);
                try {
                    if (TECNICODAO.verificarEntrada(tecnico)) {
                        System.out.println("Este técnico já existe no sistema!");
                        cadastrarTecnico();
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                try {
                    System.out.println(
                        "\nNOME: " + nome +
                        "\nESPECIALIDADE: " + especialidade +
                        "\n<>------------------<>\n" +
                        "\nTem certeza que deseja cadastrar este técnico?" +
                        "\n1 - SIM" +
                        "\n2 - NÃO"
                        );
                    int confirma = SC.nextInt();
                    switch (confirma) {
                        case 1: {
                            TECNICODAO.cadastrarTecnico(tecnico);
                            System.out.println("Técnico cadastrado com sucesso!");
                            break;
                        }
                        case 2: {
                            System.out.println("Retornando ao menu...");
                            return;
                        }
                        default: {
                            System.out.println("ERRO!!!");
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void cadastrarPeca() {
        System.out.println("Tem certeza que deseja cadastrar uma nova peça? \n" +
                "1 - SIM \n" +
                "2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                SC.nextLine();
                String nome;
                do {
                    System.out.println("Insira o nome da peça: ");
                    nome = SC.nextLine();
                    if (nome == null) {
                        System.out.println("O nome é obrigatório!");
                    }
                } while (nome == null);
                double estoque;
                do {
                    System.out.println("Insira o estoque inicial da peça: ");
                    estoque = SC.nextInt();
                    if (estoque <= 0) {
                        System.out.println("O estoque inicial é obrigatório!");
                    }
                } while (estoque <= 0);
                Peca peca = new Peca(nome, estoque);
                try {
                    if (PECADAO.verificarEntrada(peca)) {
                        System.out.println("Esta peça ja existe no sistema!");
                        cadastrarMaquina();
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                try {
                    System.out.println(
                        "\nNOME: " + nome +
                        "\nESTOQUE: " + estoque +
                        "\n<>------------------<>\n" +
                        "\nTem certeza que deseja cadastrar esta peça?" +
                        "\n1 - SIM" +
                        "\n2 - NÃO"
                        );
                    int confirma = SC.nextInt();
                    switch (confirma) {
                        case 1: {
                            PECADAO.cadastrarPeca(peca);
                            System.out.println("Peça cadastrada com sucesso!");
                            break;
                        }
                        case 2: {
                            System.out.println("Retornando ao menu...");
                            return;
                        }
                        default: {
                            System.out.println("ERRO!!!");
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void criarOrdemManutencao() {
        List<Maquina> maquinas = new ArrayList<>();
        List<Tecnico> tecnicos = new ArrayList<>();
        System.out.println("Tem certeza que deseja criar uma ordem de manutenção? \n" +
            "1 - SIM \n" +
            "2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                boolean idMaquinaCorreto = false;
                int idMaquina;
                String nomeMaquina = "";
                String setorMaquina = "";
                do {
                    try {
                        maquinas = MAQUINADAO.listarMaquinas();
                    } catch (SQLException e) {
                        System.out.println("Erro ao conectar com o banco de dados!");
                        e.printStackTrace();
                    }
                    for (Maquina maquina : maquinas) {
                        System.out.println(maquina.toStringBasico());
                    }
                    System.out.println("Insira o ID da máquina que necessita de manutenção:");
                    idMaquina = SC.nextInt();
                    for (Maquina maquina : maquinas){
                        if (maquina.getId() == idMaquina) {
                            nomeMaquina = maquina.getNome();
                            setorMaquina = maquina.getSetor();
                            idMaquinaCorreto = true;
                            break;
                        }
                    }
                    if (!idMaquinaCorreto){
                        System.out.println("Este ID não existe!");
                    }
                } while (!idMaquinaCorreto);
                boolean idTecnicoCorreto = false;
                int idTecnico;
                String nomeTecnico = "";
                String especialidadeTecnico = "";
                do {
                    try {
                        tecnicos = TECNICODAO.listarTecnicos();
                    } catch (SQLException e) {
                        System.out.println("Erro ao conectar com o banco de dados!");
                        e.printStackTrace();
                    }
                    for (Tecnico tecnico : tecnicos) {
                        System.out.println(tecnico.toString());
                    }
                    System.out.println("Insira o ID do técnico que fará a manutenção:");
                    idTecnico = SC.nextInt();
                    for (Tecnico tecnico : tecnicos){
                        if (tecnico.getId() == idTecnico) {
                            nomeTecnico = tecnico.getNome();
                            especialidadeTecnico = tecnico.getEspecialidade();
                            idTecnicoCorreto = true;
                            break;
                        }
                    }
                    if (!idTecnicoCorreto){
                        System.out.println("Este ID não existe!");
                    }
                } while (!idTecnicoCorreto);
                LocalDate dataSolicitacao = LocalDate.now();
                String status = StatusOrdemManutencao.PENDENTE.getName();
                OrdemManutencao ordemManutencao = new OrdemManutencao(idMaquina, idTecnico, dataSolicitacao, status);
                try {
                    System.out.println(
                        "\nID MÁQUINA: " + idMaquina +
                        "\nNOME MÁQUINA: " + nomeMaquina +
                        "\nSETOR MÁQUINA: " + setorMaquina +
                        "\n<>------------------<>\n" +
                        "\nID TÉCNICO: " + idTecnico +
                        "\nNOME TÉCNICO: " + nomeTecnico +
                        "\nSETOR TÉCNICO: " + especialidadeTecnico +
                        "\n<>------------------<>\n" +
                        "\nTem certeza que deseja criar esta ordem de manutenção?" +
                        "\n1 - SIM" +
                        "\n2 - NÃO"
                        );
                    int confirma = SC.nextInt();
                    switch (confirma) {
                        case 1: {
                            ORDEMMANUTENCAODAO.criarOrdemManutencao(ordemManutencao);
                            MAQUINADAO.alterarStatus(idMaquina);
                            System.out.println("Ordem de manutenção criada com sucesso!");
                            break;
                        }
                        case 2: {
                            System.out.println("Retornando ao menu...");
                            return;
                        }
                        default: {
                            System.out.println("ERRO!!!");
                            break;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Erro ao conectar com o banco de dados!");
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void associarPecaOrdem() {
        List<OrdemManutencao> ordemManutencaos = new ArrayList<>();
        List<Peca> pecas = new ArrayList<>();
        System.out.println("Tem certeza que deseja associar uma peça a uma manutenção? \n" +
                "1 - SIM \n" +
                "2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {
                boolean idOrdemCorreto = false;
                int idOrdem = 0;
                int idMaquina = 0;
                int idTecnico = 0;
                LocalDate dataSolicitacao = null;
                do {
                    try {
                        ordemManutencaos = ORDEMMANUTENCAODAO.listarOrdemManutencaos();
                    } catch (SQLException e) {
                        System.out.println("Erro ao conectar com o banco de dados!");
                        e.printStackTrace();
                    }
                    for (OrdemManutencao ordemManutencao : ordemManutencaos) {
                        System.out.println(ordemManutencao.toString());
                    }
                    System.out.println("Insira o ID da manutenção que necessita de uma peça:");
                    idOrdem = SC.nextInt();
                    for (OrdemManutencao ordemManutencao : ordemManutencaos) {
                        if (ordemManutencao.getId() == idOrdem) {
                            idMaquina = ordemManutencao.getIdMaquina();
                            idTecnico = ordemManutencao.getIdTecnico();
                            idOrdemCorreto = true;
                            break;
                        }
                    }
                    if (!idOrdemCorreto) {
                        System.out.println("Este ID não existe!");
                    }
                } while (!idOrdemCorreto);
                boolean idPecaCorreto = false;
                boolean quantidadePecaCorreto = false;
                int idPeca = 0;
                double quantidadePeca = 0;
                String nomePeca = "";
                double estoquePeca = 0;
                int novaPeca = 0;
                do {
                    do {
                        try {
                            pecas = PECADAO.listarPecas();
                        } catch (SQLException e) {
                            System.out.println("Erro ao conectar com o banco de dados!");
                            e.printStackTrace();
                        }
                        for (Peca peca : pecas) {
                            System.out.println(peca.toString());
                        }
                        System.out.println("Insira o ID da peça que será associada:");
                        idPeca = SC.nextInt();
                        for (Peca peca : pecas) {
                            if (peca.getId() == idPeca) {
                                nomePeca = peca.getNome();
                                estoquePeca = peca.getEstoque();
                                do {
                                    System.out.println("Insira a quantia de peças necessárias:");
                                    quantidadePeca = SC.nextDouble();
                                    if (quantidadePeca > estoquePeca || quantidadePeca <= 0) {
                                        System.out.println("Quantidade de peças inválida ou indisponível!");
                                        quantidadePecaCorreto = true;
                                    }
                                } while (quantidadePecaCorreto);
                                idPecaCorreto = true;
                                break;
                            }
                        }
                        if (!idPecaCorreto) {
                            System.out.println("Este ID não existe!");
                        }
                    } while (!idPecaCorreto);
                    dataSolicitacao = LocalDate.now();
                    OrdemPeca ordemPeca = new OrdemPeca(idOrdem, idPeca, quantidadePeca);
                    try {
                        System.out.println(
                                "\nID ORDEM MANUTENÇÃO: " + idOrdem +
                                        "\nID MÁQUINA: " + idMaquina +
                                        "\nID TÉCNICO: " + idTecnico +
                                        "\nDATA SOLICITAÇÃO: " + dataSolicitacao +
                                        "\n<>------------------<>\n" +
                                        "\nID PEÇA: " + idPeca +
                                        "\nQUANTIDADE SOLICITADA: " + quantidadePeca +
                                        "\nNOME: " + nomePeca +
                                        "\nESTOQUE: " + estoquePeca +
                                        "\nESTOQUE DESCONTADO: " + (estoquePeca - quantidadePeca) +
                                        "\n<>------------------<>\n" +
                                        "\nTem certeza que deseja associar esta peça a esta manutenção?" +
                                        "\n1 - SIM" +
                                        "\n2 - NÃO"
                        );
                        int confirma = SC.nextInt();
                        switch (confirma) {
                            case 1: {
                                ORDEMPECADAO.associarOrdemPeca(ordemPeca);
                                PECADAO.alterarEstoque(idPeca, quantidadePeca);
                                System.out.println("Peça associada a manutenção com sucesso!\n");
                                do {
                                    System.out.println("Deseja associar mais peças a esta manutenção? \n" +
                                            "1 - SIM\n" +
                                            "2 - NÃO");
                                    novaPeca = SC.nextInt();
                                    if (novaPeca == 2) {
                                        System.out.println("Processo de associação finalizado com sucesso!");
                                        return;
                                    } else if (novaPeca != 1 && novaPeca != 2) {
                                        System.out.println("Erro!!!");
                                    }
                                } while (novaPeca != 1 && novaPeca != 2);
                                break;
                            }
                            case 2: {
                                System.out.println("Retornando ao menu...");
                                return;
                            }
                            default: {
                                System.out.println("ERRO!!!");
                                break;
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro ao conectar com o banco de dados!");
                        e.printStackTrace();
                    }
                } while (novaPeca == 1);
                break;
            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }

    public static void executarManutencao() {
        List<OrdemManutencao> ordemManutencaos = new ArrayList<>();
        System.out.println("Tem certeza que deseja associar uma peça a uma manutenção? \n" +
                "1 - SIM \n" +
                "2 - NÃO");
        int opcao = SC.nextInt();
        switch (opcao) {
            case 1: {

            }
            case 2: {
                System.out.println("Retornando ao menu...");
                return;
            }
            default: {
                System.out.println("ERRO!!!");
                break;
            }
        }
    }
}