/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_biblioteca;

import java.util.ArrayList;

/**
 *
 * @author juanp
 */
public class Sistema_Biblioteca {

    public static void main(String[] args) {
       
        Biblioteca biblioteca = new Biblioteca();
        
        Livro livro1 = new LivroFisico(
               "Senhor dos aneis", "J.J.K", 1280, 5.50
       );
       
        Livro livro2 = new Ebook(
               "Percy Jackson e o Ladrão de Raios", "Rick Riordan", 400, 1.50
       );
     
      biblioteca.adicionarLivro(livro1);
      biblioteca.adicionarLivro(livro2);
      biblioteca.exibirCatalogo();
      
      
    }
}
