package apresentacao;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
import negocio.AppMusica;

public class Principal {
	public static final String ERRO_CLASSE_NAO_ENCONTRADA = "Não foi encontrada a classe do driver do banco de dados";
	public static final String ERRO_SQL = "Erro no banco de dados";
	public static final String ERRO_USUARIO_JA_LOGADO = "Usuário já está logado";
	public static final String ERRO_USUARIO_NAO_LOGADO = "Usuário não está logado";
	public static final String ERRO_JLAYER = "Erro no tocador de musica";
	public static final String ERRO_IO = "Erro no io";
	public static final String ERRO_USUARIO_NAO_E_DONO = "Você não é dono desse recurso";
	public static final String ERRO_DATA_INVALIDA = "Você digitou a data de forma inválida. Digite a data no formato dd/mm/aaaa";
	public static final String ERRO_ARQUIVO_NULO = "Você não cadastrou o arquivo da música";
	public static final String ERRO_ALGORITMO_NAO_ENCONTRADO = "O algoritmo SHA-256 não foi encontrado";
	public static final String AVISO_LOGIN_INVALIDO = "Login inválido";
	public static final String AVISO_USUARIO_EXISTE = "Usuário de nome %s já existe";
	public static final String AVISO_CONFIRMACAO_SENHA_INVALIDO = "As duas senhas não são iguais";
	public static final DateTimeFormatter FORMATACAO_DATA_PADRAO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static AppMusica appMusica;
	private static JFrame janelaAtual;

	public static void main(String[] args) {
		try {
			appMusica = new AppMusica();
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
			System.exit(1);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
			System.exit(1);
		}

		abrirTelaLogin();
	}

	public static void abrirTelaLogin() {
		fecharJanelaAtual();
		janelaAtual = new Login();
		janelaAtual.setVisible(true);
	}

	public static void abrirTelaApp() {
		fecharJanelaAtual();
		janelaAtual = new TelaApp();
		janelaAtual.setVisible(true);
	}

	public static void fecharJanelaAtual() {
		if (janelaAtual != null) {
			janelaAtual.setVisible(false);
			janelaAtual.dispose();
			janelaAtual = null;
		}
	}

	/////////////////////// USUÁRIOS ///////////////////////

	public static Usuario pegarUsuarioAtual() {
		return appMusica.getUsuarioLogado();
	}

