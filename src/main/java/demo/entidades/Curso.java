package demo.entidades;

public class Curso {
    private int id;
    private String nome;
    private String status;
    private int cargaHoraria;
    private Professor ministrante;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Professor getMinistrante() {
        return ministrante;
    }

    public void setMinistrante(Professor ministrante) {
        this.ministrante = ministrante;
    }

}