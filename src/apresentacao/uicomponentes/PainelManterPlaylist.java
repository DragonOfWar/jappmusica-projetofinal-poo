package apresentacao.uicomponentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import apresentacao.Principal;
import dados.Musica;
import dados.Playlist;

public class PainelManterPlaylist extends JPanel {
	private static final long serialVersionUID = -3931027650112055736L;
	private PainelGerenciadorLista<Musica> listaMusica;
	private JTextField textFieldNome;

	public PainelManterPlaylist(Playlist playlist) {
		setPreferredSize(new Dimension(500, 250));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		add(lblNome, gbc_lblNome);

		textFieldNome = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textFieldNome, gbc_textField);
		textFieldNome.setColumns(10);
		textFieldNome.setText(playlist == null ? "" : playlist.getNome());

		JLabel lblMusicas = new JLabel("Musicas:");
		GridBagConstraints gbc_lblMusicas = new GridBagConstraints();
		gbc_lblMusicas.insets = new Insets(0, 0, 5, 5);
		gbc_lblMusicas.gridx = 0;
		gbc_lblMusicas.gridy = 1;
		add(lblMusicas, gbc_lblMusicas);

		listaMusica = new PainelGerenciadorLista<Musica>(new FuncaoPesquisa<Musica>() {
			@Override
			public Vector<Musica> pesquisarElementos(String nome) {
				return new Vector<Musica>(Principal.pesquisarMusicas(nome));
			}
		});
		GridBagConstraints gbc_painelGerenciadorLista = new GridBagConstraints();
		gbc_painelGerenciadorLista.insets = new Insets(0, 0, 5, 0);
		gbc_painelGerenciadorLista.fill = GridBagConstraints.BOTH;
		gbc_painelGerenciadorLista.gridx = 1;
		gbc_painelGerenciadorLista.gridy = 1;
		add(listaMusica, gbc_painelGerenciadorLista);

		if (playlist != null) {
			listaMusica.setLista(playlist.getMusicas());
			JButton btnApagar = new JButton("Apagar");
			btnApagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int resultado = JOptionPane.showConfirmDialog(getParent(),
							"Tem certeza que deseja apagar esse artista?", "Apagar Artista",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (resultado == JOptionPane.OK_OPTION) {
						Principal.apagarPlaylist(playlist);
						Window w = SwingUtilities.getWindowAncestor(btnApagar);

						if (w != null)
							w.dispose();
					}
				}
			});
			GridBagConstraints gbc_btnApagar = new GridBagConstraints();
			gbc_btnApagar.gridwidth = 2;
			gbc_btnApagar.insets = new Insets(0, 0, 0, 5);
			gbc_btnApagar.gridx = 0;
			gbc_btnApagar.gridy = 2;
			add(btnApagar, gbc_btnApagar);
		}
	}

	public Playlist gerarPlaylist() {
		Playlist playlist = new Playlist();
		playlist.setNome(this.textFieldNome.getText());
		for (Musica musica : this.listaMusica.getLista())
			playlist.addMusica(musica);
		return playlist;
	}
}
