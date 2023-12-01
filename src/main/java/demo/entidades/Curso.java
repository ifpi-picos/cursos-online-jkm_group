package demo.entidades;

public class Curso {
    private int id_curso;
    private String nome;
    private String status;
    private int cargaHoraria;

    public Curso(String nome, String status, int cargaHoraria) {
        this.nome = nome;
        this.status = status;
        this.cargaHoraria = cargaHoraria;
    }

    public Curso(int id_curso, String nome, String status, int cargaHoraria){
        this.id_curso = id_curso;
        this.nome = nome;
        this.status = status;
        this.cargaHoraria = cargaHoraria;
    }

    public int getIdCurso(){
        return id_curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

}