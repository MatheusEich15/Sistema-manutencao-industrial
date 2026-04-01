package br.com.manutencaoIndustrial.dao;

import br.com.manutencaoIndustrial.model.OrdemManutencao;
import br.com.manutencaoIndustrial.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemManutencaoDAO {
    public void criarOrdemManutencao(OrdemManutencao ordemManutencao) throws SQLException {
        String command = """
                INSERT INTO OrdemManutencao
                (idMaquina, idTecnico, dataSolicitacao, status)
                VALUES
                (?, ?, ?, ?)
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, ordemManutencao.getIdMaquina());
            stmt.setInt(2, ordemManutencao.getIdTecnico());
            stmt.setDate(3, Date.valueOf(ordemManutencao.getDataSolicitacao()));
            stmt.setString(4, ordemManutencao.getStatus().toString());

            stmt.executeUpdate();
        }
    }

    public List<OrdemManutencao> listarOrdemManutencaos() throws SQLException{
        List<OrdemManutencao> ordemManutencaos = new ArrayList<>();
        String command = """
                SELECT
                id, idMaquina, idTecnico, dataSolicitacao
                FROM
                OrdemManutencao
                WHERE status = 'Pendente'
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                ordemManutencaos.add(new OrdemManutencao(
                    rs.getInt("id"),
                    rs.getInt("idMaquina"),
                    rs.getInt("idTecnico"),
                    rs.getObject("dataSolicitacao", LocalDate.class)
                ));
            }
        }
        return ordemManutencaos;
    }

    public void alterarStatus(int idOrdem) throws SQLException{
        String command = """
               UPDATE OrdemManutencao
               SET status = 'Executada'
               WHERE id = ?
               """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, idOrdem);
            stmt.executeUpdate();
        }
    }
}
