package demo.entidades;

public class Professor {
    private int id_professor;
    private String nome;
    private String email;
    private Curso cursos;
    
    public Professor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
    
    public Professor(String nome, String email, int id_professor){
        this.id_professor = id_professor;
        this.nome = nome;
        this.email = email;
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
    public Curso getCursos() {
        return cursos;
    }
    public void setCursos(Curso cursos) {
        this.cursos = cursos;
    }

    public int getCargaHoraria() {
        return 0;
    }

    public int getID(){
        return 0;
    }
}
