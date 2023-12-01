package demo.DAO;

import java.sql.SQLException;
import java.util.List;
import demo.entidades.Aluno;
import demo.entidades.Curso;

public interface AlunoDAO{
    public void cadastrarAluno(Aluno aluno) throws SQLException;
    public void atualizarAluno(Aluno alunoDados) throws SQLException;
    public void matricularAlunoEmCurso(Aluno aluno, Curso curso) throws SQLException;
    public void cancelarMatriculaAluno(Aluno aluno, Curso curso) throws SQLException;
    public void gerarRelatorioDesempenho(Aluno aluno) throws SQLException;
    public double calcularAproveitamento(Aluno aluno) throws SQLException;
    public List<Aluno> listarAlunos() throws SQLException;
    public List<Curso> listarCursosConcluidos(Aluno aluno) throws SQLException;
    public List<Curso> listarCursosMatriculados(Aluno aluno) throws SQLException;
}  