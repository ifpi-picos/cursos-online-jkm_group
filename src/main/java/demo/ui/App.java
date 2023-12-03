package demo.ui;

import java.sql.Connection;
import java.util.Scanner;

import demo.ConexaoDB;
import demo.DAO.AlunoDAO;
import demo.DAO.CursoDAO;
import demo.DAO.MatriculaDAO;
import demo.DAO.ProfessorDAO;
import demo.DAO.implementacao.AlunoDAOImplementacao;
import demo.DAO.implementacao.CursoDAOImplementacao;
import demo.DAO.implementacao.MatriculaDAOImplementacao;
import demo.DAO.implementacao.ProfessorDAOImplementacao;
import demo.entidades.Aluno;
import demo.entidades.Professor;
import demo.enums.Perfil;
import demo.excecoes.UnauthorizedException;

public class App {
    private static int opcaoPrincipal = 0;

    protected static Scanner scanner = new Scanner(System.in);
    protected static AlunoDAO alunoDAO;
    protected static ProfessorDAO professorDAO;
    protected static CursoDAO cursoDAO;
    protected static MatriculaDAO matriculaDAO;

    public static void main(String[] args) {
        // Conexão com o banco de dados
        ConexaoDB db = new ConexaoDB();
        Connection conexaoDB = db.retonarConexao();

        // Instanciar os DAOs
        alunoDAO = new AlunoDAOImplementacao(conexaoDB);
        professorDAO = new ProfessorDAOImplementacao(conexaoDB);
        cursoDAO = new CursoDAOImplementacao(conexaoDB);
        matriculaDAO = new MatriculaDAOImplementacao(conexaoDB);

        do {
            System.out.println("--------------------------------------------");
            System.out.println("| ->          Sistema acadêmico         <- |");
            System.out.println("--------------------------------------------");
            System.out.println("| 1 - Acessar a plataforma (aluno).        |");
            System.out.println("| 2 - Acessar a plataforma (professor).    |");
            System.out.println("| 3 - Criar uma conta.                     |");
            System.out.println("| 4 - Encerrar o programa.                 |");
            System.out.println("--------------------------------------------");

            System.out.print("\nDigite o número da opção desejada: ");
            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine(); // Cleaning the scanner.

            switch (opcaoPrincipal) {
                case 1:
                    acessarPlataforma(Perfil.ALUNO);
                    break;
                case 2:
                    acessarPlataforma(Perfil.PROFESSOR);
                    break;
                case 3:
                    cadastrarUsuario();
            }

        } while (opcaoPrincipal != 4);

    }

    public static void acessarPlataforma(Perfil perfil) {
        String email, senha;

        System.out.println("--------------------------------------------");
        System.out.println("| ->               LOGIN                <- |");
        System.out.println("--------------------------------------------");

        System.out.print("\nDigite seu e-mail: ");
        email = scanner.nextLine();

        System.out.print("\nDigite sua senha: ");
        senha = scanner.nextLine();

        try {
            if (perfil == Perfil.ALUNO) {
                Aluno aluno = alunoDAO.autenticar(email, senha);
                AlunoUi.menuAluno(aluno);
            } else {
                Professor professor = professorDAO.autenticar(email, senha);
                ProfessorUi.menuProfessor(professor);
            }
        } catch (UnauthorizedException e) {
            System.out.println("--------------------------------------------");
            System.out.println("| ->          DADOS INVÁLIDOS           <- |");
            System.out.println("--------------------------------------------");
            System.out.print("Pressione qualquer tecla para continuar...");
            scanner.nextLine();
        }

    }

    public static void cadastrarUsuario() {
        String email, senha, nome;
        int perfilCodigo;

        System.out.println("--------------------------------------------");
        System.out.println("| ->             CADASTRO                <- |");
        System.out.println("--------------------------------------------");

        System.out.print("\nDigite seu e-mail: ");
        email = scanner.nextLine();

        System.out.print("\nDigite seu nome: ");
        nome = scanner.nextLine();

        System.out.print("\nDigite sua senha: ");
        senha = scanner.nextLine();

        System.out.print("\nEscolha um perfil (1 - Professor ou 2 - Aluno): ");
        perfilCodigo = scanner.nextInt();
        scanner.nextLine();

        if (perfilCodigo == 1) {
            Professor professor = new Professor();
            professor.setNome(nome);
            professor.setEmail(email);
            professor.setSenha(senha);
            professorDAO.cadastrar(professor);
        } else {
            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setEmail(email);
            aluno.setSenha(senha);
            alunoDAO.cadastrar(aluno);
        }
    }
}
