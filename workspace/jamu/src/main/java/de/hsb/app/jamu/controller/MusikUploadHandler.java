package de.hsb.app.jamu.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;

import java.io.File;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class MusikUploadHandler implements Serializable {
	private static final long serialVersionUID = 1L;
	UploadedFile trackFile;

	public void handleFileUpload(FileUploadEvent event) {
		this.trackFile = event.getFile();
		String trackName = event.getFile().getFileName();
		
		FacesMessage uploadErfolgMessage = new FacesMessage("Erfolg:", trackName + " wurde hochgeladen.");
		FacesContext.getCurrentInstance().addMessage(null, uploadErfolgMessage);
		
		try {
			String s = System.getProperty("jboss.server.data.dir"); //Kann ich als Final und Static declaren
			File musikOrdner = new File(s + File.separator + "musikOrdner");
	        musikOrdner.mkdirs();
			File f = new File(musikOrdner, trackName);
			FileUtils.writeByteArrayToFile(f, trackFile.getContents());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}