/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_biblioteca;

/**
 *
 * @author juanp
 */
public class Ebook extends Livro{
    private double tamanhoArquivo;

    public Ebook (String titulo, String autor, int numeroPaginas, double tamanhoArquivo) {
        super(titulo, autor, numeroPaginas);
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public double getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(double tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }
    
    @Override
    
    public void descricao(){
        System.out.println("Edição Ebook");
    }
    
    
    
}
