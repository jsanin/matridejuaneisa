package com.jsanin.matri.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.google.gwt.user.server.Base64Utils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.jsanin.matri.client.GreetingService;
import com.jsanin.matri.server.dao.Familia;
import com.jsanin.matri.server.dao.Invitado;
import com.jsanin.matri.shared.FieldVerifier;
import com.jsanin.matri.shared.InfoFamilia;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public static final String SECRET_KEY = "FFFFFFF324879";
    static {
        ObjectifyService.register(Invitado.class);
        ObjectifyService.register(Familia.class);
    }

	
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);
		
    	Objectify ofy = getOfy();
    	Invitado inv = new Invitado();
    	inv.setName("Ines");
    	inv.setSurname("Pineda");
    	ofy.put(inv);
    	System.out.println("Se grabó el invitado con ID: " + inv.getId());
		

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * k
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	protected Objectify getOfyTxn() {
		return ObjectifyService.beginTransaction();
	}

	protected Objectify getOfy() {
		return ObjectifyService.begin();
	}

	@Override
	public void resetInvitados() {
		
		
		
		
		ResourceBundle rb = ResourceBundle.getBundle("com.jsanin.matri.shared.invitados");
		
		Set<String> keys = rb.keySet();
		Iterator<String> iter = keys.iterator();
		
		Map<String, List<Invitado>> filias = new HashMap<String, List<Invitado>>();
		List<Invitado> invitados = new ArrayList<Invitado>();

		// organizando invitados por familias
		while(iter.hasNext()) {
			Invitado inv = new Invitado();
			String k = iter.next();
			// el nombre del invitado es la clave del properties
			inv.setName(k);
			invitados.add(inv);
			// el código de la filia es el valor del properties
			String filiaCod = rb.getString(k).trim();
			
			// organiza los invitados por familia
			List<Invitado> listaInvitadosFilia = filias.get(filiaCod);
			if(listaInvitadosFilia == null) {
				listaInvitadosFilia = new ArrayList<Invitado>();
				filias.put(filiaCod, listaInvitadosFilia);
			}
			listaInvitadosFilia.add(inv);
		}
		
		// almacena todos los invitados en la BD
		getOfy().put(invitados);
		System.out.println("[admin.reset] total invitados almacenados: " + invitados.size());
		
		Collection<List<Invitado>> listInvitados = filias.values();
		List<Familia> fs = new ArrayList<Familia>();
		
		for (List<Invitado> list : listInvitados) {
			Familia f = new Familia();
			Key<Invitado> [] invis = new Key[list.size()];
			int i = 0;
			for (Invitado inv : list) {
				invis[i++] = new Key<Invitado>(Invitado.class, inv.getId());
			}
			f.setInvitados(invis);
			f.setAsistira(false);
			f.setConfirmados(0L);
			f.setUltimoIngreso(null);
			fs.add(f);
		}
		getOfy().put(fs);
		System.out.println("[admin.reset] total familias almacenadas: " + fs.size());
		MessageDigest msgMD5 = null;
		try {
			msgMD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
//		msgMD5.reset();
		for (Familia familia : fs) {
			byte[] tokenDigest = msgMD5.digest(
					getBytesToDigest(familia.getId().toString()));
			System.out.println("cf: " + familia.getId() + 
					" cant_inv: " + familia.getInvitados().length + 
					" tk: " + Base64Utils.toBase64(tokenDigest));

		}
		

	}

	private byte[] getBytesToDigest(String filiaId) {
		return new StringBuffer(filiaId).
				append(SECRET_KEY).toString().getBytes();
	}

	@Override
	public InfoFamilia getInfoFamilia(String codigoFilia, String key) throws IllegalArgumentException  {
		
		validarCodigo(codigoFilia, key);
		
		Familia f = getOfy().find(Familia.class, Long.parseLong(codigoFilia));
		Map<Key<Invitado>, Invitado> fetched = getOfy().get(Arrays.asList(f.getInvitados()));
		Collection<Invitado> invs = fetched.values();
		int size = invs.size();
		StringBuilder txtInvs = new StringBuilder();
		int i = 1;
		for (Invitado inv : invs) {
			if(inv.getName().startsWith("i") || inv.getName().startsWith("I")) {
				String txtInvsT = txtInvs.toString();
				if(txtInvsT.endsWith("y ")) {
					txtInvsT = txtInvsT.substring(0, txtInvsT.length() - 3);
					txtInvs.delete(0, txtInvs.length());
					txtInvs.append(txtInvsT).append(" e ");
				}
			}
			txtInvs.append(inv.getName());
			if((i + 1) == size) {
				txtInvs.append(" y ");
			} else if((i + 1) > size) {
			} else {
				txtInvs.append(", ");
			}
			i++;
		}
//		f.setUltimoIngreso(new Date());
//		getOfy().put(f);
		
		InfoFamilia infoFam = new InfoFamilia();
		infoFam.setNombreInvis(txtInvs.toString());
		infoFam.setCantidadInvitados(Long.valueOf(size));
		infoFam.setInvitadosConfirmados(f.getConfirmados());
		infoFam.setUltimoIngreso(f.getUltimoIngreso());
		infoFam.setAsistira(f.isAsistira());
		 
		return infoFam;
	}

	private void validarCodigo(String codigoFilia, String key) {
		MessageDigest msgMD5 = null;
		try {
			msgMD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] tokenDigest = msgMD5.digest(
				getBytesToDigest(codigoFilia));
		
		if(!Base64Utils.toBase64(tokenDigest).equals(key)) {
			throw new IllegalArgumentException("Código inválido: " + codigoFilia + " " + key);
		}
	}

	@Override
	public void noAsistencia(String codigoFilia, String key)
			throws IllegalArgumentException {
		validarCodigo(codigoFilia, key);
		
		Familia f = getOfy().find(Familia.class, Long.parseLong(codigoFilia));
		f.setAsistira(false);
		f.setUltimoIngreso(new Date());
		f.setConfirmados(0L);
		getOfy().put(f);
		
	}

	@Override
	public void confirmarAsistencia(String codigoFilia, String key,
			int asistentes) throws IllegalArgumentException {
		validarCodigo(codigoFilia, key);
		
		Familia f = getOfy().find(Familia.class, Long.parseLong(codigoFilia));
		f.setAsistira(asistentes > 0);
		f.setUltimoIngreso(new Date());
		f.setConfirmados(new Long(asistentes));
		getOfy().put(f);
		
	}

	public static void main(String[] args) {
		MessageDigest msgMD5 = null;
		try {
			msgMD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] tokenDigest = msgMD5.digest(
				new GreetingServiceImpl().getBytesToDigest(args[0]));
		
		System.out.println(Base64Utils.toBase64(tokenDigest));

	}

	@Override
	public String getHashCodes() throws IllegalArgumentException {
		MessageDigest msgMD5 = null;
		try {
			msgMD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		
		Iterable<Familia> filias = getOfy().query(Familia.class);
		StringBuilder sb = new StringBuilder("F INVS NOMBINVS CONF FCONF CD\n");
		for (Familia f : filias) {
			byte[] tokenDigest = msgMD5.digest(
					getBytesToDigest(f.getId().toString()));
			
			sb.append(f.getId()).append(" ").
				append(f.getInvitados()!=null?f.getInvitados().length:0).append(" ").
				append(getNombreInvitados(f.getInvitados())).append(" ").
				append(f.getConfirmados()).append(" ").
				append("\"").append(f.getUltimoIngreso()).append("\" ").
				append(Base64Utils.toBase64(tokenDigest)).append("\n");
			
		}
		return sb.toString();
	}

	private String getNombreInvitados(Key<Invitado>[] keys) {
		if(keys == null) {
			return " _ ";
		}
		try {
			StringBuilder sb = new StringBuilder(" \"");
			for (Key<Invitado> key : keys) {
				Invitado inv = getOfy().get(key);
				sb.append(inv.getId()).append("_").append(inv.getName()).append(",");
			}
			return sb.append("\" ").toString();
		} catch(com.googlecode.objectify.NotFoundException nfe) {
			nfe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return " _ ";
	}
	
}
