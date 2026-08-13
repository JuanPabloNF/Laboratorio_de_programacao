/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ligaherois;

/**
 *
 * @author Aluno
 */
public abstract class Herois {
    
    private String nome;
    private int vida;
    int energia;
    
   public Herois(String nome, int vida, int energia){
        this.nome = nome;
        this.vida = vida;
        this.energia = energia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }
   
    public abstract void apresentacao();
    
    public abstract void attack();
    
    
 
  }
   

