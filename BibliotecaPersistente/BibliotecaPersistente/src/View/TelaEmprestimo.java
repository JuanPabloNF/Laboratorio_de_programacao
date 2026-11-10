/*
package View;

import Model.Emprestimo;
import Model.Livros;
import Model.Membros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class TelaEmprestimo {
    package View;

import Model.Emprestimo;
import Model.Livros;
import Model.Membros;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

    */
/**
     * Tela de gerenciamento de empréstimos.
     * Botões: Realizar Empréstimo (INSERT), Encerrar Empréstimo (UPDATE) e
     * Listar Empréstimos (SELECT com JOIN, filtrando ativos/finalizados).
     *
     * Usa o BancoSimulado compartilhado com as outras telas. Os comentários "SQL"
     * indicam o comando que entra no lugar de cada operação no JDBC.
     *//*

    public class TelaEmprestimos extends JFrame {

        private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        */
/** Item de combo: mostra um rótulo, mas guarda a chave (id/matrícula) internamente. *//*

        private static class ItemCombo {
            private final Object chave;
            private final String rotulo;

            ItemCombo(Object chave, String rotulo) {
                this.chave = chave;
                this.rotulo = rotulo;
            }

            Object getChave() {
                return chave;
            }

            @Override
            public String toString() {
                return rotulo;
            }
        }

        private JComboBox<ItemCombo> cbLivro;
        private JComboBox<ItemCombo> cbMembro;
        private JTextField txtDataInicio;
        private JTextField txtDataFim;
        private JComboBox<String> cbFiltro;

        private JTable tabela;
        private DefaultTableModel modelo;

        private JButton btnRealizar;
        private JButton btnEncerrar;
        private JButton btnListar;
        private JButton btnLimpar;
        private JButton btnVoltar;

        public TelaEmprestimos() {
            initComponents();
            carregarCombos();
            listar();
        }

        private void initComponents() {
            setTitle("Gerenciamento de Empréstimos");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não encerra o programa
            setSize(900, 520);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));

            add(criarPainelFormulario(), BorderLayout.NORTH);
            add(criarPainelTabela(), BorderLayout.CENTER);
            add(criarPainelBotoes(), BorderLayout.SOUTH);

            // Ao voltar para esta janela, recarrega livros/membros (podem ter mudado em outra tela)
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowActivated(WindowEvent e) {
                    carregarCombos();
                }
            });
        }

        // ---------- Formulário ----------
        private JPanel criarPainelFormulario() {
            JPanel painel = new JPanel(new GridBagLayout());
            painel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 0, 10),
                    BorderFactory.createTitledBorder("Novo Empréstimo")));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 8, 5, 8);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            cbLivro = new JComboBox<>();
            cbMembro = new JComboBox<>();

            txtDataInicio = new JTextField(hoje().toLocalDate().format(FORMATO), 10);
            txtDataInicio.setToolTipText("Formato dd/MM/aaaa");

            txtDataFim = new JTextField(10);
            txtDataFim.setToolTipText("Opcional. Usada ao encerrar; em branco = hoje (dd/MM/aaaa)");

            // Linha 0: Livro e Membro
            gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
            painel.add(new JLabel("Livro:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            painel.add(cbLivro, gbc);

            gbc.gridx = 2; gbc.weightx = 0;
            painel.add(new JLabel("Membro:"), gbc);
            gbc.gridx = 3; gbc.weightx = 1;
            painel.add(cbMembro, gbc);

            // Linha 1: Data início e Data fim
            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
            painel.add(new JLabel("Data início:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            painel.add(txtDataInicio, gbc);

            gbc.gridx = 2; gbc.weightx = 0;
            painel.add(new JLabel("Data fim (opcional):"), gbc);
            gbc.gridx = 3; gbc.weightx = 1;
            painel.add(txtDataFim, gbc);

            return painel;
        }

        // ---------- Tabela ----------
        private JPanel criarPainelTabela() {
            String[] colunas = {"ID", "Livro", "Membro", "Data início", "Data fim", "Status"};
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
            tabela.getColumnModel().getColumn(0).setMaxWidth(60);

            // Filtro: Todos / Ativos / Finalizados
            cbFiltro = new JComboBox<>(new String[]{"Todos", "Ativos", "Finalizados"});
            cbFiltro.addActionListener(e -> listar());

            JPanel barraFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
            barraFiltro.add(new JLabel("Exibir:"));
            barraFiltro.add(cbFiltro);

            JPanel painel = new JPanel(new BorderLayout());
            painel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(0, 10, 0, 10),
                    BorderFactory.createTitledBorder("Empréstimos")));
            painel.add(barraFiltro, BorderLayout.NORTH);
            painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
            return painel;
        }

        // ---------- Botões ----------
        private JPanel criarPainelBotoes() {
            btnRealizar = new JButton("Realizar Empréstimo");
            btnEncerrar = new JButton("Encerrar Empréstimo");
            btnListar = new JButton("Listar Empréstimos");
            btnLimpar = new JButton("Limpar");
            btnVoltar = new JButton("Voltar");

            btnRealizar.addActionListener(e -> realizarEmprestimo());
            btnEncerrar.addActionListener(e -> encerrarEmprestimo());
            btnListar.addActionListener(e -> listar());
            btnLimpar.addActionListener(e -> limparFormulario());
            btnVoltar.addActionListener(e -> dispose());

            JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            painel.add(btnRealizar);
            painel.add(btnEncerrar);
            painel.add(btnListar);
            painel.add(btnLimpar);
            painel.add(btnVoltar);
            return painel;
        }

        // ---------- Operações ----------

        */
