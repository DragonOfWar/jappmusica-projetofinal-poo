package apresentacao.modelotabelas;

import apresentacao.Principal;
import dados.Musica;

public class ModeloTabelaMusicas extends ModeloTabela<Musica> {
	public static final int COL_TOCAR_PREVIA = 0;
	public static final int COL_NOME = 0;
	public static final int COL_AUTORES = 1;
	public static final int COL_ALBUM = 2;
	public static final int COL_DATA = 3;
	public static final int COL_LETRA = 4;
	public static final int COL_EDITAR = 5;
	public static final int COL_FAVORITO = 6;
	private static final String[] COLUNAS = { "Nome", "Autores", "Album", "Data de Lancamento", "Letra", "Editar",
			"Favorito" };

	private static final long serialVersionUID = -6765866522841523541L;

	public ModeloTabelaMusicas(ModeloTabelaBuscarDados<Musica> buscarDados) {
		super(buscarDados);
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int l, int c) {
		Musica m = this.getDado(l);
		switch (c) {
		case COL_NOME:
			return m.getNome();
		case COL_AUTORES:
			return m.autoresString();
		case COL_ALBUM:
			return m.getAlbum();
		case COL_DATA:
			return m.getDataLancamento().format(Principal.FORMATACAO_DATA_PADRAO);
		case COL_LETRA:
			return m.getLetra();
		case COL_EDITAR:
			return m.getIdDono() == Principal.pegarUsuarioAtual().getId() ? "Editar" : "";
		case COL_FAVORITO:
			return Principal.eFavorito(m) ? "❤️" : "♡";
		}
		return null;
	}

	@Override
	public String getColumnName(int c) {
		return COLUNAS[c];
	}
}
