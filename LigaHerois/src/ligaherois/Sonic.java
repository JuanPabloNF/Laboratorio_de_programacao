/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ligaherois;

/**
 *
 * @author Aluno
 */
public class Sonic extends Herois {

    public Sonic(String nome, int vida, int energia) {
        super(nome, vida, energia);
    }
    
    public void apresentacao(){
        System.out.println("Eu sou o Sonic!");
    }
    
    public void attack(){
        System.out.println("Spin dash!");
    }
    
    
    
}
