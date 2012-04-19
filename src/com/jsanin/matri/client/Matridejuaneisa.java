package com.jsanin.matri.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.jsanin.matri.shared.InfoFamilia;

public class Matridejuaneisa implements EntryPoint, HasText {
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

//	private static MatridejuaneisaUiBinder uiBinder = GWT.create(MatridejuaneisaUiBinder.class);

	interface MatridejuaneisaUiBinder extends UiBinder<Widget, Matridejuaneisa> {
	}

	DateTimeFormat sdf = DateTimeFormat.getFormat("d 'de' MMMM");
	
	
	
	public Matridejuaneisa() {
	}

	@UiField
	Button button;

	public Matridejuaneisa(String firstName) {
		
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		greetingService.greetServer(((Button)e.getSource()).getText(),
		new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				Window.alert("Error!");
			}

			public void onSuccess(String result) {
				Window.alert("Éxito!!!!!");
			}
		});
		
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

//		if(Window.Location.getPath() != null 
//				&& (Window.Location.getPath().equals("/") || 
//						Window.Location.getPath().equals("/index.html"))) {
//			// si ingresa por el index.html no haga nada
//			return;
//		}
		
//		// editar familias e invitados
//		final DialogBox dialogBoxInv = new DialogBox();
//		final TextBox cdFilia = new TextBox();
//		TextBox cdInv = new TextBox();
//		final TextBox nomInv = new TextBox();
//
//		Button btDelInv = new Button("Del invitado");
//		Button nwInv = new Button("New invitado");
//		nwInv.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				try {
//					int intCdFilia = Integer.parseInt(cdFilia.getText());
//					if(nomInv.getText().length() >= 4) {
//						
//					} else {
//						Window.alert("Tamaño de nombre no válido, mínimo 4 caracteres");
//					}
//				} catch(NumberFormatException e) {
//					Window.alert("Num de familia inválido");
//				}
//			}
//		});
//		
//		
//		Button clsInv = new Button("Cerrar");
//		clsInv.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBoxInv.hide();
//			}
//		});
//
//		
//		final String edtfilia = Window.Location.getParameter("edtfilia");
//		if(edtfilia != null && edtfilia.equals("true")) {
////			String puser = Window.prompt("Ingreso codigo de acceso: ", "");
//			String puser = "2881985";
//			if(puser != null && puser.equals("2881985")) {
//				dialogBoxInv.setText("Editar familias");
//				dialogBoxInv.setAnimationEnabled(true);
//				dialogBoxInv.setModal(true);
//				
//				VerticalPanel dialogVPanel = new VerticalPanel();
//				dialogVPanel.addStyleName("dialogVPanel");
//				dialogVPanel.add(new HTML("<b>Cod Familia</b>"));
//				dialogVPanel.add(cdFilia);
//
//				dialogVPanel.add(new HTML("<b>Cod Invitado</b>"));
//				dialogVPanel.add(cdInv);
//
//				dialogVPanel.add(new HTML("<b>Nom Invitado</b>"));
//				dialogVPanel.add(nomInv);
//
//				HorizontalPanel bPanel = new HorizontalPanel();
//				bPanel.add(btDelInv);
//				bPanel.add(nwInv);
//				bPanel.add(clsInv);
//				dialogVPanel.add(bPanel);
//
//				dialogBoxInv.setWidget(dialogVPanel);
//
//				dialogBoxInv.center();			
//				
//				
////				greetingService.resetInvitados(new AsyncCallback<Void>() {
////					@Override
////					public void onFailure(Throwable caught) {
////						Window.alert(caught.getMessage());
////
////					}
////					
////					@Override
////					public void onSuccess(Void result) {
////						Window.alert("Invitados reseteados!");
////					}
////				});
//				return;
//			}
//		}

		final String resetParam = Window.Location.getParameter("reset");
		if(resetParam != null && resetParam.equals("true")) {
			String puser = Window.prompt("Ingreso codigo de acceso: ", "");
			if(puser != null && puser.equals("2881985")) {
				greetingService.resetInvitados(new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());

					}
					
					@Override
					public void onSuccess(Void result) {
						Window.alert("Invitados reseteados!");
					}
				});
				return;
			}
		}

		final String paramKeys = Window.Location.getParameter("ks");
		if(paramKeys != null && paramKeys.equals("true")) {
//			String puser = Window.prompt("Ingreso codigo de acceso: ", "");
//			if(puser != null && puser.equals("2881985")) {
				greetingService.getHashCodes(new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
	
					}
					
					@Override
					public void onSuccess(String result) {
						RootPanel root = RootPanel.get();
						root.clear();
						root.add(new Label(result));
					}
				});
				return;
//			}
		}
		
		
		final String codigoFilia = Window.Location.getParameter("inv");
		final String key = Window.Location.getParameter("k");
		
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Confirmación de personas");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setModal(true);
		final Button closeButton = new Button("Cancelar");
		final Button okButton = new Button("Aceptar");
		// We can set the id of a widget by accessing its Element
