package apresentacao.uicomponentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public class TabelaDadosComLista<T> extends JPanel {
	private static final long serialVersionUID = -5427505728137303253L;
	private List<InteracaoTabelaComLista<T>> ouvidores;
	private JLabel lblNomeLista;
	private JList<T> jList;

	public TabelaDadosComLista(TableModel modelo) {
		this.ouvidores = new ArrayList<InteracaoTabelaComLista<T>>();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		TabelaDados tabelaDados = new TabelaDados(modelo);
		tabelaDados.adicionarInteracaoTabela(new InteracaoTabela() {
			@Override
			public void celulaClicada(int linha, int coluna) {
				chamarCelulaClicadaOuvidores(linha, coluna);
			}
		});
		GridBagConstraints gbc_tabelaDados = new GridBagConstraints();
		gbc_tabelaDados.fill = GridBagConstraints.BOTH;
		gbc_tabelaDados.gridx = 1;
		gbc_tabelaDados.gridy = 0;
		add(tabelaDados, gbc_tabelaDados);

		JPanel panelLista = new JPanel();
		panelLista.setMinimumSize(new Dimension(200, 10));
		GridBagConstraints gbc_panelLista = new GridBagConstraints();
		gbc_panelLista.insets = new Insets(0, 0, 0, 5);
		gbc_panelLista.fill = GridBagConstraints.BOTH;
		gbc_panelLista.gridx = 0;
		gbc_panelLista.gridy = 0;
		add(panelLista, gbc_panelLista);
		GridBagLayout gbl_panelLista = new GridBagLayout();
		gbl_panelLista.columnWidths = new int[] { 0, 0 };
		gbl_panelLista.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelLista.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelLista.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelLista.setLayout(gbl_panelLista);

		lblNomeLista = new JLabel("Nome Lista");
		GridBagConstraints gbc_lblNomeLista = new GridBagConstraints();
		gbc_lblNomeLista.insets = new Insets(0, 0, 5, 0);
		gbc_lblNomeLista.gridx = 0;
		gbc_lblNomeLista.gridy = 0;
		panelLista.add(lblNomeLista, gbc_lblNomeLista);

		JScrollPane scrollPaneLista = new JScrollPane();
		GridBagConstraints gbc_scrollPaneLista = new GridBagConstraints();
		gbc_scrollPaneLista.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneLista.gridx = 0;
		gbc_scrollPaneLista.gridy = 1;
		panelLista.add(scrollPaneLista, gbc_scrollPaneLista);

		jList = new JList<T>();
		scrollPaneLista.setViewportView(jList);
		jList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting())
					aoClicarElementoLista(jList.getSelectedValue());
			}
		});

	}

	public void setNomeLista(String nome) {
		this.lblNomeLista.setText(nome);
	}

	public void adicionarInteracaoTabela(InteracaoTabelaComLista<T> interacaoTabela) {
		this.ouvidores.add(interacaoTabela);
	}

	public void mostrarLista(List<T> lista) {
		this.jList.setListData(new Vector<T>(lista));
	}

	private void aoClicarElementoLista(T elemento) {
		if (elemento != null)
			this.ouvidores.forEach(ouvidor -> ouvidor.elementoListaClicada(elemento));
	}

	private void chamarCelulaClicadaOuvidores(int l, int c) {
		this.ouvidores.forEach(ouvidor -> ouvidor.celulaClicada(l, c));
	}
}
