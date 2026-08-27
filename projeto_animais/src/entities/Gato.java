package entities;

public class Gato extends Animal {

    public Gato(String nome, int idade, String cor) {
        super(nome, idade, cor);
    }

    @Override
    public void emitirSom()
    {
        System.out.println("Miau, miau!");
    }
}