	public static void logar(String login, String senha) {
		try {
			appMusica.logar(login, senha);
			abrirTelaApp();
		} catch (UsuarioLogadoException e) {
			mostrarErro(ERRO_USUARIO_JA_LOGADO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (LoginInvalidoException e) {
			mostrarAviso(AVISO_LOGIN_INVALIDO);
		} catch (NoSuchAlgorithmException e) {
			mostrarErro(ERRO_ALGORITMO_NAO_ENCONTRADO);
		}
	}

	public static void deslogar() {
		appMusica.deslogar();
		abrirTelaLogin();
	}

	public static void cadastrarELogar(Usuario usuario, String confimarSenha) {
		if (!usuario.getSenha().equals(confimarSenha)) {
			mostrarAviso(AVISO_CONFIRMACAO_SENHA_INVALIDO);
			return;
		}

		try {
			appMusica.cadastrarUsuario(usuario);
			logar(usuario.getNome(), usuario.getSenha());
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (NoSuchAlgorithmException e) {
			mostrarErro(ERRO_ALGORITMO_NAO_ENCONTRADO);
		} catch (UsuarioExisteException e) {
			mostrarAviso(String.format(AVISO_USUARIO_EXISTE, e.getUsuarioNoBD().getNome()));
		}
	}

	/////////////////////// CRUD MUSICA ///////////////////////

	public static List<Musica> pegarMusicasCadastradasPeloUsuario() {
		try {
			return appMusica.pegarMusicasPublicadoPeloUsuario();
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		}
		return new ArrayList<Musica>();
	}

	public static List<Musica> pesquisarMusicas(String nome) {
		try {
			return appMusica.pesquisarMusica(nome);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
			;
		}

		return new ArrayList<Musica>();
	}

	public static void cadastrarMusica(Musica musica, InputStream streamMusica) {
		try {
			appMusica.cadastrarMusica(musica, streamMusica);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (IOException e) {
			mostrarErro(ERRO_IO);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		}
	}

	public static void atualizarMusica(Musica musica, Musica original) {
		try {
			appMusica.alterarMusica(musica, original);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (UsuarioNaoEDonoException e) {
			mostrarErro(ERRO_USUARIO_NAO_E_DONO);
		}
	}

	public static void apagarMusica(Musica musica) {
		try {
			appMusica.removerMusica(musica);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (UsuarioNaoEDonoException e) {
			mostrarErro(ERRO_USUARIO_NAO_E_DONO);
		}
	}

	public static void tocarPrevia(Musica musica) {
		try {
			appMusica.tocarPrevia(musica);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (JavaLayerException e) {
			mostrarErro(ERRO_JLAYER);
		} catch (IOException e) {
			mostrarErro(ERRO_IO);
		}
	}

	/////////////////////// CRUD ARTISTA ///////////////////////

	public static List<Artista> pegarArtistasCadastradasPeloUsuario() {
		try {
			return appMusica.pegarArtistasPublicadoPeloUsuario();
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
		return new ArrayList<Artista>();
	}

	public static List<Artista> pesquisarArtistas(String nome) {
		try {
			return appMusica.pesquisarArtista(nome);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}

		return new ArrayList<Artista>();
	}

	public static List<Musica> pegarParticipacoesArtista(Artista a) {
		try {
			return appMusica.pegarMusicasPorArtista(a);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
		return new ArrayList<Musica>();
	}

	public static void cadastrarArtista(Artista artista) {
		try {
			appMusica.cadastrarArtista(artista);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
	}

	public static void atualizarArtista(Artista artista, Artista original) {
		try {
			appMusica.alterarArtista(artista, original);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (UsuarioNaoEDonoException e) {
			mostrarErro(ERRO_USUARIO_NAO_E_DONO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
	}

	public static void apagarArtista(Artista artista) {
		try {
			appMusica.removerArtista(artista);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (UsuarioNaoEDonoException e) {
			mostrarErro(ERRO_USUARIO_NAO_E_DONO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
	}

	/////////////////////// CRUD PLAYLIST ///////////////////////

	public static List<Playlist> pegarPlaylistsPublicadoPeloUsuario() {
		try {
			return appMusica.pegarPlaylistsPublicadoPeloUsuario();
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		}
		return null;
	}

	public static List<Playlist> pesquisarPlaylists(String nome) {
		try {
			return appMusica.pesquisarPlaylist(nome);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
		return new ArrayList<Playlist>();
	}

	public static void cadastrarPlaylist(Playlist playlist) {
		try {
			appMusica.cadastrarPlaylist(playlist);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		}
	}

	public static void atualizarPlaylist(Playlist playlist, Playlist original) {
		try {
			appMusica.alterarPlaylist(playlist, original);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (UsuarioNaoEDonoException e) {
			mostrarErro(ERRO_USUARIO_NAO_E_DONO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		}
	}

	public static void apagarPlaylist(Playlist playlist) {
		try {
			appMusica.removerPlaylist(playlist);
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (UsuarioNaoEDonoException e) {
			mostrarErro(ERRO_USUARIO_NAO_E_DONO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
	}

	/////////////////////// FAVORITOS ///////////////////////

	public static List<Musica> pegarFavoritos() {
		try {
			return appMusica.pegarMusicasFavoritas();
		} catch (ClassNotFoundException e) {
			mostrarErro(ERRO_CLASSE_NAO_ENCONTRADA);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		}
		return new ArrayList<Musica>();
	}

	public static boolean eFavorito(Musica m) {
		try {
			return appMusica.eFavorito(m);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		}
		return false;
	}

	public static void favoritar(Musica m) {
		try {
			appMusica.favoritarMusica(m);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
	}

	public static void desfavoritar(Musica m) {
		try {
			appMusica.removerMusicaDosFavoritos(m);
		} catch (UsuarioNaoLogadoException e) {
			mostrarErro(ERRO_USUARIO_NAO_LOGADO);
		} catch (SQLException e) {
			mostrarErro(ERRO_SQL);
		}
	}

	/////////////////////// ERROS ///////////////////////

	public static void mostrarErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public static void mostrarAviso(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
	}
}
