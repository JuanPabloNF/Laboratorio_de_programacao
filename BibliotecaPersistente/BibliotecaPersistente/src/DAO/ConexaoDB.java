package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Responsável por abrir conexões com o banco MySQL via JDBC.
 *
 * Uso nos DAOs:
 *   try (Connection conn = ConexaoDB.getConexao()) { ... }
 */
public class ConexaoDB {

    // ---- Ajuste estes valores para o seu ambiente ----
    private static final String HOST = "localhost";
    private static final String PORTA = "3306";
    private static final String BANCO = "Biblioteca";   // nome do seu schema
    private static final String USUARIO = "root";
    private static final String SENHA = "password";    // não envie a senha real para o Git

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO
            + "?serverTimezone=America/Sao_Paulo";

    private ConexaoDB() {
    }

    /**
     * Abre uma nova conexão. Quem chama deve fechá-la
     * (de preferência com try-with-resources).
     */
    public static Connection getConexao() throws SQLException {
        // Em JDBC 4+ o driver é carregado automaticamente, não precisa de Class.forName
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /** Teste rápido: execute este main para verificar se a conexão funciona. */
    public static void main(String[] args) {
        try (Connection conn = getConexao()) {
            System.out.println("Conexão estabelecida com sucesso!");
            System.out.println("Banco: " + conn.getCatalog());
            System.out.println("Servidor: " + conn.getMetaData().getDatabaseProductName()
                    + " " + conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.err.println("Falha ao conectar: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState() + " | Código: " + e.getErrorCode());
        }
    }
}