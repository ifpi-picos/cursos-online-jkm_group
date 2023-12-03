package demo.DAO.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.DAO.MatriculaDAO;
import demo.entidades.Aluno;
import demo.entidades.Curso;
import demo.entidades.Matricula;
import demo.excecoes.NotFoundException;

public class MatriculaDAOImplementacao implements MatriculaDAO {
    private Connection conexaoDB;

    public MatriculaDAOImplementacao(Connection conexaoDB) {
        this.conexaoDB = conexaoDB;
    }

    public void matricular(int alunoId, int cursoId) {
        String query = "INSERT INTO matricula (id_aluno, id_curso, status) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, alunoId);
            preparedStatement.setInt(2, cursoId);
            preparedStatement.setString(3, "Ativa");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelarMatricula(int matricula) throws NotFoundException {
        String query = "UPDATE matricula SET status='Inativa' WHERE id_matricula=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, matricula);
            int linhasAtualizadas = preparedStatement.executeUpdate();

            if (linhasAtualizadas == 0) {
                throw new NotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Matricula obterPorIdECursoId(int matriculaId, int cursoId) throws NotFoundException {
        String query = "SELECT M.id_aluno, M.nota, A.nome_aluno FROM matricula M INNER JOIN aluno A ON A.id_aluno = M.id_aluno WHERE M.id_matricula=? AND M.id_curso=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, matriculaId);
            preparedStatement.setInt(2, cursoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setNome(resultSet.getString("nome_aluno"));

                    Matricula matricula = new Matricula();
                    matricula.setNota(resultSet.getDouble("nota"));
                    matricula.setAluno(aluno);

                    return matricula;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new NotFoundException();
    }

    public List<Matricula> obterTodasPorAluno(int alunoId) {
        String query = "SELECT M.id_matricula, M.status, M.nota, " +
                "C.nome_curso " +
                "FROM matricula M " +
                "INNER JOIN curso C ON C.id_curso = M.id_curso " +
                "WHERE M.id_aluno=?";

        List<Matricula> matriculas = new ArrayList<>();
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, alunoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso();
                    curso.setNome(resultSet.getString("nome_curso"));

                    Matricula matricula = new Matricula();
                    matricula.setId(resultSet.getInt("id_matricula"));
                    matricula.setStatus(resultSet.getString("status"));
                    matricula.setNota(resultSet.getDouble("nota"));

                    matricula.setCurso(curso);

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matriculas;
    }

    public List<Matricula> obterTodasConcluidasPorAluno(int alunoId) {
        String query = "SELECT M.id_matricula, M.status, M.nota, " +
                "C.nome_curso " +
                "FROM matricula M " +
                "INNER JOIN curso C ON C.id_curso = M.id_curso " +
                "WHERE M.id_aluno=? AND M.status='Finalizado'";

        List<Matricula> matriculas = new ArrayList<>();
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, alunoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Curso curso = new Curso();
                    curso.setNome(resultSet.getString("nome_curso"));

                    Matricula matricula = new Matricula();
                    matricula.setId(resultSet.getInt("id_matricula"));
                    matricula.setStatus(resultSet.getString("status"));
                    matricula.setNota(resultSet.getDouble("nota"));

                    matricula.setCurso(curso);

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matriculas;
    }

    public List<Matricula> obterTodasPorCurso(Curso curso) {
        List<Matricula> matriculas = new ArrayList<>();

        String query = "SELECT M.id_matricula, A.id_aluno, A.nome_aluno, A.email_Aluno, M.nota " +
                "FROM aluno A " +
                "INNER JOIN matricula M ON M.id_aluno = A.id_aluno " +
                "WHERE M.id_curso=? AND M.status='Ativa'";

        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, curso.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setId(resultSet.getInt("id_aluno"));
                    aluno.setNome(resultSet.getString("nome_aluno"));
                    aluno.setEmail(resultSet.getString("email_aluno"));

                    Matricula matricula = new Matricula();
                    matricula.setId(resultSet.getInt("id_matricula"));
                    matricula.setAluno(aluno);
                    matricula.setCurso(curso);
                    matricula.setNota(resultSet.getDouble("nota"));

                    matriculas.add(matricula);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matriculas;
    }

    public void registrarNotaAluno(int matricula, double nota) throws NotFoundException {
        String query = "UPDATE matricula SET nota=? WHERE id_matricula=?";
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setDouble(1, nota);
            preparedStatement.setInt(2, matricula);
            int linhasAtualizadas = preparedStatement.executeUpdate();
            if (linhasAtualizadas == 0) {
                throw new NotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int contarMatriculasPorCurso(int cursoId) {
        String query = "SELECT COUNT(*) AS qtd_matricula FROM matricula WHERE id_curso=? AND status='Ativa'";
        int qtdMatriculas = 0;
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, cursoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    qtdMatriculas = resultSet.getInt("qtd_matricula");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return qtdMatriculas;
    }

    public double calcularMediaGeralPorCurso(int cursoId) {
        String query = "SELECT AVG(nota) AS media FROM matricula WHERE id_curso=? AND status='Ativa'";
        double media = 0;
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, cursoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    media = resultSet.getDouble("media");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return media;
    }

    public double calcularPercentualAlunosAprovadosPorCurso(int cursoId) {
        String query = "SELECT COUNT(*) / (SELECT CAST(NULLIF(COUNT(*), 0) AS DECIMAL(10,2)) FROM matricula WHERE id_curso=? AND status='Ativa') AS percentual "
                +
                "FROM matricula WHERE id_curso=? AND nota >= 7 AND status='Ativa'";
        double percentual = 0;
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, cursoId);
            preparedStatement.setInt(2, cursoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    percentual = resultSet.getDouble("percentual");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return percentual;
    }

    public double calcularPercentualAproveitamentoPorAluno(int alunoId) {
        String query = "SELECT COUNT(*) / (SELECT NULLIF(COUNT(*), 0) FROM matricula WHERE id_aluno=?) AS percentual " +
                "FROM matricula WHERE id_aluno=? AND nota >= 7";
        double percentual = 0;
        try (PreparedStatement preparedStatement = conexaoDB.prepareStatement(query)) {
            preparedStatement.setInt(1, alunoId);
            preparedStatement.setInt(2, alunoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    percentual = resultSet.getDouble("percentual");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return percentual;
    }
}