/** INSERT *//*

        private void realizarEmprestimo() {
            ItemCombo livro = (ItemCombo) cbLivro.getSelectedItem();
            ItemCombo membro = (ItemCombo) cbMembro.getSelectedItem();

            if (livro == null) {
                aviso("Não há livros cadastrados. Cadastre um livro primeiro.");
                return;
            }
            if (membro == null) {
                aviso("Não há membros cadastrados. Cadastre um membro primeiro.");
                return;
            }

            Date dataInicio = lerData(txtDataInicio, "Data início");
            if (dataInicio == null) {
                return;
            }

            Integer livroId = (Integer) livro.getChave();
            if (BancoSimulado.livroEmprestado(livroId)) {
                aviso("Este livro já está emprestado e ainda não foi devolvido.");
                return;
            }

            // SQL: INSERT INTO emprestimo (livro_id, membro_id, data_inicio) VALUES (?, ?, ?)
            // (data_fim fica NULL até o empréstimo ser encerrado)
            BancoSimulado.EMPRESTIMOS.add(new Emprestimo(
                    BancoSimulado.proximoIdEmprestimo(),
                    livroId,
                    (String) membro.getChave(),
                    dataInicio,
                    null));

            listar();
            limparFormulario();
            JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso!");
        }

        */
/** UPDATE *//*

        private void encerrarEmprestimo() {
            int linha = tabela.getSelectedRow();
            if (linha < 0) {
                aviso("Selecione um empréstimo ativo na tabela.");
                return;
            }

            Integer id = (Integer) modelo.getValueAt(linha, 0);
            Emprestimo emprestimo = BancoSimulado.buscarEmprestimo(id);
            if (emprestimo == null) {
                return;
            }
            if (!emprestimo.isAtivo()) {
                aviso("Este empréstimo já foi encerrado.");
                return;
            }

            Date dataFim;
            if (txtDataFim.getText().trim().isEmpty()) {
                dataFim = hoje(); // sem data informada, usa a data de hoje
            } else {
                dataFim = lerData(txtDataFim, "Data fim");
                if (dataFim == null) {
                    return;
                }
            }

            if (dataFim.before(emprestimo.getDataInicio())) {
                erro("A data fim não pode ser anterior à data de início do empréstimo.", txtDataFim);
                return;
            }

            // SQL: UPDATE emprestimo SET data_fim = ? WHERE id = ?
            emprestimo.setDataFim(dataFim);

            listar();
            limparFormulario();
            JOptionPane.showMessageDialog(this, "Empréstimo encerrado com sucesso!");
        }

        */
