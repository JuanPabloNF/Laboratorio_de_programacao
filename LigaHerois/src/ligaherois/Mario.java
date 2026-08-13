/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ligaherois;

/**
 *
 * @author Aluno
 */
public class Mario extends Herois{

    public Mario(String nome, int vida, int energia) {
        super(nome, vida, energia);
    }
    
    @Override
    public void apresentacao(){
        System.out.println("It's me, Mario!");
    }
    
    @Override
    public void attack(){
        System.out.println("Bolinha de fogo!");
    }
    
    
    
    
       
    
    
    
}
