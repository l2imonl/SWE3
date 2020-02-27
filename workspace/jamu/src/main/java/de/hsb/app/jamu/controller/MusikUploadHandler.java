package de.hsb.app.jamu.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;

import java.io.Serializable;

@ManagedBean
@ViewScoped
public class MusikUploadHandler implements Serializable {
	private static final long serialVersionUID = 1L;

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile trackFile = event.getFile();
		String trackName = event.getFile().getFileName();
		String musikOrdnerPfad = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		
		FacesMessage uploadErfolgMessage = new FacesMessage("Erfolg:", trackName + " wurde hochgeladen.");
		FacesContext.getCurrentInstance().addMessage(null, uploadErfolgMessage);
		
		try {
			trackFile.write(trackName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}