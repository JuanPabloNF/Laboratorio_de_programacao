package View;

import Model.Livros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class TelaLivros extends JFrame {

    // Simula a tabela "livros" do banco de dados
    private final List<Livros> bancoSimulado = new ArrayList<>();
    private int proximoId = 1; // simula o AUTO_INCREMENT da coluna id

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JSpinner spAno;
    private JTextField txtIsbn;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnAdicionar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnListar;
    private JButton btnLimpar;
    private JButton btnVoltar;

    public TelaLivros() {
        initComponents();
        listar(); // carrega a tabela ao abrir a tela
    }

    private void initComponents() {
        setTitle("Gerenciamento de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não encerra o programa
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    // ---------- Formulário ----------
    private JPanel criarPainelFormulario() {
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

        int anoAtual = Year.now().getValue();
        spAno = new JSpinner(new SpinnerNumberModel(anoAtual, 1000, anoAtual, 1));
        // Formato "#" evita separador de milhar (ex.: 2.026)
        spAno.setEditor(new JSpinner.NumberEditor(spAno, "#"));

        // Linha 0: Título e Autor
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        painel.add(txtTitulo, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        painel.add(txtAutor, gbc);

        // Linha 1: Ano e ISBN
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

    // ---------- Tabela ----------
    private JScrollPane criarPainelTabela() {
        String[] colunas = {"ID", "Título", "Autor", "Ano", "ISBN"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(24);
        tabela.getTableHeader().setReorderingAllowed(false);

        // Ao selecionar uma linha, carrega os dados no formulário para edição
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarLinhaSelecionada();
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createTitledBorder("Livros Cadastrados")));
        return scroll;
    }

    // ---------- Botões ----------
    private JPanel criarPainelBotoes() {
        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnListar = new JButton("Listar");
        btnLimpar = new JButton("Limpar");
        btnVoltar = new JButton("Voltar");

        btnAdicionar.addActionListener(e -> adicionar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnListar.addActionListener(e -> listar());
        btnLimpar.addActionListener(e -> limparFormulario());
        btnVoltar.addActionListener(e -> dispose());

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painel.add(btnAdicionar);
        painel.add(btnAtualizar);
        painel.add(btnExcluir);
        painel.add(btnListar);
        painel.add(btnLimpar);
        painel.add(btnVoltar);
        return painel;
    }

    // ---------- Operações (CRUD) ----------

    /** INSERT */
    private void adicionar() {
        if (!validarCampos(null)) {
            return;
        }

        // SQL: INSERT INTO livros (titulo, autor, ano, isbn) VALUES (?, ?, ?, ?)
        // (o id é gerado pelo banco; aqui é simulado por proximoId)
        bancoSimulado.add(new Livros(
                proximoId++,
                txtTitulo.getText().trim(),
                txtAutor.getText().trim(),
                (Integer) spAno.getValue(),
                txtIsbn.getText().trim()));

        listar();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
    }

    /** UPDATE */
    private void atualizar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um livro na tabela para atualizar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Livros l = bancoSimulado.get(linha);

        if (!validarCampos(l.getId())) {
            return;
        }

        // SQL: UPDATE livros SET titulo = ?, autor = ?, ano = ?, isbn = ? WHERE id = ?
        l.setTitulo(txtTitulo.getText().trim());
        l.setAutor(txtAutor.getText().trim());
        l.setAno((Integer) spAno.getValue());
        l.setIsbn(txtIsbn.getText().trim());

        listar();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
    }

    /** DELETE */
    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um livro na tabela para excluir.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este livro?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            // SQL: DELETE FROM livros WHERE id = ?
            bancoSimulado.remove(linha);
            listar();
            limparFormulario();
        }
    }

    /** SELECT * - preenche a JTable com todos os livros */
    private void listar() {
        // SQL: SELECT * FROM livros
        modelo.setRowCount(0); // limpa a tabela
        for (Livros l : bancoSimulado) {
            modelo.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getAno(), l.getIsbn()});
        }
    }

    // ---------- Apoio ----------
    private void carregarLinhaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        // A linha da tabela tem a mesma posição do livro na lista
        Livros l = bancoSimulado.get(linha);
        txtTitulo.setText(l.getTitulo());
        txtAutor.setText(l.getAutor());
        spAno.setValue(l.getAno());
        txtIsbn.setText(l.getIsbn());
    }

    /**
     * Valida os campos do formulário.
     *
     * @param idIgnorado id do livro que está sendo atualizado (para não acusar
     *                   ISBN duplicado com ele mesmo); null ao adicionar.
     */
    private boolean validarCampos(Integer idIgnorado) {
        if (txtTitulo.getText().trim().isEmpty()) {
            erro("Informe o título do livro.", txtTitulo);
            return false;
        }
        if (txtAutor.getText().trim().isEmpty()) {
            erro("Informe o autor do livro.", txtAutor);
            return false;
        }

        String isbnLimpo = limparIsbn(txtIsbn.getText());
        if (!isbnLimpo.matches("\\d{10}|\\d{13}")) {
            erro("ISBN inválido. Informe 10 ou 13 dígitos (hífens são opcionais).", txtIsbn);
            return false;
        }

        for (Livros l : bancoSimulado) {
            if (l.getId().equals(idIgnorado)) {
                continue;
            }
            if (limparIsbn(l.getIsbn()).equals(isbnLimpo)) {
                erro("Já existe um livro cadastrado com este ISBN.", txtIsbn);
                return false;
            }
        }
        return true;
    }

    private String limparIsbn(String isbn) {
        return isbn.replace("-", "").replace(" ", "").trim();
    }

    private void erro(String mensagem, JComponent campo) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
    }

    private void limparFormulario() {
        txtTitulo.setText("");
        txtAutor.setText("");
        spAno.setValue(Year.now().getValue());
        txtIsbn.setText("");
        tabela.clearSelection();
        txtTitulo.requestFocus();
    }

    // Permite testar a tela isoladamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLivros().setVisible(true));
    }
}