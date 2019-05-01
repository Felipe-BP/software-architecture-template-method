package br.edu.utfpr.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.utfpr.dto.PaisDTO;

public abstract class AbstractDAO<Type> {

    public boolean inserir(Type entity) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            PreparedStatement statement = conn.prepareStatement(getStringInserir());
            setPreparedStatmentInserir(statement, entity);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Type> listarTodos() {

        List<Type> resultado = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(getStringListarTodos());

            int count = 0;

            while (result.next()) {
                resultado.add(createEntity(result));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public boolean excluir(int id) {

        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            PreparedStatement statement = conn.prepareStatement(getStringExcluir());
            setPreparedStatmentExcluir(statement, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean alterar(Type entity) {
        try (Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            PreparedStatement statement = conn.prepareStatement(getStringAlterar());
            setPreparedStatmentAlterar(statement, entity);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) 
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    //methods to implement the specifics strings SQL
    protected abstract String getStringAlterar();
    protected abstract String getStringExcluir();
    protected abstract String getStringListarTodos();
    protected abstract String getStringInserir();

    //methods to implements Prepared Statements specifics
    protected abstract void setPreparedStatmentAlterar(PreparedStatement preparedStatement, Type entity);
    protected abstract void setPreparedStatmentExcluir(PreparedStatement preparedStatement, int id);
    protected abstract void setPreparedStatmentInserir(PreparedStatement preparedStatement, Type entity);

    //method to build and return object
    protected abstract Type createEntity(ResultSet rs) throws SQLException;
}