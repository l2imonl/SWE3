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
	private String jsWebPfad;
	private String laenge;

	public Musik() {

	}

	public Musik(String titel, String kuenstler, String album, String jsWebPfad) {
		super();
		this.titel = titel;
		this.kuenstler = kuenstler;
		this.album = album;
		this.jsWebPfad = jsWebPfad;
		this.laenge = laenge;
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
	
	public String getJsWebPfad() {
		return jsWebPfad;
	}

	public void setJsWebPfad(String jsWebPfad) {
		this.jsWebPfad = jsWebPfad;
	}
	
	public String getLaenge() {
		return laenge;
	}

	public void setLaenge(String laenge) {
		this.laenge = laenge;
	}
}
