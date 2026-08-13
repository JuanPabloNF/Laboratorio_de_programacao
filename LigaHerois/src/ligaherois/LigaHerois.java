/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ligaherois;

/**
 *
 * @author Aluno
 */
public class LigaHerois {

    public static void main(String[] args) {
       
        Herois goku = new Goku(
          "Goku", 1000, 500
        ); 
        
        Herois sonic = new Sonic(
           "Sonic", 500, 1000
        );
        
        Herois mario = new Mario(
            "Mário", 300, 100
        );

        Herois[] herois = {goku, sonic, mario};
        
        for (Herois heroi : herois ){
            heroi.apresentacao();
            heroi.attack();
            System.out.println();
        }        
                
        
        
    }
    
}
