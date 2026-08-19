/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_biblioteca;

/**
 *
 * @author juanp
 */
public abstract class Livro {

    private String titulo;
    private String autor;
    private int numeroPaginas;

    public Livro(String titulo, String autor, int numeroPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
     public void setAutor(String autor) {
        this.autor = autor;
    }
    
    
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    
    
    
    public abstract void descricao();
    
    
    
    
}
