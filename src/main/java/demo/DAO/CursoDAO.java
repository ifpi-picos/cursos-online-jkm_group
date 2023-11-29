package demo.DAO;

import java.util.List;
import demo.entidades.Curso;

public interface CursoDAO{
    public void cadastrarCurso(Curso curso) throws Exception;
    public void atualizarCurso(Curso curso) throws Exception;
    public double registrarNota(int id_aluno, int id_curso) throws Exception;
    public double calcularMediaNotas(int id_curso) throws Exception;
    public List<Curso> listarCursos() throws Exception;
}