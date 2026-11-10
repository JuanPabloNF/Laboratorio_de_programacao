package View;
import Model.Membros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaMembros extends JFrame {

    private final List<Membros> bancoSimulado = new ArrayList<>();

    private JTextField txtNome;
    private JTextField txtMatricula;
    private JTextField txtContato;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnCadastrar;
    private JButton btnEditar;
    private JButton btnRemover;
    private JButton btnListar;
    private JButton btnLimpar;
    private JButton btnVoltar;

    public TelaMembros() {
        initComponents();
        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private void initComponents() {
        setTitle("Gerenciamento de Membros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não encerra o programa
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaMembros().setVisible(true));
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 0, 10),
                BorderFactory.createTitledBorder("Dados do Membro")));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome = new JTextField(20);
        txtMatricula = new JTextField(20);
        txtContato = new JTextField(20);

        // Linha 0: Nome e Matrícula
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        painel.add(txtNome, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        painel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        painel.add(txtMatricula, gbc);

        // Linha 1: Contato
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painel.add(new JLabel("Contato:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1;
        painel.add(txtContato, gbc);

        return painel;
    }

    private JScrollPane criarPainelTabela() {
        String[] colunas = {"Nome", "Matrícula", "Contato"};
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
                BorderFactory.createTitledBorder("Membros Cadastrados")));
        return scroll;
    }

    private JPanel criarPainelBotoes() {
        btnCadastrar = new JButton("Cadastrar");
        btnEditar = new JButton("Editar");
        btnRemover = new JButton("Remover");
        btnListar = new JButton("Listar");
        btnLimpar = new JButton("Limpar");
        btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> cadastrar());
        btnEditar.addActionListener(e -> editar());
        btnRemover.addActionListener(e -> remover());
        btnListar.addActionListener(e -> listar());
        btnLimpar.addActionListener(e -> limparFormulario());
        btnVoltar.addActionListener(e -> dispose());

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painel.add(btnCadastrar);
        painel.add(btnEditar);
        painel.add(btnRemover);
        painel.add(btnListar);
        painel.add(btnLimpar);
        painel.add(btnVoltar);
        return painel;
    }

    private void cadastrar() {
        if (!validarCampos(true)) {
            return;
        }

        // SQL: INSERT INTO membros (nome, matricula, contato) VALUES (?, ?, ?)
        bancoSimulado.add(new Membros(
                txtNome.getText().trim(),
                txtMatricula.getText().trim(),
                txtContato.getText().trim()));

        listar();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Membro cadastrado com sucesso!");
    }

    /** UPDATE */
    private void editar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um membro na tabela para editar.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarCampos(false)) {
            return;
        }

        // SQL: UPDATE membros SET nome = ?, contato = ? WHERE matricula = ?
        Membros m = bancoSimulado.get(linha);
        m.setNome(txtNome.getText().trim());
        m.setContato(txtContato.getText().trim());

        listar();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Membro atualizado com sucesso!");
    }

    /** DELETE */
    private void remover() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um membro na tabela para remover.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente remover este membro?",
                "Confirmar remoção", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            // SQL: DELETE FROM membros WHERE matricula = ?
            bancoSimulado.remove(linha);
            listar();
            limparFormulario();
        }
    }

    /** SELECT * - preenche a JTable com todos os membros */
    private void listar() {
        // SQL: SELECT * FROM membros
        modelo.setRowCount(0); // limpa a tabela
        for (Membros m : bancoSimulado) {
            modelo.addRow(new Object[]{m.getNome(), m.getMatricula(), m.getContato()});
        }
    }

    // ---------- Apoio ----------
    private void carregarLinhaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }
        txtNome.setText((String) modelo.getValueAt(linha, 0));
        txtMatricula.setText((String) modelo.getValueAt(linha, 1));
        txtContato.setText((String) modelo.getValueAt(linha, 2));

        // A matrícula é a chave (WHERE do UPDATE/DELETE), então não pode ser alterada
        txtMatricula.setEditable(false);
    }

    /**
     * Valida os campos do formulário.
     *
     * @param novo true ao cadastrar (checa matrícula duplicada); false ao editar.
     */
    private boolean validarCampos(boolean novo) {
        if (txtNome.getText().trim().isEmpty()) {
            erro("Informe o nome do membro.", txtNome);
            return false;
        }

        String matricula = txtMatricula.getText().trim();
        if (matricula.isEmpty()) {
            erro("Informe a matrícula do membro.", txtMatricula);
            return false;
        }

        if (txtContato.getText().trim().isEmpty()) {
            erro("Informe o contato do membro (telefone ou e-mail).", txtContato);
            return false;
        }

        if (novo) {
            for (Membros m : bancoSimulado) {
                if (m.getMatricula().equalsIgnoreCase(matricula)) {
                    erro("Já existe um membro cadastrado com esta matrícula.", txtMatricula);
                    return false;
                }
            }
        }
        return true;
    }

    private void erro(String mensagem, JComponent campo) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtMatricula.setText("");
        txtContato.setText("");
        txtMatricula.setEditable(true);
        tabela.clearSelection();
        txtNome.requestFocus();
    }
}
