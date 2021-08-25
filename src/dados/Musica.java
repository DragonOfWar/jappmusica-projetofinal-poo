package dados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Musica {
	private int id;
	private String nome;
	private String letra;
	private String album;
	private int idDono;
	private LocalDate dataLancamento;
	private List<Artista> artistas;

	public Musica() {
		this.artistas = new ArrayList<Artista>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public void addArtista(Artista artista) {
		if (!this.artistas.contains(artista))
			this.artistas.add(artista);
	}

	public boolean removerArtista(Artista artista) {
		return this.artistas.remove(artista);
	}

	public int getIdDono() {
		return idDono;
	}

	public void setIdDono(int idDono) {
		this.idDono = idDono;
	}

	public String autoresString() {
		List<String> nomes = new ArrayList<String>();
		this.artistas.forEach(a -> nomes.add(a.getNome()));
		return String.join(", ", nomes);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Musica other = (Musica) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s = this.nome + " - ";
		s += this.autoresString();
		return s;
	}
}
