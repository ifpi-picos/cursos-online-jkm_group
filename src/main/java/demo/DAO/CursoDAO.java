package demo.DAO;

import java.util.List;

import demo.entidades.Curso;
import demo.excecoes.NotFoundException;

public interface CursoDAO {
    public void cadastrar(Curso entidade);

    public void atualizar(int id, Curso curso);

    public Curso buscarPorId(int id) throws NotFoundException;

    public List<Curso> obterTodos();
}