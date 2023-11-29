package demo;

import java.sql.SQLException;

import demo.DAO.implementacao.AlunoDAOimplementacao;
import demo.DAO.implementacao.CursoDAOimplementacao;
import demo.DAO.implementacao.ProfessorDAOimplementacao;
import demo.entidades.Aluno;
import demo.entidades.Professor;
import demo.entidades.Curso;
import demo.ConexaoDB;
import java.sql.Connection;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static int opcao = 0;

    private static AlunoDAOimplementacao alunoDao;
    private static ProfessorDAOimplementacao professorDAO;
    private static CursoDAOimplementacao cursoDAO;

    public static void main(String[] args) {
        // Conexão com o banco de dados
        ConexaoDB db = new ConexaoDB();
        Connection conexaoDB = db.retonarConexao();

        // Instanciar os DAOs
        alunoDao = new AlunoDAOimplementacao(conexaoDB);
        professorDAO = new ProfessorDAOimplementacao(conexaoDB);
        cursoDAO = new CursoDAOimplementacao(conexaoDB);

        do {
            System.out.println("--------------------------------------------");
            System.out.println("| ->          Sistema acadêmico         <- |");
            System.out.println("| 1 - Acessar o sistema.                   |");
            System.out.println("| 2 - Cadastrar na plataforma.             |");
            System.out.println("--------------------------------------------");

            System.out.print("\nDigite o número da opção desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Cleaning the scanner.

            switch (opcao) {
                case 1: 
                    cadastrarProfessor();
                    break;
                case 2:
                    cadastrarAluno();
                    break;
                case 3:                     
                    cadastrarCurso();                
                    
            }

        } while (true);


    }

    public static void acessarPlataforma() {
            String email, senha;
            int cargaHoraria, statusCode;
            Curso curso;

            System.out.println("--------------------------------------------");
            System.out.println("| ->               LOGIN                <- |");
            System.out.println("--------------------------------------------");
            
            System.out.print("\nDigite seu e-mail: ");
            email = scanner.nextLine();

            System.out.print("\nDigite sua senha: ");
            senha = scanner.nextLine();

            // Realizar a autenticação
            // 1 - Consultar se o e-mail e senha existem na tabela Professor -> Perfil Professor
            // 2 - Consulta se o e-mail e senha existem na tabela Aluno -> Perfil Aluno
            // 3 - Informar que o usuário não existe.
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
            senha = scanner.nextLine();

            System.out.print("\nDigite sua senha: ");
            senha = scanner.nextLine();

            System.out.print("\nEscolha um perfil (1 - Professor ou 2 - Aluno): ");
            perfilCodigo = scanner.nextInt();
            scanner.nextLine();
            
            if (perfilCodigo == 1) {
                // Cadastrar na tabela Professor
            } else {
                // Cadastrar na tabela Aluno
            }
    }

    public static void cadastrarCurso() {
            String nome, status;
            int cargaHoraria, statusCode;
            Curso curso;

            System.out.println("--------------------------------------------");
            System.out.println("| ->               Curso                <- |");
            System.out.println("--------------------------------------------");
            
            System.out.print("\nDigite o nome do curso: ");
            nome = scanner.nextLine();

            System.out.print("\nDigite o status do curso [1 -> Disponível, 2 -> Indisponível]: ");
            statusCode = scanner.nextInt();

            if (statusCode == 1) {
                status = "Disponível";
            } else {
                status = "Indisponível";
            }

            System.out.print("\nDigite a carga horária do curso [Apenas números]: ");
            cargaHoraria = scanner.nextInt();

            curso = new Curso(nome, status, cargaHoraria);

            try {
                cursoDAO.cadastrarCurso(curso);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void cadastrarAluno() {
            String nome, email;
            Aluno aluno;

            System.out.println("--------------------------------------------");
            System.out.println("| ->               Aluno                 <- |");
            System.out.println("--------------------------------------------");
            
            System.out.print("\nDigite o nome do aluno: ");
            nome = scanner.nextLine();

            System.out.print("\nDigite o email do aluno: ");
            email = scanner.nextLine();

            aluno =  new Aluno(nome, email);

            try {
                alunoDao.cadastrarAluno(aluno);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void cadastrarProfessor() {
            String nome, email;
            Professor professor;

            System.out.println("--------------------------------------------");
            System.out.println("| ->               Professor            <- |");
            System.out.println("--------------------------------------------");
            
            System.out.print("\nDigite o nome do professor: ");
            nome = scanner.nextLine();

            System.out.print("\nDigite o email do professor: ");
            email = scanner.nextLine();

            professor =  new Professor(nome, email);

            try {
                professorDAO.cadastrarProfessor(professor);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
