package entities;

import java.util.ArrayList;
import java.util.List;

public class Veterinario {

    private List<Animal> animals =  new ArrayList<Animal>();

    public Veterinario(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
