package com.jsanin.matri.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class InfoFamilia implements Serializable {
	String nombreInvis;
	Long cantidadInvitados;
	Date ultimoIngreso;
	Long invitadosConfirmados;
	boolean asistira;
	public String getNombreInvis() {
		return nombreInvis;
	}
	public void setNombreInvis(String nombreInvis) {
		this.nombreInvis = nombreInvis;
	}
	public Long getCantidadInvitados() {
		return cantidadInvitados;
	}
	public void setCantidadInvitados(Long cantidadInvitados) {
		this.cantidadInvitados = cantidadInvitados;
	}
	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}
	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}
	public Long getInvitadosConfirmados() {
		return invitadosConfirmados;
	}
	public void setInvitadosConfirmados(Long invitadosConfirmados) {
		this.invitadosConfirmados = invitadosConfirmados;
	}
	public boolean isAsistira() {
		return asistira;
	}
	public void setAsistira(boolean asistira) {
		this.asistira = asistira;
	}
	
	
	
}
