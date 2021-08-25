package persistencia;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dados.Usuario;
import excessoes.UsuarioExisteException;

public class UsuarioDAO {
	private static final String SQL_SELECT_ID = "select nextval('id_usuario')";
	private static final String SQL_SELECT = "select * from usuarios where id=?";
	private static final String SQL_SELECT_NOME = "select * from usuarios where nome=?";
	private static final String SQL_SELECT_LOGIN = "select * from usuarios where nome=? and senha=?";
	private static final String SQL_INSERT = "insert into usuarios values (?, ?, ?)";

	private PreparedStatement selectId;
	private PreparedStatement select;
	private PreparedStatement selectLogin;
	private PreparedStatement selectNome;
	private PreparedStatement insert;

	private static UsuarioDAO instance;

	private UsuarioDAO() throws ClassNotFoundException, SQLException {
		Connection conexao = Conexao.getConexao();
		this.selectId = conexao.prepareStatement(SQL_SELECT_ID);
		this.select = conexao.prepareStatement(SQL_SELECT);
		this.selectLogin = conexao.prepareStatement(SQL_SELECT_LOGIN);
		this.selectNome = conexao.prepareStatement(SQL_SELECT_NOME);
		this.insert = conexao.prepareStatement(SQL_INSERT);
	}

	public static UsuarioDAO getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null)
			instance = new UsuarioDAO();
		return instance;
	}

	public int selectId() throws SQLException {
		ResultSet rs = this.selectId.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		return 0;
	}

	public Usuario select(int id) throws SQLException {
		this.select.setInt(1, id);
		ResultSet rs = this.select.executeQuery();
		if (rs.next())
			return deResultSet(rs);
		return null;
	}

	public Usuario selectLogin(String nome, String senha) throws SQLException, NoSuchAlgorithmException {
		this.selectLogin.setString(1, nome);
		this.selectLogin.setString(2, criptografarSenha(senha));
		ResultSet rs = this.selectLogin.executeQuery();
		if (rs.next())
			return deResultSet(rs);
		return null;
	}

	public Usuario selectNome(String nome) throws SQLException {
		this.selectNome.setString(1, nome);
		ResultSet rs = this.selectNome.executeQuery();
		if (rs.next())
			return deResultSet(rs);
		return null;
	}

	public void insert(Usuario usuario) throws SQLException, NoSuchAlgorithmException, UsuarioExisteException {
		Usuario usuarioNoBD = selectNome(usuario.getNome());
		if (usuarioNoBD != null)
			throw new UsuarioExisteException(usuarioNoBD);

		usuario.setId(this.selectId());
		this.insert.setInt(1, usuario.getId());
		this.insert.setString(2, usuario.getNome());
		this.insert.setString(3, criptografarSenha(usuario.getSenha()));
		this.insert.execute();
	}

	private Usuario deResultSet(ResultSet rs) throws SQLException {
		Usuario u = new Usuario();
		u.setId(rs.getInt(1));
		u.setNome(rs.getString(2));
		u.setSenha(rs.getString(3));
		return u;
	}

	private String criptografarSenha(String senha) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] senhaCriptografadaBytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
		return new String(senhaCriptografadaBytes);
	}
}
