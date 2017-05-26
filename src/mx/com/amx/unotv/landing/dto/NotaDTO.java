package mx.com.amx.unotv.landing.dto;

public class NotaDTO {

	private String fcIdContenido;
	private String fcTitulo;
	private String fcDescripcion;
	private String fcLinkDetalle;
	private String fcIdCategoria;
	private String fcNombre;
	private String fcImgPrincipal;
	private String fiBanPatrocinio;
	private String fcIdTipoNota;
	private String fcDescripcionCategoria;
	private String fcNombreMagazine;
	private String fcFechaPublicacion;
	private String fcTabla;
	/**
	 * @return the fcIdContenido
	 */
	public String getFcIdContenido() {
		return fcIdContenido;
	}

	/**
	 * @param fcIdContenido
	 *            the fcIdContenido to set
	 */
	public void setFcIdContenido(String fcIdContenido) {
		this.fcIdContenido = fcIdContenido;
	}

	/**
	 * @return the fcTitulo
	 */
	public String getFcTitulo() {
		return fcTitulo;
	}

	/**
	 * @param fcTitulo
	 *            the fcTitulo to set
	 */
	public void setFcTitulo(String fcTitulo) {
		this.fcTitulo = fcTitulo;
	}
	
	/**
	 * @return the fcDescripcion
	 */
	public String getFcDescripcion() {
		return fcDescripcion;
	}

	/**
	 * @param fcDescripcion the fcDescripcion to set
	 */
	public void setFcDescripcion(String fcDescripcion) {
		this.fcDescripcion = fcDescripcion;
	}
	
	/**
	 * @return the fcLinkDetalle
	 */
	public String getFcLinkDetalle() {
		return fcLinkDetalle;
	}

	/**
	 * @param fcLinkDetalle the fcLinkDetalle to set
	 */
	public void setFcLinkDetalle(String fcLinkDetalle) {
		this.fcLinkDetalle = fcLinkDetalle;
	}

	/**
	 * @return the fcIdCategoria
	 */
	public String getFcIdCategoria() {
		return fcIdCategoria;
	}

	/**
	 * @param fcIdCategoria
	 *            the fcIdCategoria to set
	 */
	public void setFcIdCategoria(String fcIdCategoria) {
		this.fcIdCategoria = fcIdCategoria;
	}

	/**
	 * @return the fcNombre
	 */
	public String getFcNombre() {
		return fcNombre;
	}

	/**
	 * @param fcNombre
	 *            the fcNombre to set
	 */
	public void setFcNombre(String fcNombre) {
		this.fcNombre = fcNombre;
	}

	/**
	 * @return the fcImgPrincipal
	 */
	public String getFcImgPrincipal() {
		return fcImgPrincipal;
	}

	/**
	 * @param fcImgPrincipal
	 *            the fcImgPrincipal to set
	 */
	public void setFcImgPrincipal(String fcImgPrincipal) {
		this.fcImgPrincipal = fcImgPrincipal;
	}

	/**
	 * @return the fiBanPatrocinio
	 */
	public String getFiBanPatrocinio() {
		return fiBanPatrocinio;
	}

	/**
	 * @param fiBanPatrocinio
	 *            the fiBanPatrocinio to set
	 */
	public void setFiBanPatrocinio(String fiBanPatrocinio) {
		this.fiBanPatrocinio = fiBanPatrocinio;
	}

	/**
	 * @return the fcIdTipoNota
	 */
	public String getFcIdTipoNota() {
		return fcIdTipoNota;
	}

	/**
	 * @param fcIdTipoNota
	 *            the fcIdTipoNota to set
	 */
	public void setFcIdTipoNota(String fcIdTipoNota) {
		this.fcIdTipoNota = fcIdTipoNota;
	}

	/**
	 * @return the fcDescripcionCategoria
	 */
	public String getFcDescripcionCategoria() {
		return fcDescripcionCategoria;
	}

	/**
	 * @param fcDescripcionCategoria
	 *            the fcDescripcionCategoria to set
	 */
	public void setFcDescripcionCategoria(String fcDescripcionCategoria) {
		this.fcDescripcionCategoria = fcDescripcionCategoria;
	}

