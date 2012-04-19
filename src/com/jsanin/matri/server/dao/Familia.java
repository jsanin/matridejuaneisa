package com.jsanin.matri.server.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings("serial")
public class Familia implements Serializable{
	@Id Long id;
	@Unindexed 
	Key<Invitado> [] invitados;

	/**
	 * Cantidad de invitados confirmados
	 */
	@Unindexed 
	Long confirmados;
	@Unindexed
	Date ultimoIngreso;
	
	/**
	 * Este campo se inicializa en false. Si la familia ingresa y digita un número de invitados
	 * a asistir este valor se pone el true. Si la familia hace clic en No Voy, este campo queda
	 * en false.
	 */
	@Unindexed
	boolean asistira;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Key<Invitado>[] getInvitados() {
		return invitados;
	}
	public void setInvitados(Key<Invitado>[] invitados) {
		this.invitados = invitados;
	}
	public Long getConfirmados() {
		return confirmados;
	}
	public void setConfirmados(Long confirmados) {
		this.confirmados = confirmados;
	}
	public Date getUltimoIngreso() {
		return ultimoIngreso;
	}
	public void setUltimoIngreso(Date ultimoIngreso) {
		this.ultimoIngreso = ultimoIngreso;
	}
	public boolean isAsistira() {
		return asistira;
	}
	public void setAsistira(boolean asistira) {
		this.asistira = asistira;
	}
	
	

}
