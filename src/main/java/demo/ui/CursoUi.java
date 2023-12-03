package demo.ui;

import java.util.List;

import demo.entidades.Curso;
import demo.entidades.Professor;
import demo.excecoes.NotFoundException;

public class CursoUi {
    private static int opcaoUiCurso = 0;

    public static void menuCursos(Professor professor) {
        do {
            System.out.println("---------------------------------------------------------------");
            System.out.println("| ->                          CURSOS                       <- |");
            System.out.println("---------------------------------------------------------------");
            System.out.println("| 1 - Cadastrar um novo curso.                                |");
            System.out.println("| 2 - Atualizar um curso existente.                           |");
            System.out.println("| 3 - Listar cursos.                                          |");
            System.out.println("| 4 - Gerenciar notas de um curso.                            |");
            System.out.println("| 5 - Gerar relatório de desempenho dos alunos de um curso.   |");
            System.out.println("| 6 - Voltar.                                                 |");
            System.out.println("---------------------------------------------------------------");
            System.out.println("Olá, " + professor.getNome());

            System.out.print("\nDigite o número da opção desejada: ");
            opcaoUiCurso = App.scanner.nextInt();
            App.scanner.nextLine(); // Cleaning the scanner.

            switch (opcaoUiCurso) {
                case 1:
                    cadastrarCurso(professor);
                    break;
                case 2:
                    atualizarCurso();
                    break;
                case 3:
                    listarCursos();
                    break;
                case 4:
                    gerenciarNotas();
                    break;
                case 5:
                    gerarRelatorioDesempenho();
            }

        } while (opcaoUiCurso != 6);
    }

    public static void cadastrarCurso(Professor professor) {
        int statusCode, outroProfessorId;
        Curso curso;
        Professor outroProfessor;

        curso = new Curso();

        System.out.println("--------------------------------------------");
        System.out.println("| ->               Curso                <- |");
        System.out.println("--------------------------------------------");

        System.out.print("\nDigite o nome do curso: ");
        curso.setNome(App.scanner.nextLine());

        System.out.print("\nDigite o status do curso [1 -> Aberto, 2 -> Fechado]: ");
        statusCode = Integer.parseInt(App.scanner.nextLine());

        if (statusCode == 1) {
            curso.setStatus("Aberto");
        } else {
            curso.setStatus("Fechado");
        }

        System.out.print("\nDigite a carga horária do curso [Apenas números]: ");
        curso.setCargaHoraria(Integer.parseInt(App.scanner.nextLine()));

        do {
            try {
                System.out.print(
                        "\nDigite o ID do ministrante (Caso deixe em branco, você ficará definido como ministrante): ");
                outroProfessorId = Integer.parseInt(App.scanner.nextLine());

                outroProfessor = App.professorDAO.obterPorId(outroProfessorId);
                curso.setMinistrante(outroProfessor);
                break;
            } catch (NotFoundException e) {
                System.out.println("\nNão foi possível encontrar o professor com o ID digitado.");
            } catch (NumberFormatException e) {
                System.out.println("\nO ministrante será você.");
                curso.setMinistrante(professor);
                break;
            }
        } while (true);

        App.cursoDAO.cadastrar(curso);
    }

