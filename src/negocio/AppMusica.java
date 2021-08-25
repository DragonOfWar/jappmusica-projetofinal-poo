package negocio;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import dados.Artista;
import dados.Musica;
import dados.Playlist;
import dados.Usuario;
import excessoes.LoginInvalidoException;
import excessoes.UsuarioExisteException;
import excessoes.UsuarioLogadoException;
import excessoes.UsuarioNaoEDonoException;
import excessoes.UsuarioNaoLogadoException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import persistencia.ArtistaDAO;
import persistencia.FavoritosDAO;
import persistencia.MusicaDAO;
import persistencia.ParticipacaoDAO;
import persistencia.PlaylistDAO;
import persistencia.PlaylistMusicaDAO;
import persistencia.UsuarioDAO;

public class AppMusica {
	private Usuario usuarioLogado;
	private ArtistaDAO artistaDAO;
	private FavoritosDAO favoritosDAO;
	private MusicaDAO musicaDAO;
	private ParticipacaoDAO participacaoDAO;
	private PlaylistDAO playlistDAO;
	private PlaylistMusicaDAO playlistMusicaDAO;
	private UsuarioDAO usuarioDAO;

	public AppMusica() throws ClassNotFoundException, SQLException {
		artistaDAO = ArtistaDAO.getInstance();
		favoritosDAO = FavoritosDAO.getInstance();
		musicaDAO = MusicaDAO.getInstance();
		participacaoDAO = ParticipacaoDAO.getInstance();
		playlistDAO = PlaylistDAO.getInstance();
		playlistMusicaDAO = PlaylistMusicaDAO.getInstance();
		usuarioDAO = UsuarioDAO.getInstance();
	}

	//////////////////// CONTROLES USUARIOS ////////////////////

	public void cadastrarUsuario(Usuario usuario)
			throws SQLException, NoSuchAlgorithmException, UsuarioExisteException {
		usuarioDAO.insert(usuario);
	}

	public Usuario getUsuarioLogado() {
		return this.usuarioLogado;
	}

	public void logar(String nome, String senha)
			throws UsuarioLogadoException, SQLException, LoginInvalidoException, NoSuchAlgorithmException {
		if (this.usuarioLogado != null)
			throw new UsuarioLogadoException();

		Usuario usuario = usuarioDAO.selectLogin(nome, senha);

		if (usuario == null)
			throw new LoginInvalidoException();

		this.usuarioLogado = usuario;
	}

	public void deslogar() {
		this.usuarioLogado = null;
	}

	//////////////////// CRUD MUSICAS ////////////////////

