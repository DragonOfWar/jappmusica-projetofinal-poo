package apresentacao.uicomponentes;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PainelPopupPesquisar<T> extends JPanel {
	private static final long serialVersionUID = 6193101318240768083L;
	private JList<T> jList;

	public PainelPopupPesquisar(FuncaoPesquisa<T> func) {
		setLayout(new BorderLayout(0, 0));

		JPanel panelSuperior = new JPanel();
		add(panelSuperior, BorderLayout.NORTH);
		GridBagLayout gbl_panelSuperior = new GridBagLayout();
		gbl_panelSuperior.columnWidths = new int[] { 72, 76, 114, 0 };
		gbl_panelSuperior.rowHeights = new int[] { 25, 0 };
		gbl_panelSuperior.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelSuperior.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelSuperior.setLayout(gbl_panelSuperior);

		JLabel lblPesquisar = new JLabel("Pesquisar:");
		GridBagConstraints gbc_lblPesquisar = new GridBagConstraints();
		gbc_lblPesquisar.anchor = GridBagConstraints.EAST;
		gbc_lblPesquisar.insets = new Insets(0, 0, 0, 5);
		gbc_lblPesquisar.gridx = 0;
		gbc_lblPesquisar.gridy = 0;
		panelSuperior.add(lblPesquisar, gbc_lblPesquisar);

		JTextField textFieldPesquisa = new JTextField();
		GridBagConstraints gbc_textFieldPesquisa = new GridBagConstraints();
		gbc_textFieldPesquisa.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPesquisa.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldPesquisa.gridx = 1;
		gbc_textFieldPesquisa.gridy = 0;
		panelSuperior.add(textFieldPesquisa, gbc_textFieldPesquisa);
		textFieldPesquisa.setColumns(10);

		JButton btnPesquisar = new JButton("Pesquisar");
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPesquisar.anchor = GridBagConstraints.NORTH;
		gbc_btnPesquisar.gridx = 2;
		gbc_btnPesquisar.gridy = 0;
		panelSuperior.add(btnPesquisar, gbc_btnPesquisar);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		jList = new JList<T>();
		scrollPane.setViewportView(jList);
		btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jList.setListData(func.pesquisarElementos(textFieldPesquisa.getText()));
			}
		});

	}

	public T pegarElementoSelecionado() {
		return this.jList.getSelectedValue();
	}
}