    public static void atualizarCurso() {
        String nome;
        int cargaHoraria, statusCode, id, professorId;
        Curso curso;
        Professor professor;

        System.out.println("--------------------------------------------");
        System.out.println("| ->               Curso                <- |");
        System.out.println("--------------------------------------------");

        do {
            System.out.print("\nDigite o ID do curso a ser atualizado: ");
            id = App.scanner.nextInt();
            App.scanner.nextLine();
            try {
                curso = App.cursoDAO.buscarPorId(id);
                break;
            } catch (NotFoundException e) {
                System.out.print("\nNão foi possível encontrar o curso com o ID digitado.");
            }
        } while (true);

        System.out.print("\nDigite o novo nome do curso (Caso não queira atualizar, deixe em branco): ");
        nome = App.scanner.nextLine();

        if (!nome.isEmpty()) {
            curso.setNome(nome);
        }

        System.out.print("\nDigite o novo status do curso [1 -> Aberto, 2 -> Fechado]: ");
        statusCode = Integer.parseInt(App.scanner.nextLine());

        if (statusCode == 1) {
            curso.setStatus("Aberto");
        } else {
            curso.setStatus("Fechado");
        }

        System.out.print("\nDigite a nova carga horária do curso (Caso não queira atualizar, deixe em branco): ");
        try {
            cargaHoraria = Integer.parseInt(App.scanner.nextLine());
            curso.setCargaHoraria(cargaHoraria);
        } catch (Exception e) {
            System.out.println("A carga horária do curso não será atualizada.");
        }

        do {
            try {
                System.out.print("\nDigite o ID do novo ministrante (Caso não queira atualizar, deixe em branco): ");
                professorId = Integer.parseInt(App.scanner.nextLine());

                professor = App.professorDAO.obterPorId(professorId);
                curso.setMinistrante(professor);
                break;
            } catch (NotFoundException e) {
                System.out.println("\nNão foi possível encontrar o professor com o ID digitado.");
            } catch (NumberFormatException e) {
                System.out.println("\nO ministrante não será atualizado.");
                break;
            }
        } while (true);

        App.cursoDAO.atualizar(id, curso);
    }

    public static void listarCursos() {
        List<Curso> cursos = App.cursoDAO.obterTodos();
        imprimirTabelaCursos(cursos);
    }

    public static void imprimirTabelaCursos(List<Curso> cursos) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "| ->                                Cursos cadastrados                                          <- |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println(
                "| ID |       NOME       |       STATUS       |       CARGA HORÁRIA       |       MINISTRANTE       |");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        for (int i = 0; i < cursos.size(); i++) {
            System.out.println("| " + cursos.get(i).getId() + " | " + cursos.get(i).getNome() + " | "
                    + cursos.get(i).getStatus() + " | "
                    + cursos.get(i).getCargaHoraria() + " | " + cursos.get(i).getMinistrante().getNome() + " | ");
        }

        System.out.println("Pressione qualquer tecla para voltar...");
        App.scanner.nextLine();
    }

    public static void gerenciarNotas() {
        Curso curso;
        do {
            try {
                System.out.print("\nPara gerenciar as notas, digite o ID do curso: ");
                int cursoId = Integer.parseInt(App.scanner.nextLine());
                curso = App.cursoDAO.buscarPorId(cursoId);

                break;
            } catch (NotFoundException e) {
                System.out.print("\nNão foi possível encontrar o curso com o ID digitado.");
            } catch (NumberFormatException e) {
                System.out.println("\nID inválido.");
            }
        } while (true);

        NotasUi.menuNotas(curso);
    }

    public static void gerarRelatorioDesempenho() {
        Curso curso;
        do {
            try {
                System.out.print("\nPara gerar o relatório, digite o ID do curso: ");
                int cursoId = Integer.parseInt(App.scanner.nextLine());
                curso = App.cursoDAO.buscarPorId(cursoId);

                break;
            } catch (NotFoundException e) {
                System.out.println("Não foi possível encontrar o curso com o ID digitado.");
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
            }
        } while (true);

        int qtdAlunos = App.matriculaDAO.contarMatriculasPorCurso(curso.getId());
        double mediaGeral = App.matriculaDAO.calcularMediaGeralPorCurso(curso.getId());
        double percentualAprovacao = App.matriculaDAO.calcularPercentualAlunosAprovadosPorCurso(curso.getId()) * 100;
        double percentualReprovacao = percentualAprovacao == 100 ? 0 : 100 - percentualAprovacao;

        System.out.println("-----------------------------------------------------------");
        System.out.println("| ->                    DESEMPENHO                     <- |");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Quantidade de alunos matriculados no curso: " + qtdAlunos);
        System.out.println("Nota média geral dos alunos: " + mediaGeral);
        System.out.println("Percentual de alunos aprovados (nota >= 7): " + percentualAprovacao + "%");
        System.out.println("Percentual de alunos reprovados (nota < 7): " + percentualReprovacao + "%");
        System.out.println("-----------------------------------------------------------");

        System.out.println("Pressione qualquer tecla para voltar...");
        App.scanner.nextLine();
    }
}