package br.com.manutencaoIndustrial.dao;

import br.com.manutencaoIndustrial.model.Maquina;
import br.com.manutencaoIndustrial.util.Conexao;
import com.sun.source.tree.TryTree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {
    public void cadastrarMaquina(Maquina maquina) throws SQLException {
        String command = """
                INSERT INTO Maquina
                (nome, setor, status)
                VALUES
                (?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus().toString());

            stmt.executeUpdate();
        }
    }

    public boolean verificarEntrada(Maquina maquina) throws SQLException {
        String command = """
                SELECT
                id, nome, setor
                FROM Maquina
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("nome").equals(maquina.getNome()) && rs.getString("setor").equals(maquina.getSetor())) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<Maquina> listarMaquinas() throws SQLException {
        List<Maquina> maquinas = new ArrayList<>();
        String command = """
                SELECT
                id, nome, setor
                FROM Maquina
                WHERE status = 'Operacional'
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                maquinas.add(new Maquina(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("setor")
                ));
            }
        }
        return maquinas;
    }

    public void alterarStatus(int idMaquina) throws SQLException {
        String command = """
                UPDATE Maquina
                SET status = 'Em manutenção'
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, idMaquina);

            stmt.executeUpdate();
        }
    }
}