package demo.DAO;

import java.util.List;
import demo.entidades.Curso;

public interface CursoDAO{
    public void cadastrarCurso();
    public void atualizarCurso();
    public double registrarNota();
    public double calcularMediaNotas();
    public List<Curso> listaCursos();
}