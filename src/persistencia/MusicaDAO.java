package persistencia;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dados.Artista;
import dados.Musica;

public class MusicaDAO {
	private static final String SQL_SELECT = "select id, nome, letra, album, id_dono, data_lancamento from musicas where id=?";
	private static final String SQL_SELECT_ID = "select nextval('id_musica')";
	private static final String SQL_SELECT_ALL = "select id, nome, letra, album, id_dono, data_lancamento from musicas";
	private static final String SQL_SELECT_ARQUIVO = "select arquivo from musicas where id=?";
	private static final String SQL_INSERT = "insert into musicas values (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update musicas set nome=?, letra=?, album=?, id_dono=?, data_lancamento=? where id=?";
	private static final String SQL_DELETE = "delete from musicas where id=?";

	private PreparedStatement select;
	private PreparedStatement selectId;
	private PreparedStatement selectAll;
	private PreparedStatement selectArquivo;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;

	private static MusicaDAO instance;

	private MusicaDAO() throws ClassNotFoundException, SQLException {
		Connection conexao = Conexao.getConexao();
		select = conexao.prepareStatement(SQL_SELECT);
		selectId = conexao.prepareStatement(SQL_SELECT_ID);
		selectAll = conexao.prepareStatement(SQL_SELECT_ALL);
		selectArquivo = conexao.prepareStatement(SQL_SELECT_ARQUIVO);
		insert = conexao.prepareStatement(SQL_INSERT);
		update = conexao.prepareStatement(SQL_UPDATE);
		delete = conexao.prepareStatement(SQL_DELETE);
	}

	public static MusicaDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new MusicaDAO();
		return instance;
	}

	public Musica select(int id) throws SQLException, ClassNotFoundException {
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

	public List<Musica> selectAll() throws SQLException, ClassNotFoundException {
		List<Musica> ms = new ArrayList<Musica>();
		ResultSet rs = this.selectAll.executeQuery();
		while (rs.next())
			ms.add(deResultSet(rs));
		return ms;
	}

	public InputStream selectArquivo(Musica m) throws SQLException {
		this.selectArquivo.setInt(1, m.getId());
		ResultSet rs = this.selectArquivo.executeQuery();
		if (rs.next())
			return rs.getBinaryStream(1);
		return null;
	}

	public void insert(Musica m, InputStream streamMusica) throws SQLException, ClassNotFoundException {
		m.setId(this.selectId());
		this.insert.setInt(1, m.getId());
		this.insert.setString(2, m.getNome());
		this.insert.setString(3, m.getLetra());
		this.insert.setString(4, m.getAlbum());
		this.insert.setInt(5, m.getIdDono());
		this.insert.setObject(6, m.getDataLancamento());
		this.insert.setBinaryStream(7, streamMusica);
		this.insert.execute();
		for (Artista a : m.getArtistas())
			ParticipacaoDAO.getInstance().insert(m.getId(), a.getId());
	}

	public void update(Musica m) throws SQLException, ClassNotFoundException {
		this.update.setString(1, m.getNome());
		this.update.setString(2, m.getLetra());
		this.update.setString(3, m.getAlbum());
		this.update.setInt(4, m.getIdDono());
		this.update.setObject(5, m.getDataLancamento());
		this.update.setInt(6, m.getId());
		ParticipacaoDAO.getInstance().deleteMusica(m.getId());
		for (Artista a : m.getArtistas())
			ParticipacaoDAO.getInstance().insert(m.getId(), a.getId());
		this.update.executeUpdate();
	}

	public void delete(Musica m) throws SQLException, ClassNotFoundException {
		this.delete.setInt(1, m.getId());
		PlaylistMusicaDAO.getInstance().deleteMusica(m.getId());
		ParticipacaoDAO.getInstance().deleteMusica(m.getId());
		FavoritosDAO.getInstance().deleteMusica(m.getId());
		this.delete.execute();
	}

	private Musica deResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {
		Musica m = new Musica();
		m.setId(rs.getInt(1));
		m.setNome(rs.getString(2));
		m.setLetra(rs.getString(3));
		m.setAlbum(rs.getString(4));
		m.setIdDono(rs.getInt(5));
		m.setDataLancamento(rs.getObject(6, LocalDate.class));
		for (Artista a : ParticipacaoDAO.getInstance().selectArtistas(m.getId()))
			m.addArtista(a);
		return m;
	}
}
