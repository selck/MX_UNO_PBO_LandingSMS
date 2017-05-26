package mx.com.amx.unotv.landing.dto;

import java.io.Serializable;

public class ParametrosDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private String pathFiles;
	private String pathShell;
	private String pathShellElimina;
	private String pathRemote;
	private String nameHTML;
	private String JsoupConnectLandingSMS;
	private String baseTheme;
	private String carpetaResources;
	private String basePagesPortal;
	private String ambiente;
	private String URL_WS_BASE;
	private String idMagazineHome;
	private String baseURL;
	private String dominioPreOlimpicos;
	
	//Variables del Jsoup.connect
	public String getPathShell() {
		return pathShell;
	}
	public void setPathShell(String pathShell) {
		this.pathShell = pathShell;
	}
	public String getPathShellElimina() {
		return pathShellElimina;
	}
	public void setPathShellElimina(String pathShellElimina) {
		this.pathShellElimina = pathShellElimina;
	}
	public String getPathRemote() {
		return pathRemote;
	}
	public void setPathRemote(String pathRemote) {
		this.pathRemote = pathRemote;
	}
	public String getPathFiles() {
		return pathFiles;
	}
	public void setPathFiles(String pathFiles) {
		this.pathFiles = pathFiles;
	}
	public String getNameHTML() {
		return nameHTML;
	}
	public void setNameHTML(String nameHTML) {
		this.nameHTML = nameHTML;
	}
	public String getJsoupConnectLandingSMS() {
		return JsoupConnectLandingSMS;
	}
	public void setJsoupConnectLandingSMS(String jsoupConnectLandingSMS) {
		JsoupConnectLandingSMS = jsoupConnectLandingSMS;
	}
	public String getBaseTheme() {
		return baseTheme;
	}
	public void setBaseTheme(String baseTheme) {
		this.baseTheme = baseTheme;
	}
	public String getCarpetaResources() {
		return carpetaResources;
	}
	public void setCarpetaResources(String carpetaResources) {
		this.carpetaResources = carpetaResources;
	}
	public String getBasePagesPortal() {
		return basePagesPortal;
	}
	public void setBasePagesPortal(String basePagesPortal) {
		this.basePagesPortal = basePagesPortal;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getURL_WS_BASE() {
		return URL_WS_BASE;
	}
	public void setURL_WS_BASE(String url_ws_base) {
		URL_WS_BASE = url_ws_base;
	}
	public String getIdMagazineHome() {
		return idMagazineHome;
	}
	public void setIdMagazineHome(String idMagazineHome) {
		this.idMagazineHome = idMagazineHome;
	}
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	public String getDominioPreOlimpicos() {
		return dominioPreOlimpicos;
	}
	public void setDominioPreOlimpicos(String dominioPreOlimpicos) {
		this.dominioPreOlimpicos = dominioPreOlimpicos;
	}
	
	
}
