package apresentacao.uicomponentes;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class TabelaDados extends JScrollPane {
	private static final long serialVersionUID = -9065458249087876022L;
	private List<InteracaoTabela> ouvidores;

	public TabelaDados(TableModel modelo) {
		super();

		this.ouvidores = new ArrayList<InteracaoTabela>();

		JTable tabela = new JTable(modelo);
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evento) {
				aoClicarCelulaTabela(tabela, evento);
			}
		});

		setViewportView(tabela);
	}

	public void adicionarInteracaoTabela(InteracaoTabela interacaoTabela) {
		this.ouvidores.add(interacaoTabela);
	}

	private void aoClicarCelulaTabela(JTable tabela, MouseEvent evento) {
		int l = tabela.rowAtPoint(evento.getPoint());
		int c = tabela.columnAtPoint(evento.getPoint());
		ouvidores.forEach(ouvidor -> ouvidor.celulaClicada(l, c));
	}
}
