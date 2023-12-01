package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import demo.DAO.AlunoDAO;
import demo.entidades.Aluno;
import demo.entidades.Curso;

public class AlunoDAOimplementacao implements AlunoDAO{
    public static Object cadastrarAluno;
    private Connection conexaoDB;

    public AlunoDAOimplementacao(Connection conexaoDB) {
        this.conexaoDB = conexaoDB;
    }

    public void cadastrarAluno(Aluno aluno) throws SQLException {     
        String query = "INSERT INTO aluno (nome_aluno, email_aluno) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, aluno.getNome());
            preparedStatement.setString(2, aluno.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarAluno(Aluno aluno) throws SQLException {
        String query = "UPDATE aluno SET nome_aluno=?, email_aluno=?, cursos_matriculados=?, notas?, status=? WHERE id_aluno=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setString(1, aluno.getNome());
            preparedStatement.setString(2, aluno.getEmail());
            preparedStatement.setString(3, aluno.getCursosMatriculados());
            preparedStatement.setDouble(4, aluno.getNotas());
            preparedStatement.setBoolean(5, aluno.isStatus());
            preparedStatement.setInt(6, aluno.getIdAluno());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> listarAlunos() throws SQLException {
        List<Aluno> alunosList = new ArrayList<>();

        String query = "SELECT * FROM aluno";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Aluno aluno = new Aluno(
                        resultSet.getInt("id_aluno"),
                        resultSet.getString("nome_aluno"),
                        resultSet.getString("email_aluno"),
                        resultSet.getDouble("notas"),
                        resultSet.getBoolean("status")
                    );

                    alunosList.add(aluno);
                }
            }
        }

        return alunosList;
    }

    public void matricularAlunoEmCurso(Aluno aluno, Curso curso) throws SQLException {

            String query = "INSERT INTO cursos (id_curso, nome_curso) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
                preparedStatement.setInt(1, curso.getIdCurso());
                preparedStatement.setString(2, curso.getNome());
                preparedStatement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
    }

    public void cancelarMatriculaAluno(Aluno aluno, Curso curso) throws SQLException {

            String query = "DELETE FROM cursos WHERE id_curso = ? AND nome_curso = ?";
            try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
                preparedStatement.setInt(1, curso.getIdCurso());
                preparedStatement.setString(2, curso.getNome());
                preparedStatement.executeUpdate();
            }
    }

    public void gerarRelatorioDesempenho(Aluno aluno) throws SQLException {

        String query = "SELECT nome_aluno, matricula.id_curso, notas.nota " +
                "FROM aluno " +
                "JOIN matricula ON aluno.nome_aluno = matricula.id_aluno " +
                "LEFT JOIN notas ON matricula.id_curso = notas.id_curso " +
                "AND aluno.nome_aluno = notas.id_aluno";

        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                String nomeAluno = resultSet.getString("nome_aluno");
                String nomeCurso = resultSet.getString("id_curso");
                int nota = resultSet.getInt("nota");

                System.out.print("Aluno: " + nomeAluno + " | Curso: " + nomeCurso + " | Nota: " + nota);
                }
            }
        }
    }
    public List<Curso> listarCursosConcluidos(Aluno aluno) throws SQLException {
        List<Curso> cursosConcluidos = new ArrayList<>();

        String query = "SELECT cursos.id_curso, cursos.nome_curso, cursos.status_curso, cursos.carga_horaria " +
                "FROM cursos " +
                "JOIN matricula ON cursos.id_curso = matricula.id_curso " +
                "LEFT JOIN notas ON matricula.id_curso = notas.id_curso AND matricula.id_aluno = notas.id_aluno " +
                "WHERE notas.nota >= 7 AND matricula.id_aluno = ?";

        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, aluno.getIdAluno());
        
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso(
                            resultSet.getInt("id_curso"),
                            resultSet.getString("nome_curso"),
                            resultSet.getString("status_curso"),
                            resultSet.getInt("carga_horaria"));

                    cursosConcluidos.add(curso);
                }
            }
        }

        return cursosConcluidos;
    }

    public List<Curso> listarCursosMatriculados(Aluno aluno) throws SQLException {
        List<Curso> cursosMatriculados = new ArrayList<>();

        String query = "SELECT cursos.id_curso, cursos.nome_curso, cursos.status_curso, cursos.carga_horaria " +
                "FROM cursos " +
                "JOIN matricula ON cursos.id_curso = matricula.id_curso " +
                "WHERE matricula.id_aluno = ?";

        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, aluno.getIdAluno());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso(
                            resultSet.getInt("id_curso"),
                            resultSet.getString("nome_curso"),
                            resultSet.getString("status_curso"),
                            resultSet.getInt("carga_horaria"));

                    cursosMatriculados.add(curso);
                }
            }
        }

        return cursosMatriculados;
    }
    public double calcularAproveitamento(Aluno aluno) throws SQLException {
        String query = "SELECT AVG(nota) AS media " +
                       "FROM notas " +
                       "WHERE id_aluno = ?";

        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, aluno.getIdAluno());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double mediaNotas = resultSet.getDouble("media");
                    double porcentagemAproveitamento = (mediaNotas / 10.0) * 100.0;

                    return porcentagemAproveitamento;
                } else {
                    return 0.0;
                }
            }
        }
    }
}