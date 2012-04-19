package com.jsanin.matri.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jsanin.matri.shared.InfoFamilia;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void resetInvitados(AsyncCallback<Void> callback);

	void getInfoFamilia(String codigoFilia, String key, AsyncCallback<InfoFamilia> callback) 
			throws IllegalArgumentException;

	void noAsistencia(String codigoFilia, String key, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	void confirmarAsistencia(String codigoFilia, String key, int asistentes, AsyncCallback<Void> callback)
			throws IllegalArgumentException;

	void getHashCodes(AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
