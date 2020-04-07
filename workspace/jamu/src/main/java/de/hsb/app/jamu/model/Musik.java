package de.hsb.app.jamu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "SelectMusik", query = "Select n from Musik n")
@Entity
public class Musik {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String titel;
	private String kuenstler;
	private String album;
	private String pfad;

	public Musik() {

	}

	public Musik(String titel, String kuenstler, String album, String pfad) {
		super();
		this.titel = titel;
		this.kuenstler = kuenstler;
		this.album = album;
		this.pfad = pfad;
	}

	// Getter und Setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getKuenstler() {
		return kuenstler;
	}

	public void setKuenstler(String kuenstler) {
		this.kuenstler = kuenstler;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPfad() {
		return pfad;
	}

	public void setPfad(String pfad) {
		this.pfad = pfad;
	}
}
