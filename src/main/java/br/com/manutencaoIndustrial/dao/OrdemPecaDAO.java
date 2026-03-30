package br.com.manutencaoIndustrial.dao;

import br.com.manutencaoIndustrial.model.OrdemManutencao;
import br.com.manutencaoIndustrial.model.OrdemPeca;
import br.com.manutencaoIndustrial.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdemPecaDAO {
    public void associarOrdemPeca(OrdemPeca ordemPeca) throws SQLException {
        String command = """
                INSERT INTO OrdemPeca
                (idOrdem, idPeca, quantidade)
                VALUES
                (?, ?, ?);
                """;
        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, ordemPeca.getIdOrdem());
            stmt.setInt(2, ordemPeca.getIdPeca());
            stmt.setDouble(3, ordemPeca.getQuantidade());

            stmt.executeUpdate();
        }
    }
}
