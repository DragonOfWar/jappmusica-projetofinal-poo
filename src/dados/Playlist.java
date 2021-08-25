package dados;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private int id;
	private String nome;
	private int idDono;
	private List<Musica> musicas;

	public Playlist() {
		this.musicas = new ArrayList<Musica>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdDono() {
		return idDono;
	}

	public void setIdDono(int idDono) {
		this.idDono = idDono;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Musica> getMusicas() {
		return musicas;
	}

	public void addMusica(Musica musica) {
		if (!this.musicas.contains(musica))
			this.musicas.add(musica);
	}

	public boolean removerMusica(Musica musica) {
		return this.musicas.remove(musica);
	}

	@Override
	public String toString() {
		return this.nome + " (Criado por: " + this.idDono + ")";
	}
}
