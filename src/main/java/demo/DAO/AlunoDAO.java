package demo.DAO;

import java.util.List;
import demo.entidades.Aluno;

public interface AlunoDAO{
    public void cadastrarAluno(Aluno aluno) throws Exception;
    public void atualizarAluno(int id, Aluno alunoDados) throws Exception;
    public void matricularAlunoEmCurso(Aluno aluno, String curso) throws Exception;
    public void cancelarMatriculaAluno(Aluno aluno, String curso) throws Exception;
    public void gerarRelatorioDesempenho(Aluno aluno) throws Exception;
    public List<Aluno> listarAlunos() throws Exception;
}  