package apresentacao.modelotabelas;

import apresentacao.Principal;
import dados.Playlist;

public class ModeloTabelaPlaylists extends ModeloTabela<Playlist> {
	private static final long serialVersionUID = 7880799872369965758L;
	public static final int COL_NOME = 0;
	public static final int COL_QNT_MUSICAS = 1;
	public static final int COL_EDITAR = 2;
	private static final String[] COLUNAS = { "Nome", "Quantidade de Músicas", "Editar" };

	public ModeloTabelaPlaylists(ModeloTabelaBuscarDados<Playlist> buscarDados) {
		super(buscarDados);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int l, int c) {
		Playlist playlist = this.getDado(l);
		switch (c) {
		case COL_NOME:
			return playlist.getNome();
		case COL_QNT_MUSICAS:
			return playlist.getMusicas().size();
		case COL_EDITAR:
			return Principal.pegarUsuarioAtual().getId() == playlist.getIdDono() ? "Editar" : "";
		}

		return null;
	}

	@Override
	public String getColumnName(int c) {
		return COLUNAS[c];
	}
}
