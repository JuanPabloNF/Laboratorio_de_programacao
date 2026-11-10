package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Year;

public class TelaLivros extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JSpinner spAno;
    private JTextField txtIsbn;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnAdicionar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnVoltar;

    public TelaLivros() {
        initComponent();
    }

    public void initComponent() {
        setTitle("Gerenciamento de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não encerra o programa
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(PainelFormulario(), BorderLayout.NORTH);
    }


    private JPanel PainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 0, 10),
                BorderFactory.createTitledBorder("Dados do Livro")));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtIsbn = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        painel.add(txtTitulo, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        painel.add(txtAutor, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        painel.add(spAno, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        painel.add(txtIsbn, gbc);


        return painel;

    }

}
