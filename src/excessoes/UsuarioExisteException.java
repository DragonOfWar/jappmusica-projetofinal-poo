package excessoes;

import dados.Usuario;

public class UsuarioExisteException extends Exception {
	private static final long serialVersionUID = -1905880223430135449L;
	private Usuario usuarioNoBD;

	public UsuarioExisteException(Usuario usuarioNoBD) {
		this.usuarioNoBD = usuarioNoBD;
	}

	public Usuario getUsuarioNoBD() {
		return usuarioNoBD;
	}

	public void setUsuarioNoBD(Usuario usuarioNoBD) {
		this.usuarioNoBD = usuarioNoBD;
	}

}
