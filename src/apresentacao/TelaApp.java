package apresentacao;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import apresentacao.modelotabelas.ModeloTabelaArtistas;
import apresentacao.modelotabelas.ModeloTabelaBuscarDados;
import apresentacao.modelotabelas.ModeloTabelaMusicas;
import apresentacao.modelotabelas.ModeloTabelaPlaylists;
import apresentacao.uicomponentes.InteracaoTabela;
import apresentacao.uicomponentes.InteracaoTabelaComLista;
import apresentacao.uicomponentes.PainelManterArtista;
import apresentacao.uicomponentes.PainelManterMusica;
import apresentacao.uicomponentes.PainelManterPlaylist;
import apresentacao.uicomponentes.TabelaDados;
import apresentacao.uicomponentes.TabelaDadosComLista;
import dados.Artista;
import dados.Musica;
import dados.Playlist;

public class TelaApp extends JFrame {

	private static final long serialVersionUID = 1683374967623128666L;
	private ModeloTabelaMusicas minhasMusicas;
	private ModeloTabelaMusicas meusFavoritos;
	private ModeloTabelaArtistas meusArtistas;
	private ModeloTabelaPlaylists meusPlaylists;
	private ModeloTabelaArtistas pesquisaArtistas;
	private ModeloTabelaMusicas pesquisaMusicas;
	private ModeloTabelaPlaylists pesquisaPlaylists;
	private TabelaDadosComLista<Musica> tabelaMeusArtistas;
	private TabelaDadosComLista<Musica> tabelaMeusPlaylists;
	private TabelaDadosComLista<Musica> tabelaPesquisaArtistas;
	private TabelaDadosComLista<Musica> tabelaPesquisaPlaylists;
	private JPanel contentPane;
	private JTextField textFieldPesquisa;

