import View.TelaLivros;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private JButton btnLivros;
    private JButton btnMembros;
    private JButton btnEmprestimos;

    public TelaPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Biblioteca - Tela Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        btnLivros = new JButton("Gerenciamento de Livros");
        btnMembros = new JButton("Gerenciamento de Membros");
        btnEmprestimos = new JButton("Gerenciamento de Empréstimos");

        btnLivros.addActionListener(e -> {
            new TelaLivros().setVisible(true);
            dispose();
        });
        /*btnMembros.addActionListener(e -> {
            new TelaMembros().setVisible(true);
            dispose();
        });*/
        /*btnEmprestimos.addActionListener(e -> {
            new TelaEmprestimos().setVisible(true);
            dispose();
        });*/

        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        painel.add(btnLivros);
        painel.add(btnMembros);
        painel.add(btnEmprestimos);

        add(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
