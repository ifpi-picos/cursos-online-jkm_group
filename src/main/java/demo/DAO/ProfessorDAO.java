package demo.DAO;

import java.util.List;
import demo.entidades.Curso;
import demo.entidades.Professor;

public interface  ProfessorDAO{
    public void cadastrarProfessor(Professor professor) throws Exception;
    public void atualizarProfessores(Professor professor) throws Exception;
    public List<Professor> listarProfessor() throws Exception;
    public List<Curso> listarCursosMinistrados() throws Exception;
}