	public TelaApp() {
		this.criarModelosTabelas();

		setTitle("App de Musica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 656, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel painelPesquisar = this.criarPainelPesquisar();
		tabbedPane.addTab("Pesquisar", null, painelPesquisar, null);

		JPanel painelMeuPerfil = this.criarPainelPerfil();
		tabbedPane.addTab("Meu Perfil", null, painelMeuPerfil, null);

		atualizarTabelas(true);
	}

	public void mostrarDialogoManterMusica(Musica original) {
		PainelManterMusica painelManterMusica = new PainelManterMusica(original);

		int resultado = JOptionPane.showConfirmDialog(getParent(), painelManterMusica, "Manter Música",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			try {
				Musica musica = painelManterMusica.gerarMusica();
				if (original == null) {
					InputStream arquivo = painelManterMusica.getArquivo();
					if (arquivo == null)
						Principal.mostrarErro(Principal.ERRO_ARQUIVO_NULO);
					else
						Principal.cadastrarMusica(musica, arquivo);
				} else {
					Principal.atualizarMusica(musica, original);
				}
			} catch (DateTimeParseException e) {
				Principal.mostrarErro(Principal.ERRO_DATA_INVALIDA);
			}
		}
	}

	public void mostrarDialogoCriarMusica() {
		mostrarDialogoManterMusica(null);
	}

	public void mostrarDialogoManterArtista(Artista original) {
		PainelManterArtista painel = new PainelManterArtista(original);

		int resultado = JOptionPane.showConfirmDialog(getParent(), painel, "Manter Artista",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			try {
				Artista artista = painel.gerarArtista();
				if (original == null)
					Principal.cadastrarArtista(artista);
				else
					Principal.atualizarArtista(artista, original);

			} catch (DateTimeParseException e) {
				Principal.mostrarErro(Principal.ERRO_DATA_INVALIDA);
			}
		}
	}

	public void mostrarDialogoCriarArtista() {
		this.mostrarDialogoManterArtista(null);
	}

	public void mostrarDialogoManterPlaylist(Playlist original) {
		PainelManterPlaylist painel = new PainelManterPlaylist(original);

		int resultado = JOptionPane.showConfirmDialog(getParent(), painel, "Manter Playlist",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (resultado == JOptionPane.OK_OPTION) {
			try {
				Playlist playlist = painel.gerarPlaylist();
				if (original == null)
					Principal.cadastrarPlaylist(playlist);
				else
					Principal.atualizarPlaylist(playlist, original);

			} catch (DateTimeParseException e) {
				Principal.mostrarErro(Principal.ERRO_DATA_INVALIDA);
			}
		}
	}

	public void mostrarDialogoCriarPlaylist() {
		this.mostrarDialogoManterPlaylist(null);
	}

	public void aoClicarCelulaTabelaMusica(ModeloTabelaMusicas modelo, int l, int c) {
		Musica original = modelo.getDado(l);
		switch (c) {
		case ModeloTabelaMusicas.COL_TOCAR_PREVIA:
			Principal.tocarPrevia(original);
			break;
		case ModeloTabelaMusicas.COL_EDITAR:
			if (original.getIdDono() != Principal.pegarUsuarioAtual().getId())
				return;

			mostrarDialogoManterMusica(original);
			break;
		case ModeloTabelaMusicas.COL_FAVORITO:
			if (Principal.eFavorito(original))
				Principal.desfavoritar(original);
			else
				Principal.favoritar(original);
			break;
		}
	}

	public void aoClicarCelulaTabelaArtista(TabelaDadosComLista<Musica> tabela, ModeloTabelaArtistas modelo, int l,
			int c) {
		Artista original = modelo.getDado(l);
		tabela.mostrarLista(Principal.pegarParticipacoesArtista(original));

		switch (c) {
		case ModeloTabelaArtistas.COL_EDITAR:
			if (original.getIdDono() != Principal.pegarUsuarioAtual().getId())
				return;

			mostrarDialogoManterArtista(original);
			atualizarTabelas(true);
			return;
		}
		atualizarTabelas(false);
	}

	public void aoClicarCelulaTabelaPlaylist(TabelaDadosComLista<Musica> tabela, ModeloTabelaPlaylists modelo, int l,
			int c) {
		Playlist original = modelo.getDado(l);
		tabela.mostrarLista(original.getMusicas());

		switch (c) {
		case ModeloTabelaPlaylists.COL_EDITAR:
			if (original.getIdDono() != Principal.pegarUsuarioAtual().getId())
				return;

			mostrarDialogoManterPlaylist(original);
			atualizarTabelas(true);
			return;
		}
		atualizarTabelas(false);
	}

	private void criarModelosTabelas() {
		this.minhasMusicas = new ModeloTabelaMusicas(new ModeloTabelaBuscarDados<Musica>() {
			@Override
			public List<Musica> buscar() {
				return Principal.pegarMusicasCadastradasPeloUsuario();
			}
		});
		this.meusArtistas = new ModeloTabelaArtistas(new ModeloTabelaBuscarDados<Artista>() {
			@Override
			public List<Artista> buscar() {
				return Principal.pegarArtistasCadastradasPeloUsuario();
			}
		});
		this.meusPlaylists = new ModeloTabelaPlaylists(new ModeloTabelaBuscarDados<Playlist>() {
			@Override
			public List<Playlist> buscar() {
				return Principal.pegarPlaylistsPublicadoPeloUsuario();
			}
		});
		this.meusFavoritos = new ModeloTabelaMusicas(new ModeloTabelaBuscarDados<Musica>() {
			@Override
			public List<Musica> buscar() {
				return Principal.pegarFavoritos();
			}
		});
		this.pesquisaArtistas = new ModeloTabelaArtistas(new ModeloTabelaBuscarDados<Artista>() {
			@Override
			public List<Artista> buscar() {
				return Principal.pesquisarArtistas(textFieldPesquisa.getText());
			}
		});
		this.pesquisaMusicas = new ModeloTabelaMusicas(new ModeloTabelaBuscarDados<Musica>() {
			@Override
			public List<Musica> buscar() {
				return Principal.pesquisarMusicas(textFieldPesquisa.getText());
			}
		});
		this.pesquisaPlaylists = new ModeloTabelaPlaylists(new ModeloTabelaBuscarDados<Playlist>() {
			@Override
			public List<Playlist> buscar() {
				return Principal.pesquisarPlaylists(textFieldPesquisa.getText());
			}
		});
	}

	private JPanel criarPainelPerfil() {
		JPanel painel = new JPanel();

		painel.setLayout(new BorderLayout(0, 0));

		JPanel painelBotoesCriacao = new JPanel();
		painel.add(painelBotoesCriacao, BorderLayout.SOUTH);

		JButton btnCriarMusica = new JButton("Criar Musica");
		btnCriarMusica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarDialogoCriarMusica();
				atualizarTabelas(true);
			}
		});
		painelBotoesCriacao.add(btnCriarMusica);

