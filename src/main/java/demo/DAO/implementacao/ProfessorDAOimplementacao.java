package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.DAO.ProfessorDAO;
import demo.entidades.Professor;
import demo.excecoes.NotFoundException;
import demo.excecoes.UnauthorizedException;

public class ProfessorDAOimplementacao implements ProfessorDAO {
  private Connection conexaoDB;

  public ProfessorDAOimplementacao(Connection conexaoDB) {
    this.conexaoDB = conexaoDB;
  }

  public Professor obterPorId(int id) throws NotFoundException {
    String query = "SELECT nome_professor, email_professor FROM professor WHERE id_professor=?";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        Professor professor = new Professor();
        professor.setId(id);
        professor.setNome(resultSet.getString("nome_professor"));
        professor.setEmail(resultSet.getString("email_professor"));

        return professor;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    throw new NotFoundException();
  }

  public void cadastrar(Professor professores) {

    String query = "INSERT INTO professor (nome_professor, email_professor, senha_professor) VALUES (?, ?, ?)";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      preparedStatement.setString(1, professores.getNome());
      preparedStatement.setString(2, professores.getEmail());
      preparedStatement.setString(3, professores.getSenha());
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Professor autenticar(String email, String senha) throws UnauthorizedException {
    String query = "SELECT id_professor, nome_professor FROM professor WHERE email_professor=? AND senha_professor=?";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, senha);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        Professor professor = new Professor();
        professor.setId(resultSet.getInt("id_professor"));
        professor.setNome(resultSet.getString("nome_professor"));
        professor.setEmail(email);
        professor.setSenha(senha);

        return professor;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    throw new UnauthorizedException();
  }

  public void atualizar(int id, Professor professorDados) {
    String query = "UPDATE professor SET nome_professor=?, email_professor=?, senha_professor=? WHERE id_professor=?";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      preparedStatement.setString(1, professorDados.getNome());
      preparedStatement.setString(2, professorDados.getEmail());
      preparedStatement.setString(3, professorDados.getSenha());
      preparedStatement.setInt(4, professorDados.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Professor> obterTodos() {

    List<Professor> professorLista = new ArrayList<>();

    String query = "SELECT * FROM professor";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          Professor professor = new Professor();
          professor.setNome(resultSet.getString("nome_professor"));
          professor.setEmail(resultSet.getString("email_professor"));
          professorLista.add(professor);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return professorLista;
  }
}
