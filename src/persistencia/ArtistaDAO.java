package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dados.Artista;

public class ArtistaDAO {
	private static final String SQL_SELECT_ID = "select nextval('id_artista')";
	private static final String SQL_SELECT = "select * from artistas where id=?";
	private static final String SQL_SELECT_ALL = "select * from artistas";
	private static final String SQL_INSERT = "insert into artistas values (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update artistas set nome=?, data_nascimento=?, biografia=? where id=?";
	private static final String SQL_DELETE = "delete from artistas where id=?";

	private PreparedStatement selectId;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement update;
	private PreparedStatement delete;

	private static ArtistaDAO instance;

	private ArtistaDAO() throws SQLException, ClassNotFoundException {
		Connection conexao = Conexao.getConexao();
		this.selectId = conexao.prepareStatement(SQL_SELECT_ID);
		this.select = conexao.prepareStatement(SQL_SELECT);
		this.selectAll = conexao.prepareStatement(SQL_SELECT_ALL);
		this.insert = conexao.prepareStatement(SQL_INSERT);
		this.update = conexao.prepareStatement(SQL_UPDATE);
		this.delete = conexao.prepareStatement(SQL_DELETE);
	}

	public static ArtistaDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new ArtistaDAO();
		return instance;
	}

	public int selectId() throws SQLException {
		ResultSet rs = this.selectId.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		return 0;
	}

	public Artista select(int id) throws SQLException {
		this.select.setInt(1, id);
		ResultSet rs = this.select.executeQuery();
		if (rs.next())
			return deResultSet(rs);
		return null;
	}

	public List<Artista> selectAll() throws SQLException {
		List<Artista> as = new ArrayList<Artista>();
		ResultSet rs = this.selectAll.executeQuery();
		while (rs.next())
			as.add(deResultSet(rs));
		return as;
	}

	public void insert(Artista a) throws SQLException {
		a.setId(this.selectId());
		this.insert.setInt(1, a.getId());
		this.insert.setString(2, a.getNome());
		this.insert.setObject(3, a.getDataNascimento());
		this.insert.setString(4, a.getBiografia());
		this.insert.setInt(5, a.getIdDono());
		this.insert.execute();
	}

	public void update(Artista a) throws SQLException {
		this.update.setString(1, a.getNome());
		this.update.setObject(2, a.getDataNascimento());
		this.update.setString(3, a.getBiografia());
		this.update.setInt(4, a.getId());
		this.update.executeUpdate();
	}

	public void delete(Artista a) throws SQLException, ClassNotFoundException {
		this.delete.setInt(1, a.getId());
		ParticipacaoDAO.getInstance().deleteArtista(a.getId());
		this.delete.execute();
	}

	private Artista deResultSet(ResultSet rs) throws SQLException {
		Artista a = new Artista();
		a.setId(rs.getInt(1));
		a.setNome(rs.getString(2));
		a.setDataNascimento(rs.getObject(3, LocalDate.class));
		a.setBiografia(rs.getString(4));
		a.setIdDono(rs.getInt(5));
		return a;
	}
}
