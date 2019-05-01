package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.edu.utfpr.dto.PaisDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.extern.java.Log;

@Log
@Builder
public class PaisDAO extends AbstractDAO<PaisDTO> {

    // Responsável por criar a tabela País no banco
    public PaisDAO() {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            log.info("Criando tabela pais ...");
            conn.createStatement()
                    .executeUpdate("CREATE TABLE pais ("
                            + "id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_pais_pk PRIMARY KEY,"
                            + "nome varchar(255)," + "sigla varchar(3)," + "codigoTelefone int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getStringAlterar() {
        return "UPDATE pais SET nome=?, sigla=?, codigoTelefone=? WHERE id=?";
    }

    @Override
    protected String getStringExcluir() {
        return "DELETE FROM pais WHERE id=?";
    }

    @Override
    protected String getStringListarTodos() {
        return "SELECT * FROM pais";
    }

    @Override
    protected String getStringInserir() {
        return "INSERT INTO pais (nome, sigla, codigoTelefone) VALUES (?, ?, ?)";
    }

    @Override
    protected void setPreparedStatmentAlterar(PreparedStatement preparedStatement, PaisDTO entity) {
        try {
            preparedStatement.setString(1, entity.getNome());
            preparedStatement.setString(2, entity.getSigla());
            preparedStatement.setInt(3, entity.getCodigoTelefone());
            preparedStatement.setInt(4, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setPreparedStatmentExcluir(PreparedStatement preparedStatement, int id) {
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setPreparedStatmentInserir(PreparedStatement preparedStatement, PaisDTO entity) {
        try {
            preparedStatement.setString(1, entity.getNome());
            preparedStatement.setString(2, entity.getSigla());
            preparedStatement.setInt(3, entity.getCodigoTelefone());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected PaisDTO createEntity(ResultSet rs) throws SQLException{
        try {
            return PaisDTO.builder()
                        .codigoTelefone(rs.getInt("codigoTelefone"))
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .sigla(rs.getString("sigla"))
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
