package demo.DAO;

import java.util.List;
import demo.entidades.Curso;
import demo.entidades.Professor;

public interface  ProfessorDAO{
    public void cadastrarProfessor();
    public void atualizarProfessores();
    public List<Professor> listarProfessor();
    public List<Curso> listarCursosMinistrados();
}