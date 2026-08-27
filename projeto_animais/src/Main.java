import entities.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Animal cachorro = new Cachorro(
                "Pluto",
                6,
                "Parda"
        );

        Animal gato = new Gato(
                "Tom",
                6,
                "Preta"
        );

        Animal cavalo = new Cavalo(
           "Alazao",
           8,
           "Marrom"
        );

        Veterinario veterinario = new Veterinario(
                List.of(cachorro, gato, cavalo)
        );

        for (Animal animal : veterinario.getAnimals()) {
            System.out.println(
                    "Nome: " + animal.getNome() +
                            ", Idade: " + animal.getIdade() +
                            ", Cor: " + animal.getCor()
            );
        }

        cachorro.emitirSom();
        gato.emitirSom();
        cavalo.emitirSom();

    }
}
