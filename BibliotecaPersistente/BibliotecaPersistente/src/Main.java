import DAO.ConexaoDB;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conexao = ConexaoDB.getConexao();

            System.out.println("Conexão estabelecida com sucesso!");

            conexao.close();

        } catch (SQLException e) {

            System.out.println("Erro ao conectar ao banco:");
            e.printStackTrace();
        }
    }
}

