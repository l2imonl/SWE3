package de.hsb.app.jamu.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.hsb.app.jamu.model.Nutzer;

@ManagedBean
@SessionScoped
public class NutzerHandler {

	private DataModel<Nutzer> nutzer;
	private Nutzer merkeNutzer;
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;
	
	@PostConstruct
	public void init() {
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.persist(new Nutzer("Felix", "Blume", "boss@Aplha.com"));
		em.persist(new Nutzer("Farid", "Hamed", "Banger@Musik.com"));
		nutzer = new ListDataModel<Nutzer>();
		nutzer.setWrappedData(em.createNamedQuery("SelectNutzer").getResultList());
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
	
	public String neu() {
		merkeNutzer = new Nutzer();
		return "neuerNutzer";
	}

	public String speichern() {
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		merkeNutzer = em.merge(merkeNutzer);
		em.persist(merkeNutzer);
		nutzer.setWrappedData(em.createNamedQuery("SelectNutzer").getResultList());
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
		return "alleNutzer";
	}
	
	public String delete() {
		merkeNutzer = nutzer.getRowData();
		System.out.println("Lösche Nutzer");
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		merkeNutzer = em.merge(merkeNutzer);
		em.remove(merkeNutzer);
		nutzer.setWrappedData(em.createNamedQuery("SelectNutzer").getResultList());
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
		return "alleNutzer";
	}
	
	//Getter u. Setter
	
	public DataModel<Nutzer> getNutzer() {
		return nutzer;
	}

	public void setNutzer(DataModel<Nutzer> nutzer) {
		this.nutzer = nutzer;
	}

	public Nutzer getMerkeNutzer() {
		return merkeNutzer;
	}

	public void setMerkeNutzer(Nutzer merkeNutzer) {
		this.merkeNutzer = merkeNutzer;
	}
	
	
}
