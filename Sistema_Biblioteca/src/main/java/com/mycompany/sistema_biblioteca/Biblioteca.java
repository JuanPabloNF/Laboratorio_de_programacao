/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_biblioteca;

import java.util.ArrayList;

/**
 *
 * @author juanp
 */
public class Biblioteca {
     ArrayList<Livro> catalogo = new ArrayList<>(); 
     
    public Biblioteca() {
        this.catalogo = new ArrayList<>();
    }
    
    public void adicionarLivro(Livro livro) {
        this.catalogo.add(livro);
    }
    
    public void exibirCatalogo() {
     
        System.out.println("\n--- Catálogo da Biblioteca ---");
        for (Livro livro : catalogo) {
            System.out.println(livro);
        }
    }
}
