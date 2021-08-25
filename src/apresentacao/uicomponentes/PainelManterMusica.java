package apresentacao.uicomponentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import apresentacao.Principal;
import dados.Artista;
import dados.Musica;

public class PainelManterMusica extends JPanel {
	private static final long serialVersionUID = -7203750983328348000L;
	private static final String DESC_SELECAO_ARQUIVO = "Arquivo de Audio";
	private static final String[] FORMATOS_SUPORTADOS = { "mp3", "wav" };

	private JTextField textFieldNome;
	private JTextField textFieldAlbum;
	private JTextField textFieldData;
	private JTextField textFieldLetra;
	private InputStream arquivo;
	private PainelGerenciadorLista<Artista> listaArtistas;

	public PainelManterMusica(Musica musica) {
		setPreferredSize(new Dimension(500, 300));
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter(DESC_SELECAO_ARQUIVO, FORMATOS_SUPORTADOS);
		fc.setFileFilter(filtro);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);

		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		this.add(lblNome, gbc_lblNome);

		textFieldNome = new JTextField();
		GridBagConstraints gbc_textFieldNome = new GridBagConstraints();
		gbc_textFieldNome.gridwidth = 2;
		gbc_textFieldNome.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNome.gridx = 1;
		gbc_textFieldNome.gridy = 0;
		this.add(textFieldNome, gbc_textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setText(musica == null ? "" : musica.getNome());

		JLabel lblArtistas = new JLabel("Artistas:");
		GridBagConstraints gbc_lblArtistas = new GridBagConstraints();
		gbc_lblArtistas.anchor = GridBagConstraints.EAST;
		gbc_lblArtistas.insets = new Insets(0, 0, 5, 5);
		gbc_lblArtistas.gridx = 0;
		gbc_lblArtistas.gridy = 1;
		this.add(lblArtistas, gbc_lblArtistas);

		listaArtistas = new PainelGerenciadorLista<Artista>(new FuncaoPesquisa<Artista>() {

			@Override
			public Vector<Artista> pesquisarElementos(String nome) {
				return new Vector<Artista>(Principal.pesquisarArtistas(nome));
			}

		});
		GridBagConstraints gbc_painelAutores = new GridBagConstraints();
		gbc_painelAutores.gridwidth = 2;
		gbc_painelAutores.insets = new Insets(0, 0, 5, 5);
		gbc_painelAutores.fill = GridBagConstraints.BOTH;
		gbc_painelAutores.gridx = 1;
		gbc_painelAutores.gridy = 1;
		this.add(listaArtistas, gbc_painelAutores);

		JLabel lblAlbum = new JLabel("Album:");
		GridBagConstraints gbc_lblAlbum = new GridBagConstraints();
		gbc_lblAlbum.anchor = GridBagConstraints.EAST;
		gbc_lblAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlbum.gridx = 0;
		gbc_lblAlbum.gridy = 2;
		this.add(lblAlbum, gbc_lblAlbum);

		textFieldAlbum = new JTextField();
		GridBagConstraints gbc_textFieldAlbum = new GridBagConstraints();
		gbc_textFieldAlbum.gridwidth = 2;
		gbc_textFieldAlbum.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAlbum.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAlbum.gridx = 1;
		gbc_textFieldAlbum.gridy = 2;
		this.add(textFieldAlbum, gbc_textFieldAlbum);
		textFieldAlbum.setColumns(10);
		textFieldAlbum.setText(musica == null ? "" : musica.getAlbum());

		JLabel lblData = new JLabel("Data de Lançamento:");
		GridBagConstraints gbc_lblData = new GridBagConstraints();
		gbc_lblData.anchor = GridBagConstraints.EAST;
		gbc_lblData.insets = new Insets(0, 0, 5, 5);
		gbc_lblData.gridx = 0;
		gbc_lblData.gridy = 3;
		this.add(lblData, gbc_lblData);

		textFieldData = new JTextField();
		GridBagConstraints gbc_textFieldData = new GridBagConstraints();
		gbc_textFieldData.gridwidth = 2;
		gbc_textFieldData.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldData.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldData.gridx = 1;
		gbc_textFieldData.gridy = 3;
		this.add(textFieldData, gbc_textFieldData);
		textFieldData.setColumns(10);
		textFieldData
				.setText(musica == null ? "" : musica.getDataLancamento().format(Principal.FORMATACAO_DATA_PADRAO));

		JLabel lblLetra = new JLabel("Letra:");
		GridBagConstraints gbc_lblLetra = new GridBagConstraints();
		gbc_lblLetra.anchor = GridBagConstraints.EAST;
		gbc_lblLetra.insets = new Insets(0, 0, 5, 5);
		gbc_lblLetra.gridx = 0;
		gbc_lblLetra.gridy = 4;
		this.add(lblLetra, gbc_lblLetra);

		textFieldLetra = new JTextField();
		GridBagConstraints gbc_textFieldLetra = new GridBagConstraints();
		gbc_textFieldLetra.gridwidth = 2;
		gbc_textFieldLetra.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLetra.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLetra.gridx = 1;
		gbc_textFieldLetra.gridy = 4;
		this.add(textFieldLetra, gbc_textFieldLetra);
		textFieldLetra.setColumns(10);
		textFieldLetra.setText(musica == null ? "" : musica.getLetra());

		if (musica == null) {
			JLabel lblArquivo = new JLabel("Arquivo:");
			GridBagConstraints gbc_lblArquivo = new GridBagConstraints();
			gbc_lblArquivo.anchor = GridBagConstraints.EAST;
			gbc_lblArquivo.insets = new Insets(0, 0, 0, 5);
			gbc_lblArquivo.gridx = 0;
			gbc_lblArquivo.gridy = 5;
			this.add(lblArquivo, gbc_lblArquivo);

			JButton btnInserirArquivo = new JButton("Inserir Arquivo");

			GridBagConstraints gbc_btnInserirArquivo = new GridBagConstraints();
			gbc_btnInserirArquivo.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnInserirArquivo.insets = new Insets(0, 0, 0, 5);
			gbc_btnInserirArquivo.gridx = 1;
			gbc_btnInserirArquivo.gridy = 5;
			this.add(btnInserirArquivo, gbc_btnInserirArquivo);

			JLabel lblNomeArquivo = new JLabel("Nome arquivo");
			GridBagConstraints gbc_lblNomeArquivo = new GridBagConstraints();
			gbc_lblNomeArquivo.anchor = GridBagConstraints.WEST;
			gbc_lblNomeArquivo.gridx = 2;
			gbc_lblNomeArquivo.gridy = 5;
			this.add(lblNomeArquivo, gbc_lblNomeArquivo);

			btnInserirArquivo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
						try {
							arquivo = new FileInputStream(fc.getSelectedFile());
							lblNomeArquivo.setText(fc.getSelectedFile().getName());
						} catch (FileNotFoundException e) {
							Principal.mostrarErro("Não foi possível achar este arquivo");
						}
					}
				}
			});
		} else {
			listaArtistas.setLista(musica.getArtistas());

			JButton btnApagarMusica = new JButton("Apagar");
			btnApagarMusica.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int resultado = JOptionPane.showConfirmDialog(getParent(),
							"Tem certeza que deseja apagar essa música?", "Apagar Musica", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (resultado == JOptionPane.OK_OPTION) {
						Principal.apagarMusica(musica);
						Window w = SwingUtilities.getWindowAncestor(btnApagarMusica);

						if (w != null)
							w.dispose();
					}
				}
			});
			GridBagConstraints gbc_btnApagarMusica = new GridBagConstraints();
			gbc_btnApagarMusica.gridwidth = 3;
			gbc_btnApagarMusica.insets = new Insets(0, 0, 5, 5);
			gbc_btnApagarMusica.gridx = 0;
			gbc_btnApagarMusica.gridy = 5;
			add(btnApagarMusica, gbc_btnApagarMusica);
		}
	}

	public Musica gerarMusica() {
		Musica m = new Musica();
		m.setNome(this.textFieldNome.getText());
		m.setAlbum(this.textFieldAlbum.getText());
		m.setLetra(this.textFieldLetra.getText());
		m.setDataLancamento(LocalDate.parse(this.textFieldData.getText(), Principal.FORMATACAO_DATA_PADRAO));
		for (Artista artista : this.listaArtistas.getLista())
			m.addArtista(artista);
		return m;
	}

	public InputStream getArquivo() {
		return this.arquivo;
	}
}
