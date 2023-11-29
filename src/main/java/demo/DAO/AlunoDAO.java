package demo.DAO;

import java.util.List;

import demo.entidades.Aluno;

public interface AlunoDAO{
    public void cadastrarAluno();
    public void atualizarAluno();
    public void matricularAlunoEmCurso();
    public void cancelarMatriculaAluno();
    public void gerarRelatorioDesempenho();
    public List<Aluno> listarAlunos();
}  