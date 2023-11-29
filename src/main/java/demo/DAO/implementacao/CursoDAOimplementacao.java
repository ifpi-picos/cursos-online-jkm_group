package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import demo.entidades.Curso;

public class CursoDAOimplementacao {
    private String jdbcURL;
    private Connection conexaoDB;

    public CursoDAOimplementacao(Connection conexaoDB) {
        this.conexaoDB = conexaoDB;
    }

    public void cadastrarCurso(Curso curso) throws SQLException {
        String query = "INSERT INTO cursos (nome_curso, status_curso, carga_horaria) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getStatus());
            preparedStatement.setInt(3, curso.getCargaHoraria());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCurso(Curso curso) throws SQLException {
        String query = "UPDATE cursos SET nome_curso=?, status_curso=?, carga_horaria=? WHERE id_curso=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getStatus());
            preparedStatement.setInt(3, curso.getCargaHoraria());
            preparedStatement.setInt(4, curso.getID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Curso> listarCursos() throws SQLException {
        List<Curso> cursosList = new ArrayList<>();

        String query = "SELECT * FROM cursos";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso(
                            resultSet.getString("nome_curso"),
                            resultSet.getString("status_curso"),
                            resultSet.getInt("carga_horaria"));

                    cursosList.add(curso);
                }
            }
        }

        return cursosList;
    }

    public double registrarNota(int id_aluno, int id_curso) throws SQLException {
        String query = "SELECT nota FROM notas WHERE id_aluno=? AND id_curso=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, id_aluno);
            preparedStatement.setInt(2, id_curso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("nota");
                } else {
                    return 0;
                }
            }
        }
    }

    public double calcularMediaNotas(int id_curso) throws SQLException {
        String query = "SELECT AVG(nota) AS media FROM notas WHERE id_curso=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, id_curso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("media");
                } else {
                    return 0;
                }
            }
        }
    }
}