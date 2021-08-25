package apresentacao;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dados.Usuario;

public class Login extends JFrame {
	private static final long serialVersionUID = 8981086502232259185L;

	public Login() {
		setTitle("Login App de Musica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 191);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		JPanel painelLogin = new JPanel();
		painelLogin.setBorder(new EmptyBorder(8, 8, 8, 8));
		tabbedPane.addTab("Login", null, painelLogin, null);
		GridBagLayout gbl_painelLogin = new GridBagLayout();
		gbl_painelLogin.columnWidths = new int[] { 0, 0, 0 };
		gbl_painelLogin.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_painelLogin.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_painelLogin.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		painelLogin.setLayout(gbl_painelLogin);

		JLabel lblLoginUsuario = new JLabel("Usuário:");
		GridBagConstraints gbc_lblLoginUsuario = new GridBagConstraints();
		gbc_lblLoginUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblLoginUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoginUsuario.gridx = 0;
		gbc_lblLoginUsuario.gridy = 0;
		painelLogin.add(lblLoginUsuario, gbc_lblLoginUsuario);

		JTextField textFieldLoginUsuario = new JTextField();
		GridBagConstraints gbc_textFieldLoginUsuario = new GridBagConstraints();
		gbc_textFieldLoginUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldLoginUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLoginUsuario.gridx = 1;
		gbc_textFieldLoginUsuario.gridy = 0;
		painelLogin.add(textFieldLoginUsuario, gbc_textFieldLoginUsuario);
		textFieldLoginUsuario.setColumns(10);

		JLabel lblLoginSenha = new JLabel("Senha:");
		GridBagConstraints gbc_lblLoginSenha = new GridBagConstraints();
		gbc_lblLoginSenha.anchor = GridBagConstraints.EAST;
		gbc_lblLoginSenha.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoginSenha.gridx = 0;
		gbc_lblLoginSenha.gridy = 1;
		painelLogin.add(lblLoginSenha, gbc_lblLoginSenha);

		JPasswordField passwordFieldLoginSenha = new JPasswordField();
		GridBagConstraints gbc_passwordFieldLoginSenha = new GridBagConstraints();
		gbc_passwordFieldLoginSenha.insets = new Insets(0, 0, 5, 0);
		gbc_passwordFieldLoginSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldLoginSenha.gridx = 1;
		gbc_passwordFieldLoginSenha.gridy = 1;
		painelLogin.add(passwordFieldLoginSenha, gbc_passwordFieldLoginSenha);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Principal.logar(textFieldLoginUsuario.getText(), new String(passwordFieldLoginSenha.getPassword()));
				textFieldLoginUsuario.setText("");
				passwordFieldLoginSenha.setText("");
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.SOUTH;
		gbc_btnLogin.gridwidth = 2;
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 2;
		painelLogin.add(btnLogin, gbc_btnLogin);

		JPanel painelCadastro = new JPanel();
		painelCadastro.setBorder(new EmptyBorder(8, 8, 8, 8));
		tabbedPane.addTab("Cadastrar", null, painelCadastro, null);
		GridBagLayout gbl_painelCadastro = new GridBagLayout();
		gbl_painelCadastro.columnWidths = new int[] { 0, 0, 0 };
		gbl_painelCadastro.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_painelCadastro.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_painelCadastro.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		painelCadastro.setLayout(gbl_painelCadastro);

		JLabel lblCadastroUsaurio = new JLabel("Usuário:");
		GridBagConstraints gbc_lblCadastroUsaurio = new GridBagConstraints();
		gbc_lblCadastroUsaurio.anchor = GridBagConstraints.EAST;
		gbc_lblCadastroUsaurio.insets = new Insets(0, 0, 5, 5);
		gbc_lblCadastroUsaurio.gridx = 0;
		gbc_lblCadastroUsaurio.gridy = 0;
		painelCadastro.add(lblCadastroUsaurio, gbc_lblCadastroUsaurio);

		JTextField textFieldCadastroUsuario = new JTextField();
		GridBagConstraints gbc_textFieldCadastroUsuario = new GridBagConstraints();
		gbc_textFieldCadastroUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldCadastroUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCadastroUsuario.gridx = 1;
		gbc_textFieldCadastroUsuario.gridy = 0;
		painelCadastro.add(textFieldCadastroUsuario, gbc_textFieldCadastroUsuario);
		textFieldCadastroUsuario.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.anchor = GridBagConstraints.EAST;
		gbc_lblSenha.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenha.gridx = 0;
		gbc_lblSenha.gridy = 1;
		painelCadastro.add(lblSenha, gbc_lblSenha);

		JPasswordField passwordFieldCadastroSenha = new JPasswordField();
		GridBagConstraints gbc_passwordFieldCadastroSenha = new GridBagConstraints();
		gbc_passwordFieldCadastroSenha.insets = new Insets(0, 0, 5, 0);
		gbc_passwordFieldCadastroSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldCadastroSenha.gridx = 1;
		gbc_passwordFieldCadastroSenha.gridy = 1;
		painelCadastro.add(passwordFieldCadastroSenha, gbc_passwordFieldCadastroSenha);

		JLabel lblConfirmarSenha = new JLabel("Confirmar senha:");
		GridBagConstraints gbc_lblConfirmarSenha = new GridBagConstraints();
		gbc_lblConfirmarSenha.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmarSenha.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmarSenha.gridx = 0;
		gbc_lblConfirmarSenha.gridy = 2;
		painelCadastro.add(lblConfirmarSenha, gbc_lblConfirmarSenha);

		JPasswordField passwordFieldConfirmarSenha = new JPasswordField();
		GridBagConstraints gbc_passwordFieldConfirmarSenha = new GridBagConstraints();
		gbc_passwordFieldConfirmarSenha.insets = new Insets(0, 0, 5, 0);
		gbc_passwordFieldConfirmarSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldConfirmarSenha.gridx = 1;
		gbc_passwordFieldConfirmarSenha.gridy = 2;
		painelCadastro.add(passwordFieldConfirmarSenha, gbc_passwordFieldConfirmarSenha);
		passwordFieldConfirmarSenha.setColumns(10);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Usuario u = new Usuario();
				u.setNome(textFieldCadastroUsuario.getText());
				u.setSenha(new String(passwordFieldCadastroSenha.getPassword()));
				Principal.cadastrarELogar(u, new String(passwordFieldConfirmarSenha.getPassword()));
			}
		});
		GridBagConstraints gbc_btnCadastrar = new GridBagConstraints();
		gbc_btnCadastrar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCadastrar.gridwidth = 3;
		gbc_btnCadastrar.gridx = 0;
		gbc_btnCadastrar.gridy = 3;
		painelCadastro.add(btnCadastrar, gbc_btnCadastrar);
	}

}
