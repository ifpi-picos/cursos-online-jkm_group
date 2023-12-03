package demo.DAO;

import java.util.List;

import demo.entidades.Curso;
import demo.entidades.Matricula;
import demo.excecoes.NotFoundException;

public interface MatriculaDAO {
    public List<Matricula> obterTodasPorAluno(int alunoId);

    public List<Matricula> obterTodasConcluidasPorAluno(int alunoId);

    public List<Matricula> obterTodasPorCurso(Curso curso);

    public Matricula obterPorIdECursoId(int matriculaId, int cursoId) throws NotFoundException;

    public void matricular(int alunoId, int cursoId);

    public void cancelarMatricula(int matricula) throws NotFoundException;

    public void registrarNotaAluno(int matricula, double nota) throws NotFoundException;

    public int contarMatriculasPorCurso(int cursoId);

    public double calcularMediaGeralPorCurso(int cursoId);

    public double calcularPercentualAlunosAprovadosPorCurso(int cursoId);

    public double calcularPercentualAproveitamentoPorAluno(int alunoId);
}
