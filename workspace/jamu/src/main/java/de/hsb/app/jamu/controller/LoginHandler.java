package de.hsb.app.jamu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
public class LoginHandler implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String passwort;
	private Nutzer nutzer;

	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	@PostConstruct
	public void init() {
		try {
			utx.begin();
			Query query = em.createQuery("Select n from Nutzer n");
			List<Nutzer> nutzerList = query.getResultList();
			System.out.println("Nutzer: " + nutzerList.size());
			if (nutzerList.size() == 0)
				em.persist(new Nutzer("Admin", "SafeAsShit", Rolle.ADMIN, "Big", "Boss", "boss@Aplha.com"));
				em.persist(new Nutzer("test", "test", Rolle.NUTZER, "test", "test", "test"));
			utx.commit();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String login() {
		Query query = em.createQuery("Select n from Nutzer n "
				+ "where n.username= :username and n.passwort = :passwort ");
		query.setParameter("username", username);
		query.setParameter("passwort", passwort);

		System.out.println(username + " " + passwort);
		List<Nutzer> nutzerList = query.getResultList();
		if (nutzerList.size() == 1) {
			nutzer = nutzerList.get(0);
			return "/adminPage.xhtml?faces-redirect=true";
		} else
			return null;
	}

	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (nutzer == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"login.xhtml?faces-redirect=true");
		}
	}

	public void isAdmin(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (nutzer.getRolle() != Rolle.ADMIN) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"index.xhtml?faces-redirect=true");
		}

	}
	
	public String upgrade() {
		nutzer.setUpgrade(true);
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nutzer = em.merge(nutzer);
		em.persist(nutzer);
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
		return "index";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces -redirect=true";
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

	public Nutzer getNutzer() {
		return nutzer;
	}

	public void setNutzer(Nutzer nutzer) {
		this.nutzer = nutzer;
	}

}
