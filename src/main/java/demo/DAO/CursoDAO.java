package demo.DAO;

import java.sql.SQLException;
import java.util.List;
import demo.entidades.Curso;

public interface CursoDAO{
    public void cadastrarCurso(Curso curso) throws SQLException;
    public void atualizarCurso(Curso curso) throws SQLException;
    public double registrarNota(int id_aluno, int id_curso) throws SQLException;
    public double calcularMediaNotas(int id_curso) throws SQLException;
    public double calcularMediaGeral() throws SQLException;
    public double calcularPorcentagemAprovados() throws SQLException;
    public double calcularPorcentagemReprovados() throws SQLException;
    public int contarAlunosMatriculados(int id_curso) throws SQLException;
    public List<Curso> listarCursos() throws SQLException;
}