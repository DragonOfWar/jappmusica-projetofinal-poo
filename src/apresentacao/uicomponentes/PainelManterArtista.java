package apresentacao.uicomponentes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import apresentacao.Principal;
import dados.Artista;

public class PainelManterArtista extends JPanel {
	private static final long serialVersionUID = -5637636118613446667L;
	private JTextField textFieldNome;
	private JTextField textFieldDataNascimento;
	private JTextField textFieldBiografia;

	public PainelManterArtista(Artista artista) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { -38, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		add(lblNome, gbc_lblNome);

		textFieldNome = new JTextField();
		GridBagConstraints gbc_textFieldNome = new GridBagConstraints();
		gbc_textFieldNome.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNome.gridx = 1;
		gbc_textFieldNome.gridy = 0;
		add(textFieldNome, gbc_textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setText(artista == null ? "" : artista.getNome());

		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento:");
		GridBagConstraints gbc_lblDataDeNascimento = new GridBagConstraints();
		gbc_lblDataDeNascimento.anchor = GridBagConstraints.EAST;
		gbc_lblDataDeNascimento.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataDeNascimento.gridx = 0;
		gbc_lblDataDeNascimento.gridy = 1;
		add(lblDataDeNascimento, gbc_lblDataDeNascimento);

		textFieldDataNascimento = new JTextField();
		GridBagConstraints gbc_textFieldDataNascimento = new GridBagConstraints();
		gbc_textFieldDataNascimento.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDataNascimento.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDataNascimento.gridx = 1;
		gbc_textFieldDataNascimento.gridy = 1;
		add(textFieldDataNascimento, gbc_textFieldDataNascimento);
		textFieldDataNascimento.setColumns(10);
		textFieldDataNascimento
				.setText(artista == null ? "" : artista.getDataNascimento().format(Principal.FORMATACAO_DATA_PADRAO));

		JLabel lblBiografia = new JLabel("Biografia:");
		GridBagConstraints gbc_lblBiografia = new GridBagConstraints();
		gbc_lblBiografia.anchor = GridBagConstraints.EAST;
		gbc_lblBiografia.insets = new Insets(0, 0, 5, 5);
		gbc_lblBiografia.gridx = 0;
		gbc_lblBiografia.gridy = 2;
		add(lblBiografia, gbc_lblBiografia);

		textFieldBiografia = new JTextField();
		GridBagConstraints gbc_textFieldBiografia = new GridBagConstraints();
		gbc_textFieldBiografia.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldBiografia.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldBiografia.gridx = 1;
		gbc_textFieldBiografia.gridy = 2;
		add(textFieldBiografia, gbc_textFieldBiografia);
		textFieldBiografia.setColumns(10);
		textFieldBiografia.setText(artista == null ? "" : artista.getBiografia());

		if (artista != null) {
			JButton btnApagar = new JButton("Apagar");
			btnApagar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int resultado = JOptionPane.showConfirmDialog(getParent(),
							"Tem certeza que deseja apagar esse artista?", "Apagar Artista",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (resultado == JOptionPane.OK_OPTION) {
						Principal.apagarArtista(artista);
						Window w = SwingUtilities.getWindowAncestor(btnApagar);

						if (w != null)
							w.dispose();
					}
				}
			});
			GridBagConstraints gbc_btnApagar = new GridBagConstraints();
			gbc_btnApagar.anchor = GridBagConstraints.SOUTH;
			gbc_btnApagar.gridwidth = 2;
			gbc_btnApagar.insets = new Insets(0, 0, 0, 5);
			gbc_btnApagar.gridx = 0;
			gbc_btnApagar.gridy = 3;
			add(btnApagar, gbc_btnApagar);
		}
	}

	public Artista gerarArtista() {
		Artista artista = new Artista();
		artista.setNome(textFieldNome.getText());
		artista.setDataNascimento(LocalDate.parse(textFieldDataNascimento.getText(), Principal.FORMATACAO_DATA_PADRAO));
		artista.setBiografia(textFieldBiografia.getText());
		return artista;
	}
}
