package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.entidades.Professor;
import demo.entidades.Curso;

public class ProfessorDAOimplementacao {
  private Connection conexaoDB;

  public ProfessorDAOimplementacao(Connection conexaoDB) {
    this.conexaoDB = conexaoDB;
  }

  public void cadastrarProfessor(Professor professores) throws SQLException {

    String query = "INSERT INTO professor (nome_professor, email_professor) VALUES (?, ?)";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      preparedStatement.setString(1, professores.getNome());
      preparedStatement.setString(2, professores.getEmail());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void atualizarProfessores(Professor professor) throws SQLException {

    String query = "UPDATE professor SET (nome_professor=?, email_professor=?, WHERE id_professor=?";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      preparedStatement.setString(1, professor.getNome());
      preparedStatement.setString(2, professor.getEmail());
      preparedStatement.setInt(4, professor.getID());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Professor> listarProfessor() throws SQLException {

    List<Professor> professorList = new ArrayList<>();

    String query = "SELECT * FROM professor";
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          Professor professor = new Professor(
              resultSet.getString("nome_professor"),
              resultSet.getString("email_professor")
          );
          professorList.add(professor);
        }
      }
    }
    return professorList;
  }

  public List<Curso> listarCursosMinistrados() throws SQLException  {
    ArrayList<Curso> cursos = new ArrayList<>();
    
    String query = "SELECT nome_curso, status_curso, carga_horaria AS lista_cursos FROM cursos C INNER JOIN Professor P ON P.id_professor = C.id_professor WHERE id_professor=?";    
    try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Curso curso = new Curso(resultSet.getString("nome_curso"), resultSet.getString("status_curso"), resultSet.getInt("carga_horaria"));
                cursos.add(curso);  
            } 
        }
    }  

    return cursos;  
  }
}
