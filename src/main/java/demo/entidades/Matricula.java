package demo.entidades;

public class Matricula {
    private int id;
    private Curso curso;
    private Aluno aluno;
    private String status;
    private Double nota;

    public Double getNota() {
        return this.nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
