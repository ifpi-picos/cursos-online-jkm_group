package demo.ui;

import java.util.List;

import demo.entidades.Curso;
import demo.entidades.Matricula;
import demo.excecoes.NotFoundException;

public class NotasUi {
    private static int opcaoUiNotas = 0;

    public static void menuNotas(Curso curso) {
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("| ->                      NOTAS                    <- |");
            System.out.println("-------------------------------------------------------");
            System.out.println("Curso: " + curso.getNome());
            System.out.println("------------------------- Opções -----------------------");
            System.out.println("| 1 - Registrar/Atualizar as notas de todos os alunos. |");
            System.out.println("| 2 - Registrar/Atualizar a nota de um único aluno.    |");
            System.out.println("| 3 - Visualizar todas as notas do curso.              |");
            System.out.println("| 4 - Voltar.                                          |");
            System.out.println("--------------------------------------------------------");

            System.out.print("\nDigite o número da opção desejada: ");
            opcaoUiNotas = Integer.parseInt(App.scanner.nextLine());

            switch (opcaoUiNotas) {
                case 1:
                    registrarTodasNotas(curso);
                    break;
                case 2:
                    registrarNotaAluno(curso);
                    break;
                case 3:
                    visualizarNotas(curso);
            }

        } while (opcaoUiNotas != 4);
    }

    public static void registrarTodasNotas(Curso curso) {
        List<Matricula> matriculas = App.matriculaDAO.obterTodasPorCurso(curso);

        double nota = 0;
        for (int i = 0; i < matriculas.size(); i++) {
            do {
                try {
                    System.out.print("Nota do aluno " + matriculas.get(i).getAluno().getNome() + ": ");
                    nota = Double.parseDouble(App.scanner.nextLine());

                    App.matriculaDAO.registrarNotaAluno(matriculas.get(i).getId(), nota);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido.");
                } catch (NotFoundException e) {
                    System.out.println("A matrícula do aluno não foi encontrada.");
                }
            } while (true);
        }
    }

    public static void registrarNotaAluno(Curso curso) {
        int matriculaId;
        Matricula matricula;
        do {
            try {
                System.out.print("Digite a matricula do aluno (ou 0 para sair): ");
                matriculaId = Integer.parseInt(App.scanner.nextLine());

                if (matriculaId == 0) {
                    return;
                }

                matricula = App.matriculaDAO.obterPorIdECursoId(matriculaId, curso.getId());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            } catch (NotFoundException e) {
                System.out.println("Matricula inválida.");
            }
        } while (true);

        System.out.println("Aluno: " + matricula.getAluno().getNome());
        if (matricula.getNota() != null) {
            System.out.println("Nota atual: " + matricula.getNota());
        }

        double nota;
        do {
            try {
                System.out.print("Digite a nota do aluno: ");
                nota = Double.parseDouble(App.scanner.nextLine());
                App.matriculaDAO.registrarNotaAluno(matriculaId, nota);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Nota inválida.");
            } catch (NotFoundException e) {
                System.out.println("Matricula inválida.");
            }
        } while (true);
    }

    public static void visualizarNotas(Curso curso) {
        List<Matricula> matriculas = App.matriculaDAO.obterTodasPorCurso(curso);

        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "|                                            NOTAS                                              <- |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "| Nº MATRICULA |                       NOME DO ALUNO              |               NOTA             |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (int i = 0; i < matriculas.size(); i++) {
            System.out.println("| " + matriculas.get(i).getId() + " | " + matriculas.get(i).getAluno().getNome() + " | "
                    + matriculas.get(i).getNota() + " |");
        }

        System.out.println("Pressione qualquer tecla para voltar...");
        App.scanner.nextLine();
    }
}