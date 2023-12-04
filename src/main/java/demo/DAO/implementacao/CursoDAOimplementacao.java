package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.DAO.CursoDAO;
import demo.entidades.Curso;
import demo.entidades.Professor;
import demo.excecoes.NotFoundException;

public class CursoDAOimplementacao implements CursoDAO {
    private Connection conexaoDB;

    public CursoDAOimplementacao(Connection conexaoDB) {
        this.conexaoDB = conexaoDB;
    }

    public void cadastrar(Curso curso) {
        String query = "INSERT INTO curso (nome_curso, status_curso, carga_horaria, id_professor) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getStatus());
            preparedStatement.setInt(3, curso.getCargaHoraria());
            preparedStatement.setInt(4, curso.getMinistrante().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Curso curso) {
        String query = "UPDATE curso SET nome_curso=?, status_curso=?, carga_horaria=? WHERE id_curso=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getStatus());
            preparedStatement.setInt(3, curso.getCargaHoraria());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Curso buscarPorId(int id) throws NotFoundException {
        String query = "SELECT nome_curso, status_curso, carga_horaria, " +
                "P.id_professor, nome_professor, email_professor  " +
                "FROM curso C " +
                "INNER JOIN professor P ON p.id_professor = C.id_professor " +
                "WHERE id_curso=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Curso curso = new Curso();
                curso.setId(id);
                curso.setNome(resultSet.getString("nome_curso"));
                curso.setStatus(resultSet.getString("status_curso"));
                curso.setCargaHoraria(resultSet.getInt("carga_horaria"));

                Professor professor = new Professor();
                professor.setId(resultSet.getInt("id_professor"));
                professor.setNome(resultSet.getString("nome_professor"));
                professor.setEmail(resultSet.getString("email_professor"));

                curso.setMinistrante(professor);

                return curso;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NotFoundException();
    }

    public List<Curso> obterTodos() {
        List<Curso> cursosList = new ArrayList<>();

        String query = "SELECT id_curso, nome_curso, status_curso, carga_horaria, " +
                " P.id_professor, nome_professor, email_professor " +
                " FROM curso C " +
                "INNER JOIN professor P ON p.id_professor = C.id_professor";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso();
                    curso.setId(resultSet.getInt("id_curso"));
                    curso.setNome(resultSet.getString("nome_curso"));
                    curso.setStatus(resultSet.getString("status_curso"));
                    curso.setCargaHoraria(resultSet.getInt("carga_horaria"));

                    Professor professor = new Professor();
                    professor.setId(resultSet.getInt("id_professor"));
                    professor.setNome(resultSet.getString("nome_professor"));
                    professor.setEmail(resultSet.getString("email_professor"));

                    curso.setMinistrante(professor);

                    cursosList.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursosList;
    }

}