	public void cadastrarMusica(Musica musica, InputStream streamMusica)
			throws SQLException, UsuarioNaoLogadoException, ClassNotFoundException, IOException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		musica.setIdDono(this.usuarioLogado.getId());
		musicaDAO.insert(musica, streamMusica);
		streamMusica.close();
	}

	public List<Musica> pesquisarMusica(String nomeMusica) throws SQLException, ClassNotFoundException {
		List<Musica> musicas = musicaDAO.selectAll();
		musicas.removeIf(m -> !m.getNome().contains(nomeMusica));
		return musicas;
	}

	public List<Musica> pegarMusicasPublicadoPeloUsuario()
			throws SQLException, UsuarioNaoLogadoException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		List<Musica> musicas = musicaDAO.selectAll();
		musicas.removeIf(m -> m.getIdDono() != this.usuarioLogado.getId());

		return musicas;
	}

	public void alterarMusica(Musica musica, Musica original)
			throws UsuarioNaoEDonoException, UsuarioNaoLogadoException, SQLException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();
		if (this.usuarioLogado.getId() != original.getIdDono())
			throw new UsuarioNaoEDonoException();

		musica.setIdDono(original.getIdDono());
		musica.setId(original.getId());
		musicaDAO.update(musica);
	}

	public void removerMusica(Musica musica)
			throws UsuarioNaoLogadoException, UsuarioNaoEDonoException, SQLException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();
		if (musica.getIdDono() != this.usuarioLogado.getId())
			throw new UsuarioNaoEDonoException();

		musicaDAO.delete(musica);
	}

	//////////////////// CRUD ARTISTAS ////////////////////

	public void cadastrarArtista(Artista artista) throws UsuarioNaoLogadoException, SQLException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		artista.setIdDono(this.usuarioLogado.getId());
		artistaDAO.insert(artista);
	}

	public List<Artista> pesquisarArtista(String nomeArtista) throws SQLException {
		List<Artista> artistas = artistaDAO.selectAll();
		artistas.removeIf(a -> !a.getNome().contains(nomeArtista));
		return artistas;
	}

	public List<Artista> pegarArtistasPublicadoPeloUsuario() throws UsuarioNaoLogadoException, SQLException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		List<Artista> artistas = artistaDAO.selectAll();
		artistas.removeIf(a -> a.getIdDono() != this.usuarioLogado.getId());

		return artistas;
	}

	public void alterarArtista(Artista artista, Artista original)
			throws UsuarioNaoLogadoException, UsuarioNaoEDonoException, SQLException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();
		if (this.usuarioLogado.getId() != original.getIdDono())
			throw new UsuarioNaoEDonoException();

		artista.setId(original.getId());
		artista.setIdDono(original.getIdDono());
		artistaDAO.update(artista);
	}

	public void removerArtista(Artista artista)
			throws UsuarioNaoLogadoException, UsuarioNaoEDonoException, SQLException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();
		if (artista.getIdDono() != this.usuarioLogado.getId())
			throw new UsuarioNaoEDonoException();

		artistaDAO.delete(artista);
	}

	//////////////////// CRUD PLAYLISTS ////////////////////

	public void cadastrarPlaylist(Playlist playlist)
			throws SQLException, UsuarioNaoLogadoException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		playlist.setIdDono(this.usuarioLogado.getId());
		playlistDAO.insert(playlist);
	}

	public List<Playlist> pesquisarPlaylist(String nomePlaylist) throws ClassNotFoundException, SQLException {
		List<Playlist> playlists = playlistDAO.selectAll();
		playlists.removeIf(p -> !p.getNome().contains(nomePlaylist));
		return playlists;
	}

	public List<Playlist> pegarPlaylistsPublicadoPeloUsuario()
			throws SQLException, UsuarioNaoLogadoException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		List<Playlist> playlists = playlistDAO.selectAll();
		playlists.removeIf(p -> p.getIdDono() != this.usuarioLogado.getId());

		return playlists;
	}

	public void alterarPlaylist(Playlist playlist, Playlist original)
			throws UsuarioNaoLogadoException, UsuarioNaoEDonoException, ClassNotFoundException, SQLException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();
		if (this.usuarioLogado.getId() != original.getIdDono())
			throw new UsuarioNaoEDonoException();

		playlist.setId(original.getId());
		playlist.setIdDono(original.getIdDono());
		playlistDAO.update(playlist);
	}

	public void removerPlaylist(Playlist playlist)
			throws SQLException, UsuarioNaoLogadoException, UsuarioNaoEDonoException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();
		if (playlist.getIdDono() != this.usuarioLogado.getId())
			throw new UsuarioNaoEDonoException();

		playlistDAO.delete(playlist);
	}

	//////////////////// FAVORITOS ////////////////////

	public boolean eFavorito(Musica m) throws SQLException, UsuarioNaoLogadoException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		return favoritosDAO.selectExiste(m.getId(), this.usuarioLogado.getId());
	}

	public void favoritarMusica(Musica musica) throws UsuarioNaoLogadoException, SQLException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		favoritosDAO.insert(this.usuarioLogado.getId(), musica.getId());
	}

	public void removerMusicaDosFavoritos(Musica musica) throws SQLException, UsuarioNaoLogadoException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		favoritosDAO.delete(this.usuarioLogado.getId(), musica.getId());
	}

	public List<Musica> pegarMusicasFavoritas() throws SQLException, UsuarioNaoLogadoException, ClassNotFoundException {
		if (this.usuarioLogado == null)
			throw new UsuarioNaoLogadoException();

		return favoritosDAO.selectMusicas(this.usuarioLogado.getId());
	}

	//////////////////// UTILITARIOS ////////////////////

	public List<Musica> pegarMusicasPorArtista(Artista artista) throws SQLException, ClassNotFoundException {
		return participacaoDAO.selectMusicas(artista.getId());
	}

	public List<Playlist> pegarPlaylistsComMusica(Musica musica) throws SQLException, ClassNotFoundException {
		return playlistMusicaDAO.selectPlaylists(musica.getId());
	}

	public void tocarPrevia(Musica musica)
			throws ClassNotFoundException, SQLException, JavaLayerException, IOException {
		InputStream streamMusica = MusicaDAO.getInstance().selectArquivo(musica);
		Player player = new Player(streamMusica);
		player.play(300);
		streamMusica.close();
	}
}
