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

	String vorname;
	String nachname;
	String email;

	public Nutzer() {

	}

	public Nutzer(String vorname, String nachname, String email) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
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
