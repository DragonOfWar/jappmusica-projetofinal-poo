package apresentacao.uicomponentes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class PainelGerenciadorLista<T> extends JPanel {
	private static final long serialVersionUID = 2250054022029788381L;
	private List<T> lista;
	private JList<T> jList;
	private FuncaoPesquisa<T> funcaoPesquisa;

	public PainelGerenciadorLista(FuncaoPesquisa<T> funcaoPesquisa) {
		this.funcaoPesquisa = funcaoPesquisa;

		GridBagLayout gbl_painelAutores = new GridBagLayout();
		gbl_painelAutores.columnWidths = new int[] { 98, 0, 0 };
		gbl_painelAutores.rowHeights = new int[] { 0, 0, 0 };
		gbl_painelAutores.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_painelAutores.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_painelAutores);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		this.add(scrollPane, gbc_scrollPane);

		jList = new JList<T>();
		GridBagConstraints gbc_jListArtistas = new GridBagConstraints();
		gbc_jListArtistas.gridwidth = 3;
		gbc_jListArtistas.insets = new Insets(0, 0, 5, 0);
		gbc_jListArtistas.fill = GridBagConstraints.BOTH;
		gbc_jListArtistas.gridx = 0;
		gbc_jListArtistas.gridy = 1;
		scrollPane.setViewportView(jList);

		JButton btnAdicionarArtista = new JButton("Adicionar");
		btnAdicionarArtista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				T a = mostrarPopupPesquisar();
				if (a != null && !lista.contains(a)) {
					lista.add(a);
					setLista(lista);
				}
			}
		});
		GridBagConstraints gbc_btnAdicionarArtista = new GridBagConstraints();
		gbc_btnAdicionarArtista.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdicionarArtista.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdicionarArtista.gridx = 0;
		gbc_btnAdicionarArtista.gridy = 1;
		this.add(btnAdicionarArtista, gbc_btnAdicionarArtista);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				T a = jList.getSelectedValue();
				if (a != null) {
					lista.remove(a);
					setLista(lista);
				}
			}
		});
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemover.gridx = 1;
		gbc_btnRemover.gridy = 1;
		this.add(btnRemover, gbc_btnRemover);

		this.setLista(new ArrayList<T>());
	}

	private T mostrarPopupPesquisar() {
		PainelPopupPesquisar<T> painel = new PainelPopupPesquisar<T>(this.funcaoPesquisa);

		int resultado = JOptionPane.showConfirmDialog(getRootPane(), painel, "Pesquisar artista",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION)
			return painel.pegarElementoSelecionado();

		return null;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
		this.jList.setListData(new Vector<T>(lista));
		this.jList.setVisibleRowCount(this.lista.size());
	}

	public List<T> getLista() {
		return this.lista;
	}
}
