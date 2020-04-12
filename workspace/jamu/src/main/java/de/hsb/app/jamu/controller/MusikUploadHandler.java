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
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import de.hsb.app.jamu.model.Musik;

@ManagedBean
@ViewScoped
public class MusikUploadHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	private DataModel<Musik> musikList;
	private Musik neueMusik;
	
	UploadedFile musikDatei;
	
	// Anlegen der benoetigten Pfade
	final static String dataDir = System.getProperty("jboss.server.data.dir");
	final static String musikDir = "musikOrdner";
	final static File musikOrdner = new File(dataDir + File.separator + musikDir);
    
	
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
			} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void handleFileUpload(FileUploadEvent event) {
		// Metadaten
		Mp3File mp3file = null;
		
		this.musikDatei = event.getFile();
		musikOrdner.mkdirs();
		String musikDateiName = event.getFile().getFileName();
		String zielDateiPfad = musikOrdner + File.separator + musikDateiName;
		
		// Upload Erfolgsmessage
		FacesMessage uploadErfolgMessage = new FacesMessage("Erfolg:", musikDateiName + " wurde hochgeladen.");
		FacesContext.getCurrentInstance().addMessage(null, uploadErfolgMessage);
		
		// Musik schreiben
		try {
			File zielDatei = new File(musikOrdner, musikDateiName);
			FileUtils.writeByteArrayToFile(zielDatei, musikDatei.getContents());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mp3file = new Mp3File(zielDateiPfad);
		} catch (UnsupportedTagException | InvalidDataException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		// Metadaten extrahieren und setten
		metaDatenHandler(mp3file, musikDateiName);
		
		try {
			utx.begin();
		} catch (NotSupportedException | SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		neueMusik = em.merge(neueMusik);
		em.persist(neueMusik);
		musikList.setWrappedData(em.createNamedQuery("SelectMusik").getResultList());
		
		try {
			utx.commit();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void metaDatenHandler(Mp3File mp3file, String musikDateiName) {
		neueMusik.setLaenge(convertLongToString(mp3file.getLengthInSeconds()));
		neueMusik.setJsWebPfad("/musik/" + musikDateiName);
		
		if (mp3file.hasId3v2Tag()) {
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			neueMusik.setTitel(id3v2Tag.getTitle());
			neueMusik.setKuenstler(id3v2Tag.getArtist());
			neueMusik.setAlbum(id3v2Tag.getAlbum());
		}
		
		else if (mp3file.hasId3v1Tag()) {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			neueMusik.setTitel(id3v1Tag.getTitle());
			neueMusik.setKuenstler(id3v1Tag.getArtist());
			neueMusik.setAlbum(id3v1Tag.getAlbum());
		}
		
		else {
			neueMusik.setTitel(FilenameUtils.removeExtension(musikDateiName));
			neueMusik.setKuenstler(null);
			neueMusik.setAlbum(null);
			}
	}
	
	public String convertLongToString (long laenge) {
		long min = laenge / 60;
		long sek = laenge % 60;
		String laengeInMin = Long.toString(min) + ":";
		String laengeInSek = sek < 9 ? "0" + Long.toString(sek) : Long.toString(sek);
		return laengeInMin + laengeInSek;
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