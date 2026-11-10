package Model;

public class Membros {
   private String nome;
   private String matricula;
   private String contato;

    public Membros(String nome, String matricula, String contato) {
        this.nome = nome;
        this.matricula = matricula;
        this.contato = contato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

