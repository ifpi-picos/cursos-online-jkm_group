package demo.ui;

import demo.entidades.Aluno;
import demo.entidades.Professor;
import demo.excecoes.NotFoundException;

public class ProfessorUi {
    private static int opcaoUiProfessor = 0;

    public static void menuProfessor(Professor professor) {
        do {
            System.out.println("---------------------------------------------------");
            System.out.println("| ->                    MENU                   <- |");
            System.out.println("---------------------------------------------------");
            System.out.println("| 1 - Gerenciar cursos.                           |");
            System.out.println("| 2 - Atualizar minhas informações.               |");
            System.out.println("| 3 - Listar todos os alunos.                     |");
            System.out.println("| 4 - Visualizar perfil de um aluno.              |");
            System.out.println("| 5 - Sair.                                       |");
            System.out.println("---------------------------------------------------");
            System.out.println("Olá, " + professor.getNome());

            System.out.print("\nDigite o número da opção desejada: ");
            opcaoUiProfessor = Integer.parseInt(App.scanner.nextLine());

            switch (opcaoUiProfessor) {
                case 1:
                    CursoUi.menuCursos(professor);
                    break;
                case 2:
                    atualizarInformacoes(professor);
                    break;
                case 3:
                    AlunoUi.listarAlunos();
                    break;
                case 4:
                    visualizarPerfilAluno();
            }

        } while (opcaoUiProfessor != 5);
    }

    public static void atualizarInformacoes(Professor professor) {
        String nome, email, senha;

        System.out.println("------------------------------------------------");
        System.out.println("| ->               Professor                <- |");
        System.out.println("------------------------------------------------");

        System.out.print("\nDigite seu nome (Caso não queira atualizar, deixe em branco): ");
        nome = App.scanner.nextLine();

        if (!nome.isEmpty()) {
            professor.setNome(nome);
        }

        System.out.print("\nDigite seu email (Caso não queira atualizar, deixe em branco): ");
        email = App.scanner.nextLine();

        if (!email.isEmpty()) {
            professor.setEmail(email);
        }

        System.out.print("\nDigite sua nova senha (Caso não queira atualizar, deixe em branco): ");
        senha = App.scanner.nextLine();

        if (!senha.isEmpty()) {
            professor.setSenha(senha);
        }

        App.professorDAO.atualizar(professor.getId(), professor);
    }

    public static void visualizarPerfilAluno() {
        Aluno aluno;
        do {
            try {
                System.out.print("Digite o ID do aluno: ");
                int alunoId = Integer.parseInt(App.scanner.nextLine());

                aluno = App.alunoDAO.obterPorId(alunoId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
            } catch (NotFoundException e) {
                System.out.println("Não foi possível encontrar um aluno com esse ID.");
            }
        } while (true);

        AlunoUi.imprimirPerfil(aluno);
    }
}
