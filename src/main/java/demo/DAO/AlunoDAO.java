package demo.DAO;

import java.util.List;

import demo.entidades.Aluno;
import demo.excecoes.NotFoundException;
import demo.excecoes.UnauthorizedException;

public interface AlunoDAO {
    public void cadastrar(Aluno alunoDados);

    public void atualizar(int id, Aluno alunoDados);

    public Aluno autenticar(String email, String senha) throws UnauthorizedException;

    public List<Aluno> obterTodos();

    public Aluno obterPorId(int alunoId) throws NotFoundException;
}
