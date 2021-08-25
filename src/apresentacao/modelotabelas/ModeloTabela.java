package apresentacao.modelotabelas;

import javax.swing.table.AbstractTableModel;

public abstract class ModeloTabela<T> extends AbstractTableModel {
	private static final long serialVersionUID = 6397449380539023008L;
	protected ModeloTabelaBuscarDados<T> buscarDados;

	public ModeloTabela(ModeloTabelaBuscarDados<T> buscarDados) {
		this.buscarDados = buscarDados;
	}

	@Override
	public int getRowCount() {
		return buscarDados.buscar().size();
	}

	public T getDado(int linha) {
		return buscarDados.buscar().get(linha);
	}
}
