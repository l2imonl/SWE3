package de.hsb.app.jamu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "SelectNutzer", query = "Select n from Nutzer n")
@Entity
public class Nutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String username;
	private String passwort;
	private Rolle rolle;

	private String vorname;
	private String nachname;
	private String email;

	public Nutzer() {

	}

	public Nutzer(String username, String passwort, Rolle rolle, String vorname, String nachname, String email) {
		super();
		this.username = username;
		this.passwort = passwort;
		this.rolle = rolle;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public Rolle getRolle() {
		return rolle;
	}

	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
