package demo.DAO;

import java.util.List;

import demo.entidades.Professor;
import demo.excecoes.NotFoundException;
import demo.excecoes.UnauthorizedException;

public interface ProfessorDAO {
    public void cadastrar(Professor professorDados);

    public void atualizar(int id, Professor professorDados);

    public Professor autenticar(String email, String senha) throws UnauthorizedException;

    public Professor obterPorId(int id) throws NotFoundException;

    public List<Professor> obterTodos();
}