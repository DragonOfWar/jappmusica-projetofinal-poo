package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.Artista;
import dados.Musica;

public class ParticipacaoDAO {
	private static final String SQL_SELECT_MUSICAS = "select id_musica from participacoes where id_artista=?";
	private static final String SQL_SELECT_ARTISTAS = "select id_artista from participacoes where id_musica=?";
	private static final String SQL_INSERT = "insert into participacoes values(?,?)";
	private static final String SQL_DELETE_MUSICA = "delete from participacoes where id_musica=?";
	private static final String SQL_DELETE_ARTISTA = "delete from participacoes where id_artista=?";

	private PreparedStatement selectMusicas;
	private PreparedStatement selectArtistas;
	private PreparedStatement insert;
	private PreparedStatement deleteMusica;
	private PreparedStatement deleteArtista;

	private static ParticipacaoDAO instance;

	private ParticipacaoDAO() throws SQLException, ClassNotFoundException {
		Connection conexao = Conexao.getConexao();
		this.selectMusicas = conexao.prepareStatement(SQL_SELECT_MUSICAS);
		this.selectArtistas = conexao.prepareStatement(SQL_SELECT_ARTISTAS);
		this.insert = conexao.prepareStatement(SQL_INSERT);
		this.deleteMusica = conexao.prepareStatement(SQL_DELETE_MUSICA);
		this.deleteArtista = conexao.prepareStatement(SQL_DELETE_ARTISTA);
	}

	public static ParticipacaoDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new ParticipacaoDAO();
		return instance;
	}

	public List<Musica> selectMusicas(int idArtista) throws SQLException, ClassNotFoundException {
		List<Musica> ms = new ArrayList<Musica>();
		this.selectMusicas.setInt(1, idArtista);
		ResultSet rs = this.selectMusicas.executeQuery();
		while (rs.next())
			ms.add(MusicaDAO.getInstance().select(rs.getInt(1)));
		return ms;
	}

	public List<Artista> selectArtistas(int idMusica) throws SQLException, ClassNotFoundException {
		List<Artista> as = new ArrayList<Artista>();
		this.selectArtistas.setInt(1, idMusica);
		ResultSet rs = this.selectArtistas.executeQuery();
		while (rs.next())
			as.add(ArtistaDAO.getInstance().select(rs.getInt(1)));
		return as;
	}

	public void insert(int idMusica, int idArtista) throws SQLException {
		this.insert.setInt(1, idMusica);
		this.insert.setInt(2, idArtista);
		this.insert.executeUpdate();
	}

	public void deleteMusica(int idMusica) throws SQLException {
		this.deleteMusica.setInt(1, idMusica);
		this.deleteMusica.executeUpdate();
	}

	public void deleteArtista(int idArtista) throws SQLException {
		this.deleteArtista.setInt(1, idArtista);
		this.deleteArtista.executeUpdate();
	}
}
