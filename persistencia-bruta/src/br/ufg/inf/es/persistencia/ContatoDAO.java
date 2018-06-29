package br.ufg.inf.es.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContatoDAO {

    private final String SQL_INSERE_CONTATO = "INSERT INTO AGENDA(NOME, TELEFONE) VALUES ( ?, ?);";
    private final String SQL_SELECIONA_CONTATO = "SELECT * FROM AGENDA";

    private PreparedStatement pst = null;

    public void inserirContato(Contato contato) {
        try (Connection conn = new CNXJDBC().conectar();
             PreparedStatement pst = conn.prepareStatement(SQL_INSERE_CONTATO);) {
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getTelefone());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao executar o Statment " + e.toString());
        }
    }

    public ArrayList<Contato> listarTodosContato() {
        ArrayList<Contato> contatos = new ArrayList<Contato>();

        Contato contato;
        try (Connection conn = new CNXJDBC().conectar();
             PreparedStatement pst = conn.prepareStatement(SQL_SELECIONA_CONTATO);
             ResultSet rs = pst.executeQuery();) {

            while (rs.next()) {
                contato = new Contato();
                contato.setId(rs.getInt("ID"));
                contato.setNome(rs.getString("NOME"));
                contato.setTelefone(rs.getString("TELEFONE"));
                contatos.add(contato);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar o Statement" + e.toString());
        }

        return contatos;
    }
}
