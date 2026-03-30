package br.com.manutencaoIndustrial.dao;

import br.com.manutencaoIndustrial.model.Peca;
import br.com.manutencaoIndustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {
    public void cadastrarPeca(Peca peca) throws SQLException {
        String command = """
                INSERT INTO Peca
                (nome, estoque)
                VALUES
                (?, ?);
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getEstoque());

            stmt.executeUpdate();
        }
    }

    public boolean verificarEntrada(Peca peca) throws SQLException {
        String command = """
                SELECT
                id, nome, estoque
                FROM Peca;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("nome").equals(peca.getNome())) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<Peca> listarPecas() throws SQLException {
        List<Peca> pecas = new ArrayList<>();
        String command = """
                SELECT
                id, nome, estoque
                FROM Peca;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pecas.add(new Peca(
                   rs.getInt("id"),
                   rs.getString("nome"),
                   rs.getDouble("estoque")
                ));
            }
        }
        return pecas;
    }

    public void alterarEstoque(int idPeca, double quantidadePeca) throws SQLException {
        String command = """
                UPDATE Peca
                SET estoque = estoque - ?
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setDouble(1, quantidadePeca);
            stmt.setInt(2, idPeca);

            stmt.executeUpdate();
        }
    }
}
