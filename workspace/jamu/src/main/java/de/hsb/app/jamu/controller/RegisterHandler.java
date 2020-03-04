package de.hsb.app.jamu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.hsb.app.jamu.model.Nutzer;
import de.hsb.app.jamu.model.Rolle;

@ManagedBean
@SessionScoped
public class RegisterHandler implements Serializable {

	private DataModel<Nutzer> nutzerList;
	private Nutzer neuerNutzer;

	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	@PostConstruct
	public void init() {
		neuerNutzer = new Nutzer();
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nutzerList = new ListDataModel<Nutzer>();
		nutzerList.setWrappedData(em.createNamedQuery("SelectNutzer").getResultList());
		try {
			utx.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String register() {
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		neuerNutzer = em.merge(neuerNutzer);
		em.persist(neuerNutzer);
		nutzerList.setWrappedData(em.createNamedQuery("SelectNutzer").getResultList());
		try {
			utx.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";
	}

	public DataModel<Nutzer> getNutzerList() {
		return nutzerList;
	}

	public void setNutzerList(DataModel<Nutzer> nutzerList) {
		this.nutzerList = nutzerList;
	}

	public Nutzer getNeuerNutzer() {
		return neuerNutzer;
	}

	public void setNeuerNutzer(Nutzer neuerNutzer) {
		this.neuerNutzer = neuerNutzer;
	}
	
	
}