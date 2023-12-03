package demo.ui;

import java.util.List;

import demo.entidades.Aluno;
import demo.entidades.Matricula;
import demo.excecoes.NotFoundException;

public class AlunoUi {
    private static int opcaoUiAluno = 0;

    public static void menuAluno(Aluno aluno) {
        do {
            System.out.println("---------------------------------------------------");
            System.out.println("| ->                    MENU                   <- |");
            System.out.println("---------------------------------------------------");
            System.out.println("| 1 - Atualizar minhas informações.               |");
            System.out.println("| 2 - Visualizar cursos                           |");
            System.out.println("| 3 - Matricular em um curso.                     |");
            System.out.println("| 4 - Visualizar todas as minhas matrículas.      |");
            System.out.println("| 5 - Visualizar minhas matrículas concluídas.    |");
            System.out.println("| 6 - Cancelar a matrícula de um curso.           |");
            System.out.println("| 7 - Visualizar percentual de aproveitamento.    |");
            System.out.println("| 8 - Sair.                                       |");
            System.out.println("---------------------------------------------------");
            System.out.println("Olá, " + aluno.getNome());

            System.out.print("\nDigite o número da opção desejada: ");
            opcaoUiAluno = Integer.parseInt(App.scanner.nextLine());

            switch (opcaoUiAluno) {
                case 1:
                    atualizarInformacoes(aluno);
                    break;
                case 2:
                    CursoUi.listarCursos();
                    break;
                case 3:
                    matricularAlunoEmCurso(aluno);
                    break;
                case 4:
                    listarTodasMatriculas(aluno.getId());
                    break;
                case 5:
                    listarMatriculasConcluidas(aluno.getId());
                    break;
                case 6:
                    cancelarMatricula(aluno);
                    break;
                case 7:
                    visualizarPercentualAproveitamento(aluno.getId());
            }

        } while (opcaoUiAluno != 8);
    }

    public static void atualizarInformacoes(Aluno aluno) {
        String nome, email, senha;

        System.out.println("------------------------------------------------");
        System.out.println("| ->                   Aluno                <- |");
        System.out.println("------------------------------------------------");

        System.out.print("\nDigite seu nome (Caso não queira atualizar, deixe em branco): ");
        nome = App.scanner.nextLine();

        if (!nome.isEmpty()) {
            System.out.println(nome);
            aluno.setNome(nome);
        }

        System.out.print("\nDigite seu email (Caso não queira atualizar, deixe em branco): ");
        email = App.scanner.nextLine();

        if (!email.isEmpty()) {
            System.out.println(email);
            aluno.setEmail(email);
        }

        System.out.print("\nDigite sua nova senha (Caso não queira atualizar, deixe em branco): ");
        senha = App.scanner.nextLine();

        if (!senha.isEmpty()) {
            System.out.println(senha);
            aluno.setSenha(senha);
        }

        App.alunoDAO.atualizar(aluno.getId(), aluno);
    }

    public static void matricularAlunoEmCurso(Aluno aluno) {
        int cursoId;

        System.out.println("------------------------------------------------");
        System.out.println("| ->         Realizando Matrícula           <- |");
        System.out.println("------------------------------------------------");

        do {
            try {
                System.out.print("\nDigite o ID do curso que deseja se matricular: ");
                cursoId = Integer.parseInt(App.scanner.nextLine());

                App.cursoDAO.buscarPorId(cursoId); // Verificando se o curso existe
                break;
            } catch (NotFoundException e) {
                System.out.println("\nNão foi possível encontrar o curso com o ID digitado.");
            } catch (NumberFormatException e) {
                System.out.println("\nInforme um ID válido.");
            }
        } while (true);

        App.matriculaDAO.matricular(aluno.getId(), cursoId);
    }

    public static void cancelarMatricula(Aluno aluno) {
        int matriculaId;

        System.out.println("------------------------------------------------");
        System.out.println("| ->         Cancelando Matrícula           <- |");
        System.out.println("------------------------------------------------");

        do {
            try {
                System.out.print("\nDigite o a matrícula que deseja cancelar: ");
                matriculaId = Integer.parseInt(App.scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nInforme um número de matrícula que seja válido.");
            }
        } while (true);

        try {
            App.matriculaDAO.cancelarMatricula(matriculaId);
        } catch (NotFoundException e) {
            System.out.println("\nNão foi possível encontrar a matrícula informada.");
        }

    }

    public static void listarTodasMatriculas(int alunoId) {
        List<Matricula> matriculas = App.matriculaDAO.obterTodasPorAluno(alunoId);
        imprimirMatriculas(matriculas);
    }

    public static void listarMatriculasConcluidas(int alunoId) {
        List<Matricula> matriculas = App.matriculaDAO.obterTodasConcluidasPorAluno(alunoId);
        imprimirMatriculas(matriculas);
    }

    public static void imprimirMatriculas(List<Matricula> matriculas) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "| ->                                       MATRICULAS                                           <- |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "|    Nº Matrícula     |                 NOME DO CURSO             |      STATUS    |      NOTA     |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (int i = 0; i < matriculas.size(); i++) {
            System.out.println("| " + matriculas.get(i).getId() + " | " + matriculas.get(i).getCurso().getNome() + " | "
                    + matriculas.get(i).getStatus() + " | " + matriculas.get(i).getNota() + " | ");
        }

        System.out.println("Pressione qualquer tecla para voltar...");
        App.scanner.nextLine();

    }

    public static void imprimirPerfil(Aluno aluno) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "|                                      PERFIL DO ALUNO                                          <- |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        System.out.println("ID: " + aluno.getId());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("E-mail: " + aluno.getEmail());

        listarTodasMatriculas(aluno.getId());
    }

    public static void visualizarPercentualAproveitamento(int alunoId) {
        double percentual = App.matriculaDAO.calcularPercentualAproveitamentoPorAluno(alunoId);

        System.out.println("-------------------------------------------------");
        System.out.println("| -> Percentual de aproveitamento nos cursos <- |");
        System.out.println("-------------------------------------------------");
        System.out.println(" -- " + percentual * 100 + "% -- ");

        System.out.println("Pressione qualquer tecla para voltar...");
        App.scanner.nextLine();
    }

    public static void listarAlunos() {
        List<Aluno> alunos = App.alunoDAO.obterTodos();

        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "|                                            ALUNOS                                              <- |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "|    ID    |                       NOME                    |                    EMAIL              |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (int i = 0; i < alunos.size(); i++) {
            System.out.println("| " + alunos.get(i).getId() + " | " + alunos.get(i).getNome() + " | "
                    + alunos.get(i).getEmail() + " |");
        }

        System.out.println("Pressione qualquer tecla para voltar...");
        App.scanner.nextLine();
    }
}
