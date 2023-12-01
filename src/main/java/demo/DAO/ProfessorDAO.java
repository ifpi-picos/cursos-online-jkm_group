package demo.DAO;

import java.sql.SQLException;
import java.util.List;
import demo.entidades.Curso;
import demo.entidades.Professor;

public interface  ProfessorDAO{
    public void cadastrarProfessor(Professor professor) throws SQLException;
    public void atualizarProfessor(Professor professor) throws SQLException;
    public List<Professor> listarProfessores() throws SQLException;
    public List<Curso> listarCursosMinistrados() throws SQLException;
}