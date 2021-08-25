package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dados.Musica;
import dados.Usuario;

public class FavoritosDAO {
	private static final String SQL_SELECT_MUSICAS = "select id_musica from favoritos where id_usuario=?";
	private static final String SQL_SELECT_USUARIOS = "select id_usuario from favoritos where id_musica=?";
	private static final String SQL_SELECT_EXISTE = "select 1 from favoritos where id_usuario=? and id_musica=?";
	private static final String SQL_INSERT = "insert into favoritos values(?,?)";
	private static final String SQL_DELETE_MUSICA = "delete from favoritos where id_musica=?";
	private static final String SQL_DELETE_USUARIO = "delete from favoritos where id_usuario=?";
	private static final String SQL_DELETE = "delete from favoritos where id_usuario=? and id_musica=?";

	private PreparedStatement selectMusicas;
	private PreparedStatement selectUsuarios;
	private PreparedStatement selectExiste;
	private PreparedStatement insert;
	private PreparedStatement deleteMusica;
	private PreparedStatement deleteUsuario;
	private PreparedStatement delete;

	private static FavoritosDAO instance;

	private FavoritosDAO() throws SQLException, ClassNotFoundException {
		Connection conexao = Conexao.getConexao();
		this.selectMusicas = conexao.prepareStatement(SQL_SELECT_MUSICAS);
		this.selectUsuarios = conexao.prepareStatement(SQL_SELECT_USUARIOS);
		this.selectExiste = conexao.prepareStatement(SQL_SELECT_EXISTE);
		this.insert = conexao.prepareStatement(SQL_INSERT);
		this.deleteMusica = conexao.prepareStatement(SQL_DELETE_MUSICA);
		this.deleteUsuario = conexao.prepareStatement(SQL_DELETE_USUARIO);
		this.delete = conexao.prepareStatement(SQL_DELETE);
	}

	public static FavoritosDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new FavoritosDAO();
		return instance;
	}

	public List<Musica> selectMusicas(int idUsuario) throws SQLException, ClassNotFoundException {
		List<Musica> ms = new ArrayList<Musica>();
		this.selectMusicas.setInt(1, idUsuario);
		ResultSet rs = this.selectMusicas.executeQuery();
		while (rs.next())
			ms.add(MusicaDAO.getInstance().select(rs.getInt(1)));
		return ms;
	}

	public boolean selectExiste(int idMusica, int idUsuario) throws SQLException {
		this.selectExiste.setInt(1, idUsuario);
		this.selectExiste.setInt(2, idMusica);
		ResultSet rs = this.selectExiste.executeQuery();
		return rs.next();
	}

	public List<Usuario> selectUsuarios(int idMusica) throws SQLException, ClassNotFoundException {
		List<Usuario> us = new ArrayList<Usuario>();
		this.selectUsuarios.setInt(1, idMusica);
		ResultSet rs = this.selectUsuarios.executeQuery();
		while (rs.next())
			us.add(UsuarioDAO.getInstance().select(rs.getInt(1)));
		return us;
	}

	public void insert(int idUsuario, int idMusica) throws SQLException {
		this.insert.setInt(1, idMusica);
		this.insert.setInt(2, idUsuario);
		this.insert.executeUpdate();
	}

	public void deleteMusica(int idMusica) throws SQLException {
		this.deleteMusica.setInt(1, idMusica);
		this.deleteMusica.executeUpdate();
	}

	public void deleteUsuario(int idUsuario) throws SQLException {
		this.deleteUsuario.setInt(1, idUsuario);
		this.deleteUsuario.executeUpdate();
	}

	public void delete(int idUsuario, int idMusica) throws SQLException {
		this.delete.setInt(1, idUsuario);
		this.delete.setInt(2, idMusica);
		this.delete.execute();
	}
}
