package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.Musica;
import dados.Playlist;

public class PlaylistMusicaDAO {
	private static final String SQL_SELECT_MUSICAS = "select id_musica from playlist_musica where id_playlist=?";
	private static final String SQL_SELECT_PLAYLISTS = "select id_playlist from playlist_musica where id_musica=?";
	private static final String SQL_INSERT = "insert into playlist_musica values(?,?)";
	private static final String SQL_DELETE_MUSICA = "delete from playlist_musica where id_musica=?";
	private static final String SQL_DELETE_PLAYLIST = "delete from playlist_musica where id_playlist=?";

	private PreparedStatement selectMusicas;
	private PreparedStatement selectPlaylists;
	private PreparedStatement insert;
	private PreparedStatement deleteMusica;
	private PreparedStatement deletePlaylists;

	private static PlaylistMusicaDAO instance;

	private PlaylistMusicaDAO() throws SQLException, ClassNotFoundException {
		Connection conexao = Conexao.getConexao();
		this.selectMusicas = conexao.prepareStatement(SQL_SELECT_MUSICAS);
		this.selectPlaylists = conexao.prepareStatement(SQL_SELECT_PLAYLISTS);
		this.insert = conexao.prepareStatement(SQL_INSERT);
		this.deleteMusica = conexao.prepareStatement(SQL_DELETE_MUSICA);
		this.deletePlaylists = conexao.prepareStatement(SQL_DELETE_PLAYLIST);
	}

	public static PlaylistMusicaDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new PlaylistMusicaDAO();
		return instance;
	}

	public List<Musica> selectMusicas(int idPlaylist) throws SQLException, ClassNotFoundException {
		List<Musica> ms = new ArrayList<Musica>();
		this.selectMusicas.setInt(1, idPlaylist);
		ResultSet rs = this.selectMusicas.executeQuery();
		while (rs.next())
			ms.add(MusicaDAO.getInstance().select(rs.getInt(1)));
		return ms;
	}

	public List<Playlist> selectPlaylists(int idMusica) throws SQLException, ClassNotFoundException {
		List<Playlist> ps = new ArrayList<Playlist>();
		this.selectPlaylists.setInt(1, idMusica);
		ResultSet rs = this.selectPlaylists.executeQuery();
		while (rs.next())
			ps.add(PlaylistDAO.getInstance().select(rs.getInt(1)));
		return ps;
	}

	public void insert(int idMusica, int idPlaylist) throws SQLException {
		this.insert.setInt(1, idMusica);
		this.insert.setInt(2, idPlaylist);
		this.insert.executeUpdate();
	}

	public void deleteMusica(int idMusica) throws SQLException {
		this.deleteMusica.setInt(1, idMusica);
		this.deleteMusica.executeUpdate();
	}

	public void deletePlaylists(int idPlaylist) throws SQLException {
		this.deletePlaylists.setInt(1, idPlaylist);
		this.deletePlaylists.executeUpdate();
	}
}
