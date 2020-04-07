package de.hsb.app.jamu.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import java.io.File;
import java.io.Serializable;

import de.hsb.app.jamu.model.Musik;
import de.hsb.app.jamu.model.Nutzer;

@ManagedBean
@ViewScoped
public class MusikUploadHandler implements Serializable {
	
	private DataModel<Musik> musikList;
	private Musik neueMusik;
	private Nutzer nutzer;
	
	UploadedFile trackFile;
	
	@PersistenceContext
	private EntityManager em;
	@Resource
	private UserTransaction utx;

	@PostConstruct
	public void init() {
		neueMusik = new Musik();
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		musikList = new ListDataModel<Musik>();
		musikList.setWrappedData(em.createNamedQuery("SelectMusik").getResultList());
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

	public void handleFileUpload(FileUploadEvent event) {
		this.trackFile = event.getFile();
		String trackName = event.getFile().getFileName();
		String dataDir = System.getProperty("jboss.server.data.dir");
		String musikDir = "musikOrdner";
		File musikOrdner = new File(dataDir + File.separator + musikDir);
        musikOrdner.mkdirs();
		
		// Upload Erfolgsmessage
		FacesMessage uploadErfolgMessage = new FacesMessage("Erfolg:", trackName + " wurde hochgeladen.");
		FacesContext.getCurrentInstance().addMessage(null, uploadErfolgMessage);
		
		// Musik schreiben
		try {
			File f = new File(musikOrdner, trackName);
			FileUtils.writeByteArrayToFile(f, trackFile.getContents());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		neueMusik.setTitel(trackName);
		neueMusik.setKuenstler(nutzer.getUsername());
		neueMusik.setAlbum(null);
		neueMusik.setPfad(dataDir + File.separator + musikDir + File.separator + trackName);
		
		// DB Shit
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		neueMusik = em.merge(neueMusik);
		em.persist(neueMusik);
		musikList.setWrappedData(em.createNamedQuery("SelectMusik").getResultList());
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
	
	// Getter und Setter
	public DataModel<Musik> getMusikList() {
		return musikList;
	}

	public void setMusikList(DataModel<Musik> musikList) {
		this.musikList = musikList;
	}

	public Musik getNeueMusik() {
		return neueMusik;
	}

	public void setNeueMusik(Musik neueMusik) {
		this.neueMusik = neueMusik;
	}
}