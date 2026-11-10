package Model;

import javax.xml.crypto.Data;

public class Emprestimo {
    private Integer id;
    private Integer livro_id;
    private Integer membro_id;
    private Data data_inicio;
    private Data data_final;

    public Emprestimo(Integer id, Integer livro_id, Integer membro_id, Data data_inicio, Data data_final) {
        this.id = id;
        this.livro_id = livro_id;
        this.membro_id = membro_id;
        this.data_inicio = data_inicio;
        this.data_final = data_final;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLivro_id() {
        return livro_id;
    }

    public void setLivro_id(Integer livro_id) {
        this.livro_id = livro_id;
    }

    public Integer getMembro_id() {
        return membro_id;
    }

    public void setMembro_id(Integer membro_id) {
        this.membro_id = membro_id;
    }

    public Data getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Data data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Data getData_final() {
        return data_final;
    }

    public void setData_final(Data data_final) {
        this.data_final = data_final;
    }
}
