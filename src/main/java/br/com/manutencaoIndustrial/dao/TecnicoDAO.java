package br.com.manutencaoIndustrial.dao;

import br.com.manutencaoIndustrial.model.Tecnico;
import br.com.manutencaoIndustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {
    public void cadastrarTecnico(Tecnico tecnico) throws SQLException {
        String command = """
                INSERT INTO Tecnico
                (nome, especialidade)
                VALUES
                (?, ?);
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());

            stmt.executeUpdate();
        }
    }

    public boolean verificarEntrada(Tecnico tecnico) throws SQLException {
        String command = """
                SELECT
                id, nome, especialidade
                FROM Tecnico;
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("nome").equals(tecnico.getNome())) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<Tecnico> listarTecnicos() throws SQLException {
        List<Tecnico> tecnicos = new ArrayList<>();
        String command = """
                SELECT
                id, nome, especialidade
                FROM Tecnico
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tecnicos.add(new Tecnico(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("especialidade")
                ));
            }
        }
        return tecnicos;
    }
}
