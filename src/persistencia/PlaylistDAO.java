package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.Musica;
import dados.Playlist;

public class PlaylistDAO {
	private static final String SQL_SELECT = "select * from playlists where id=?";
	private static final String SQL_SELECT_ID = "select nextval('id_playlist')";
	private static final String SQL_SELECT_ALL = "select * from playlists";
	private static final String SQL_INSERT = "insert into playlists values(?, ?, ?)";
	private static final String SQL_UPDATE = "update playlists set nome=?, id_dono=? where id=?";
	private static final String SQL_DELETE = "delete from playlists where id=?";

	private PreparedStatement select;
	private PreparedStatement selectId;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;

	private static PlaylistDAO instance;

	private PlaylistDAO() throws ClassNotFoundException, SQLException {
		Connection conexao = Conexao.getConexao();
		this.select = conexao.prepareStatement(SQL_SELECT);
		this.selectId = conexao.prepareStatement(SQL_SELECT_ID);
		this.selectAll = conexao.prepareStatement(SQL_SELECT_ALL);
		this.insert = conexao.prepareStatement(SQL_INSERT);
		this.update = conexao.prepareStatement(SQL_UPDATE);
		this.delete = conexao.prepareStatement(SQL_DELETE);
	}

	public static PlaylistDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new PlaylistDAO();
		return instance;
	}

	public Playlist select(int id) throws SQLException, ClassNotFoundException {
		this.select.setInt(1, id);
		ResultSet rs = this.select.executeQuery();
		if (rs.next())
			return deResultSet(rs);
		return null;
	}

	public int selectId() throws SQLException {
		ResultSet rs = this.selectId.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		return 0;
	}

	public List<Playlist> selectAll() throws SQLException, ClassNotFoundException {
		List<Playlist> ps = new ArrayList<Playlist>();
		ResultSet rs = this.selectAll.executeQuery();
		while (rs.next())
			ps.add(deResultSet(rs));
		return ps;
	}

	public void insert(Playlist p) throws SQLException, ClassNotFoundException {
		p.setId(this.selectId());
		this.insert.setInt(1, p.getId());
		this.insert.setString(2, p.getNome());
		this.insert.setInt(3, p.getIdDono());
		this.insert.execute();
		for (Musica m : p.getMusicas())
			PlaylistMusicaDAO.getInstance().insert(m.getId(), p.getId());
	}

	public void update(Playlist p) throws SQLException, ClassNotFoundException {
		this.update.setString(1, p.getNome());
		this.update.setInt(2, p.getIdDono());
		this.update.setInt(3, p.getId());
		PlaylistMusicaDAO.getInstance().deletePlaylists(p.getId());
		for (Musica m : p.getMusicas())
			PlaylistMusicaDAO.getInstance().insert(m.getId(), p.getId());
		this.update.execute();
	}

	public void delete(Playlist p) throws SQLException, ClassNotFoundException {
		this.delete.setInt(1, p.getId());
		PlaylistMusicaDAO.getInstance().deletePlaylists(p.getId());
		this.delete.execute();
	}

	private Playlist deResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {
		Playlist p = new Playlist();
		p.setId(rs.getInt(1));
		p.setNome(rs.getString(2));
		p.setIdDono(rs.getInt(3));
		for (Musica m : PlaylistMusicaDAO.getInstance().selectMusicas(p.getId()))
			p.addMusica(m);
		return p;
	}
}
