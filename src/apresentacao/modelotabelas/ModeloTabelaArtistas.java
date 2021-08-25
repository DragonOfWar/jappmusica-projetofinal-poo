package apresentacao.modelotabelas;

import apresentacao.Principal;
import dados.Artista;

public class ModeloTabelaArtistas extends ModeloTabela<Artista> {
	private static final long serialVersionUID = -3286182274568745590L;
	public static final int COL_NOME = 0;
	public static final int COL_DATA = 1;
	public static final int COL_BIOGRAFA = 2;
	public static final int COL_EDITAR = 3;
	private static final String[] COLUNAS = { "Nome", "Data Nascimento", "Biografia", "Editar" };

	public ModeloTabelaArtistas(ModeloTabelaBuscarDados<Artista> buscarDados) {
		super(buscarDados);
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int l, int c) {
		Artista artista = this.getDado(l);
		switch (c) {
		case COL_NOME:
			return artista.getNome();
		case COL_DATA:
			return artista.getDataNascimento().format(Principal.FORMATACAO_DATA_PADRAO);
		case COL_BIOGRAFA:
			return artista.getBiografia();
		case COL_EDITAR:
			return Principal.pegarUsuarioAtual().getId() == artista.getIdDono() ? "Editar" : "";
		}
		return null;
	}

	@Override
	public String getColumnName(int c) {
		return COLUNAS[c];
	}
}