/** SELECT com JOIN - preenche a JTable conforme o filtro (Todos/Ativos/Finalizados) *//*

        private void listar() {
            // SQL: SELECT e.id, l.titulo, m.nome, e.data_inicio, e.data_fim
            //      FROM emprestimo e
            //      JOIN livros  l ON l.id = e.livro_id
            //      JOIN membros m ON m.id = e.membro_id
            //      [WHERE e.data_fim IS NULL]       -- Ativos
            //      [WHERE e.data_fim IS NOT NULL]   -- Finalizados
            //      ORDER BY e.id
            String filtro = (String) cbFiltro.getSelectedItem();

            modelo.setRowCount(0); // limpa a tabela
            for (Emprestimo e : BancoSimulado.EMPRESTIMOS) {
                if ("Ativos".equals(filtro) && !e.isAtivo()) {
                    continue;
                }
                if ("Finalizados".equals(filtro) && e.isAtivo()) {
                    continue;
                }

                // O "JOIN" simulado: busca o título do livro e o nome do membro
                Livros livro = BancoSimulado.buscarLivro(e.getLivroId());
                Membros membro = BancoSimulado.buscarMembro(e.getMembroMatricula());

                modelo.addRow(new Object[]{
                        e.getId(),
                        livro != null ? livro.getTitulo() : "(livro removido)",
                        membro != null ? membro.getNome() : "(membro removido)",
                        formatar(e.getDataInicio()),
                        e.isAtivo() ? "-" : formatar(e.getDataFim()),
                        e.isAtivo() ? "Ativo" : "Finalizado"});
            }
        }

        // ---------- Apoio ----------

        */
/** Recarrega os combos de livro e membro, mantendo a seleção atual. *//*

        private void carregarCombos() {
            Object livroSelecionado = chaveSelecionada(cbLivro);
            Object membroSelecionado = chaveSelecionada(cbMembro);

            // SQL: SELECT id, titulo FROM livros
            cbLivro.removeAllItems();
            for (Livros l : BancoSimulado.LIVROS) {
                cbLivro.addItem(new ItemCombo(l.getId(), l.getId() + " - " + l.getTitulo()));
            }

            // SQL: SELECT * FROM membros
            cbMembro.removeAllItems();
            for (Membros m : BancoSimulado.MEMBROS) {
                cbMembro.addItem(new ItemCombo(m.getMatricula(), m.getNome() + " (" + m.getMatricula() + ")"));
            }

            selecionarPorChave(cbLivro, livroSelecionado);
            selecionarPorChave(cbMembro, membroSelecionado);
        }

        private Object chaveSelecionada(JComboBox<ItemCombo> combo) {
            ItemCombo item = (ItemCombo) combo.getSelectedItem();
            return item == null ? null : item.getChave();
        }

        private void selecionarPorChave(JComboBox<ItemCombo> combo, Object chave) {
            if (chave == null) {
                return;
            }
            for (int i = 0; i < combo.getItemCount(); i++) {
                if (Objects.equals(combo.getItemAt(i).getChave(), chave)) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }
        }

        */
/** Lê uma data dd/MM/aaaa de um campo; mostra erro e retorna null se inválida. *//*

        private Date lerData(JTextField campo, String nomeCampo) {
            try {
                return Date.valueOf(LocalDate.parse(campo.getText().trim(), FORMATO));
            } catch (DateTimeParseException ex) {
                erro(nomeCampo + " inválida. Use o formato dd/MM/aaaa.", campo);
                return null;
            }
        }

        private Date hoje() {
            return Date.valueOf(LocalDate.now());
        }

        private String formatar(Date data) {
            return data.toLocalDate().format(FORMATO);
        }

        private void aviso(String mensagem) {
            JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
        }

        private void erro(String mensagem, JComponent campo) {
            JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
            campo.requestFocus();
        }

        private void limparFormulario() {
            txtDataInicio.setText(hoje().toLocalDate().format(FORMATO));
            txtDataFim.setText("");
            tabela.clearSelection();
        }

        // Permite testar a tela isoladamente
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new TelaEmprestimos().setVisible(true));
        }
    }
}
*/
