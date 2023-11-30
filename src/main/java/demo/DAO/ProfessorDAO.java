package demo.DAO;

import java.util.List;
import demo.entidades.Curso;
import demo.entidades.Professor;

public interface  ProfessorDAO{
    public void cadastrarProfessor(Professor professor) throws Exception;
    public void atualizarProfessor(Professor professor) throws Exception;
    public List<Professor> listarProfessores() throws Exception;
    public List<Curso> listarCursosMinistrados() throws Exception;
}