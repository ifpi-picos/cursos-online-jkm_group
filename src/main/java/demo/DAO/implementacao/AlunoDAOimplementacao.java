package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.DAO.AlunoDAO;
import demo.entidades.Aluno;
import demo.excecoes.NotFoundException;
import demo.excecoes.UnauthorizedException;

public class AlunoDAOimplementacao implements AlunoDAO {
    private Connection conexaoDB;

    public AlunoDAOimplementacao(Connection conexaoDB) {
        this.conexaoDB = conexaoDB;
    }

    public void cadastrar(Aluno aluno) {
        String query = "INSERT INTO aluno (nome_aluno, email_aluno, senha_aluno) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, aluno.getNome());
            preparedStatement.setString(2, aluno.getEmail());
            preparedStatement.setString(3, aluno.getSenha());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Aluno autenticar(String email, String senha) throws UnauthorizedException {
        String query = "SELECT id_aluno, nome_aluno, email_aluno FROM aluno WHERE email_aluno=? AND senha_aluno=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(resultSet.getInt("id_aluno"));
                aluno.setNome(resultSet.getString("nome_aluno"));
                aluno.setEmail(email);
                aluno.setSenha(senha);

                return aluno;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new UnauthorizedException();
    }

    public void atualizar(int id, Aluno aluno) {
        String query = "UPDATE aluno SET nome_aluno=?, email_aluno=?, senha_aluno=? WHERE id_aluno=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, aluno.getNome());
            preparedStatement.setString(2, aluno.getEmail());
            preparedStatement.setString(3, aluno.getSenha());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> obterTodos() {
        List<Aluno> alunosList = new ArrayList<>();

        String query = "SELECT * FROM aluno";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setId(resultSet.getInt("id_aluno"));
                    aluno.setNome(resultSet.getString("nome_aluno"));
                    aluno.setEmail(resultSet.getString("email_aluno"));

                    alunosList.add(aluno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunosList;
    }

    public Aluno obterPorId(int alunoId) throws NotFoundException {
        String query = "SELECT nome_aluno, email_aluno FROM aluno WHERE id_aluno=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, alunoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setId(alunoId);
                    aluno.setNome(resultSet.getString("nome_aluno"));
                    aluno.setEmail(resultSet.getString("email_aluno"));

                    return aluno;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NotFoundException();
    }
}