		JButton btnCriarArtista = new JButton("Criar Artista");
		btnCriarArtista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarDialogoCriarArtista();
				atualizarTabelas(true);
			}
		});
		painelBotoesCriacao.add(btnCriarArtista);

		JButton btnCriarPlaylist = new JButton("Criar Playlist");
		btnCriarPlaylist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarDialogoCriarPlaylist();
				atualizarTabelas(true);
			}
		});
		painelBotoesCriacao.add(btnCriarPlaylist);

		JPanel panel = new JPanel();
		painel.add(panel, BorderLayout.NORTH);

		JLabel lblUsuario = new JLabel("Olá " + Principal.pegarUsuarioAtual().getNome());
		panel.add(lblUsuario);

		JButton btnDeslogar = new JButton("Deslogar");
		btnDeslogar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Principal.deslogar();
			}
		});
		panel.add(btnDeslogar);

		JTabbedPane tabbedPaneMinhasPublicacoes = new JTabbedPane(JTabbedPane.TOP);
		painel.add(tabbedPaneMinhasPublicacoes, BorderLayout.CENTER);

		TabelaDados tabelaMinhasMusicas = this.criarTabelaMusicas(minhasMusicas);
		tabbedPaneMinhasPublicacoes.addTab("Minhas Musicas", null, tabelaMinhasMusicas, null);

		tabelaMeusArtistas = this.criarTabelaArtistas(meusArtistas);
		tabbedPaneMinhasPublicacoes.addTab("Meus Artistas", null, tabelaMeusArtistas, null);

		tabelaMeusPlaylists = this.criarTabelaPlaylists(meusPlaylists);
		tabbedPaneMinhasPublicacoes.addTab("Meus Playlists", null, tabelaMeusPlaylists, null);

		TabelaDados tabelaMeusFavoritos = this.criarTabelaMusicas(meusFavoritos);
		tabbedPaneMinhasPublicacoes.addTab("Meus Favoritos", null, tabelaMeusFavoritos, null);

		return painel;
	}

	private JPanel criarPainelPesquisar() {
		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout(0, 0));

		JPanel painelBarraPesquisa = new JPanel();
		painelBarraPesquisa.setBorder(new EmptyBorder(8, 8, 8, 8));
		painel.add(painelBarraPesquisa, BorderLayout.NORTH);
		GridBagLayout gbl_painelBarraPesquisa = new GridBagLayout();
		gbl_painelBarraPesquisa.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_painelBarraPesquisa.rowHeights = new int[] { 0, 0 };
		gbl_painelBarraPesquisa.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_painelBarraPesquisa.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		painelBarraPesquisa.setLayout(gbl_painelBarraPesquisa);

		JLabel lblPesquisa = new JLabel("Pesquisa:");
		GridBagConstraints gbc_lblPesquisa = new GridBagConstraints();
		gbc_lblPesquisa.anchor = GridBagConstraints.EAST;
		gbc_lblPesquisa.insets = new Insets(0, 0, 0, 5);
		gbc_lblPesquisa.gridx = 0;
		gbc_lblPesquisa.gridy = 0;
		painelBarraPesquisa.add(lblPesquisa, gbc_lblPesquisa);

		textFieldPesquisa = new JTextField();
		GridBagConstraints gbc_textFieldPesquisa = new GridBagConstraints();
		gbc_textFieldPesquisa.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldPesquisa.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPesquisa.gridx = 1;
		gbc_textFieldPesquisa.gridy = 0;
		painelBarraPesquisa.add(textFieldPesquisa, gbc_textFieldPesquisa);
		textFieldPesquisa.setColumns(10);

		JButton btnPesquisar = new JButton("Pesquisar");

		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.gridx = 2;
		gbc_btnPesquisar.gridy = 0;
		painelBarraPesquisa.add(btnPesquisar, gbc_btnPesquisar);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		painel.add(tabbedPane, BorderLayout.CENTER);

		btnPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				atualizarTabelas(true);
			}
		});

		TabelaDados tabelaMusicas = this.criarTabelaMusicas(this.pesquisaMusicas);
		tabbedPane.addTab("Músicas", null, tabelaMusicas, null);

		tabelaPesquisaArtistas = this.criarTabelaArtistas(this.pesquisaArtistas);
		tabbedPane.addTab("Artistas", null, tabelaPesquisaArtistas, null);

		tabelaPesquisaPlaylists = this.criarTabelaPlaylists(this.pesquisaPlaylists);
		tabbedPane.addTab("Playlists", null, tabelaPesquisaPlaylists, null);

		return painel;
	}

	private TabelaDados criarTabelaMusicas(ModeloTabelaMusicas modelo) {
		TabelaDados tabela = new TabelaDados(modelo);
		tabela.adicionarInteracaoTabela(new InteracaoTabela() {
			@Override
			public void celulaClicada(int linha, int coluna) {
				aoClicarCelulaTabelaMusica(modelo, linha, coluna);
				atualizarTabelas(true);
			}
		});
		return tabela;
	}

	private TabelaDadosComLista<Musica> criarTabelaArtistas(ModeloTabelaArtistas modelo) {
		TabelaDadosComLista<Musica> tabela = new TabelaDadosComLista<Musica>(modelo);
		tabela.setNomeLista("Participações");
		tabela.adicionarInteracaoTabela(new InteracaoTabelaComLista<Musica>() {
			@Override
			public void celulaClicada(int linha, int coluna) {
				aoClicarCelulaTabelaArtista(tabela, modelo, linha, coluna);
			}

			@Override
			public void elementoListaClicada(Musica elemento) {
				Principal.tocarPrevia(elemento);
			}
		});
		return tabela;
	}

	private TabelaDadosComLista<Musica> criarTabelaPlaylists(ModeloTabelaPlaylists modelo) {
		TabelaDadosComLista<Musica> tabela = new TabelaDadosComLista<Musica>(modelo);
		tabela.setNomeLista("Músicas");
		tabela.adicionarInteracaoTabela(new InteracaoTabelaComLista<Musica>() {
			@Override
			public void celulaClicada(int linha, int coluna) {
				aoClicarCelulaTabelaPlaylist(tabela, modelo, linha, coluna);
			}

			@Override
			public void elementoListaClicada(Musica elemento) {
				Principal.tocarPrevia(elemento);
			}
		});
		return tabela;
	}

	private void atualizarTabelas(boolean limparListas) {
		this.meusArtistas.fireTableStructureChanged();
		this.meusFavoritos.fireTableStructureChanged();
		this.meusPlaylists.fireTableStructureChanged();
		this.minhasMusicas.fireTableStructureChanged();
		this.pesquisaPlaylists.fireTableStructureChanged();
		this.pesquisaArtistas.fireTableStructureChanged();
		this.pesquisaMusicas.fireTableStructureChanged();
		if (limparListas) {
			this.tabelaMeusArtistas.mostrarLista(new ArrayList<Musica>());
			this.tabelaMeusPlaylists.mostrarLista(new ArrayList<Musica>());
			this.tabelaPesquisaArtistas.mostrarLista(new ArrayList<Musica>());
			this.tabelaPesquisaPlaylists.mostrarLista(new ArrayList<Musica>());
		}
	}
}
