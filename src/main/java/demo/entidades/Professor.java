package demo.entidades;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private int id_professor;
    private String nome;
    private String email;
    private List<Curso> cursos;

    
    public Professor(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.cursos = new ArrayList<>();
    }

    public Professor(int id_professor, String nome, String email){
        this.id_professor = id_professor;
        this.nome = nome;
        this.email = email;
        this.cursos = new ArrayList<>();
    }
    
    public int getIdProfessor(){
        return id_professor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void adicionarCurso(Curso curso) {
        this.cursos.add(curso);
    }

    public void removerCurso(Curso curso) {
        this.cursos.remove(curso);
    }
}