//		closeButton.getElement().setId("closeButton");
		final TextBox cantidadPersonas = new TextBox();

		// busca la información de la familia
		greetingService.getInfoFamilia(codigoFilia, key, new AsyncCallback<InfoFamilia>() {
			
			@Override
			public void onSuccess(final InfoFamilia ifam) {
//		InfoFamilia ifam = new InfoFamilia();

				String nombreInvs = ifam.getNombreInvis();
				
				cantidadPersonas.setText(ifam.getCantidadInvitados().toString());
				RootPanel confinv = RootPanel.get("confinv");
				confinv.add(new Label(cantidadPersonas.getText()));
				
				RootPanel respTxt = RootPanel.get("respTxt");
				
				if(ifam.getUltimoIngreso() == null) {
					respTxt.add(new Label("¡No has confirmado tu asistencia!"));
				} else {
					String txtFecha = sdf.format(ifam.getUltimoIngreso());
					int idx = txtFecha.indexOf("April");
					if(idx >= 0) {
						// encontró April
						txtFecha = txtFecha.substring(0, idx) + "Abril";
					}
						
					idx = txtFecha.indexOf("May");
					if(idx >= 0) {
						// encontró May
						txtFecha = txtFecha.substring(0, idx) + "Mayo";
					}
					if(ifam.isAsistira()) {
						respTxt.add(new Label("El " + txtFecha + 
								" dijiste que iba" + (ifam.getInvitadosConfirmados()>1?"n ":" ") + ifam.getInvitadosConfirmados().toString() +
								" persona" + (ifam.getInvitadosConfirmados()>1?"s":"")));
					} else {
						respTxt.add(new Label("El " + txtFecha + 
								" dijiste que no asistirás :'("));
					}
				}
				
				


				// dialog
				VerticalPanel dialogVPanel = new VerticalPanel();
				dialogVPanel.addStyleName("dialogVPanel");
				dialogVPanel.add(new HTML("<b>Cuántas personas van?</b>"));
				dialogVPanel.add(cantidadPersonas);
				HorizontalPanel bPanel = new HorizontalPanel();
				bPanel.add(okButton);
				bPanel.add(closeButton);
				dialogVPanel.add(bPanel);
				dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
				dialogBox.setWidget(dialogVPanel);

				// fin dialog
				
				// Add a handler to close the DialogBox
				closeButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.hide();
					}
				});
				
				okButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						okButton.setEnabled(false);
						closeButton.setEnabled(false);
						
						if(cantidadPersonas.getText().equals("juan78isa")) {
							// resetea los invitados
							greetingService.resetInvitados(new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable caught) {
									Window.alert(caught.getMessage());
									okButton.setEnabled(true);
									closeButton.setEnabled(true);

								}
								
								@Override
								public void onSuccess(Void result) {
									Window.alert("Invitados reseteados!");
									dialogBox.hide();
								}
							});
							
						} else {
							String cantInvalida = "";
							int cantPersonas = 0;
							try {
								cantPersonas = Integer.parseInt(cantidadPersonas.getText());
								if(cantPersonas < 0) {
									cantInvalida = "Número no válido. Debe ser un número entero positivo. ";
								}
								if(cantPersonas > ifam.getCantidadInvitados()) {
									cantInvalida += "Debes ingresar máximo " + ifam.getCantidadInvitados() 
											+ " invitado(s).";
								}
							} catch(NumberFormatException nex) {
								cantInvalida = "Número no válido. Debe ser un número entero positivo.";
							}
							if(cantInvalida.length() > 0) {
								Window.alert(cantInvalida);
								okButton.setEnabled(true);
								closeButton.setEnabled(true);
								cantidadPersonas.selectAll();
								cantidadPersonas.setFocus(true);
							} else {
								greetingService.confirmarAsistencia(
										codigoFilia, key, cantPersonas, new AsyncCallback<Void>() {
									
									@Override
									public void onSuccess(Void result) {
										Window.alert(cantidadPersonas.getText() + " persona(s) están confirmadas. Los esperamos! :D");
										dialogBox.hide();
										Window.Location.reload();
									}
											
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Error: " + caught.getMessage());
										
									}
								}); 
							}
						}
						
						
					}
				});

				RootPanel invitado = RootPanel.get("invitado");
				invitado.add(new Label(nombreInvs));
				
				

				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
		
		//final Anchor siVoy = new Anchor("<IMG border=0 name=siVoy src=\"images/graficas-03_slice_07.jpg\" width=102 height=37>", true);
		final Anchor siVoy = new Anchor("<img src=\"images/graficas-03_slice_07.jpg\" name=\"siVoy\" width=\"102\" height=\"37\" border=\"0\">", true);
		siVoy.setHref("#");				
		siVoy.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
//				String asistencia = Window.prompt("Cuantas persona asistirán?", "0");
//				if(asistencia != null) {
//					Window.alert(asistencia + " persona(s) están confirmadas. Los esperamos! :D");
//					RootPanel confinv = RootPanel.get("confinv");
//					confinv.add(new Label(cantidadPersonas.getText()));
//				}
				
				okButton.setEnabled(true);
				closeButton.setEnabled(true);
				dialogBox.center();
				cantidadPersonas.selectAll();
				cantidadPersonas.setFocus(true);
//				okButton.setFocus(true);
				
			}
		});

		RootPanel svoy = RootPanel.get("svoy");
		svoy.add(siVoy);

		//final Anchor noVoy = new Anchor("<img src=\"images/graficas-03_slice_08.jpg\" name=noVoy width=160 height=37 border=0>", true);
		final Anchor noVoy = new Anchor("<img src=\"images/graficas-03_slice_08.jpg\" name=\"noVoy\" width=\"160\" height=\"37\" border=\"0\">", true);
		noVoy.setHref("#");				
		noVoy.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(Window.confirm("¿Confirmas que no vas a asistir?")) {
					greetingService.noAsistencia(codigoFilia, key, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							Window.alert("Confirmada tu no-asistencia. Chao! :)");
							Window.Location.reload();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error: " + caught.getMessage());
						};
					});
					
				}
			}
		});

		RootPanel nvoy = RootPanel.get("nvoy");
		nvoy.add(noVoy);
		

	}

}
