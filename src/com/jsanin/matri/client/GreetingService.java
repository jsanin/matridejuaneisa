package com.jsanin.matri.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jsanin.matri.shared.InfoFamilia;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	void resetInvitados() ;

	InfoFamilia getInfoFamilia(String codigoFilia, String key)
			throws IllegalArgumentException;

	void noAsistencia(String codigoFilia, String key)
			throws IllegalArgumentException;

	void confirmarAsistencia(String codigoFilia, String key, int asistentes)
			throws IllegalArgumentException;

	String getHashCodes()
			throws IllegalArgumentException;
}