	/**
	 * @return the fcNombreMagazine
	 */
	public String getFcNombreMagazine() {
		return fcNombreMagazine;
	}
	
	/**
	 * @param fcNombreMagazine
	 *            the fcNombreMagazine to set
	 */
	public void setFcNombreMagazine(String fcNombreMagazine) {
		this.fcNombreMagazine = fcNombreMagazine;
	}
	
	/**
	 * @return el fcFechaPublicacion
	 */
	public String getFcFechaPublicacion() {
		return fcFechaPublicacion;
	}

	/**
	 * @param fcFechaPublicacion el fcFechaPublicacion a establecer
	 */
	public void setFcFechaPublicacion(String fcFechaPublicacion) {
		this.fcFechaPublicacion = fcFechaPublicacion;
	}
	
	/**
	 * @return el fcTabla
	 */
	public String getFcTabla() {
		return fcTabla;
	}

	/**
	 * @param fcTabla el fcTabla a establecer
	 */
	public void setFcTabla(String fcTabla) {
		this.fcTabla = fcTabla;
	}

	public String toString() {

		String NEW_LINE = System.getProperty("line.separator");
		StringBuffer result = new StringBuffer();
		result.append(" [Begin of Class] " + NEW_LINE);
		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		result.append(" fcIdContenido: _" + this.getFcIdContenido() + "_"
				+ NEW_LINE);
		result.append(" fcIdCategoria: _" + this.getFcIdCategoria() + "_"
				+ NEW_LINE);
		result.append(" fcNombre: _" + this.getFcNombre() + "_" + NEW_LINE);
		result.append(" fcImgPrincipal: _" + this.getFcImgPrincipal() + "_"
				+ NEW_LINE);
		result.append(" fiBanPatrocinio: _" + this.getFiBanPatrocinio() + "_"
				+ NEW_LINE);
		result.append(" fcNombreMagazine: _" + this.getFcNombreMagazine() + "_"
				+ NEW_LINE);
		/*
		 * result.append(" fcTitulo: _" + this.getFcTitulo() + "_" + NEW_LINE);
		 * result.append(" fcDescripcion: _" + this.getFcDescripcion() + "_" +
		 * NEW_LINE); result.append(" fcEscribio: _" + this.getFcEscribio() +
		 * "_" + NEW_LINE); result.append(" fcImgPrincipal: _" +
		 * this.getFcImgPrincipal() + "_" + NEW_LINE);
		 * result.append(" fcFechaPublicacion: _" + this.getFdFechaPublicacion()
		 * + "_" + NEW_LINE); result.append(" fcHora: _" + this.getFcHora() +
		 * "_" + NEW_LINE); result.append(" fcFuente: _" + this.getFcFuente() +
		 * "_" + NEW_LINE); result.append(" fcLugar: _" + this.getFcLugar() +
		 * "_" + NEW_LINE); result.append(" fcPieFoto: _" + this.getFcPieFoto()
		 * + "_" + NEW_LINE); result.append(" clGaleriaImagenes: _" +
		 * this.getClGaleriaImagenes() + "_" + NEW_LINE);
		 * result.append(" fcIdVideoYouTube: _" + this.getFcIdVideoYouTube() +
		 * "_" + NEW_LINE); result.append(" fcIdVideoOoyala: _" +
		 * this.getFcIdVideoOoyala() + "_" + NEW_LINE);
		 * result.append(" fcIdPlayerOoyala: _" + this.getFcIdPlayerOoyala() +
		 * "_" + NEW_LINE); result.append(" fcKeywords	: _" +
		 * this.getFcKeywords() + "_" + NEW_LINE); result.append(" fcFecha: _" +
		 * this.getFcFecha() + "_" + NEW_LINE); result.append(" fcHora: _" +
		 * this.getFcHora() + "_" + NEW_LINE);
		 */
		result.append(" [End of Class] " + NEW_LINE);
		result.append("}");
		NEW_LINE = null;

		return result.toString();
	}

}
