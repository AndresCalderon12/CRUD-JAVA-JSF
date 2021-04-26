package com.numenti.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.numenti.dao.ClienteDAO;
import com.numenti.enums.TipoLog;
import com.numenti.model.Cliente;
import com.numenti.utils.Log4jUtil;

@ManagedBean(name = "clienteBean")
@RequestScoped
public class ClienteBean {
	private ClienteDAO clienteDAO = new ClienteDAO();
	private String rutaPagina;
	private static final String RUTAINDEX = "/index.xhtml?faces-redirect=true";
	private static final String RUTAGREGAR = "/agregar.xhtml?faces-redirect=true";
	private static final String RUTAAGREGARSINREDIRECT = "agregar.xhtml";
	private static final String RUTAEDITAR = "/editar.xhtml?faces-redirect=true";
	private static final String RUTAEDITARSINREDIRECT = "editar.xhtml";
	private static final String ERRORCREARCLIENTE = "ha ocurrido un error creando el cliente";

	public String nuevo() {
		Cliente cliente = new Cliente();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("cliente", cliente);
		return RUTAGREGAR;
	}

	public String guardar(Cliente cliente) {
		// guarda la fecha de registro
		Date fechaActual = new Date();
		cliente.setFregistro(new java.sql.Date(fechaActual.getTime()));
		FacesContext context = FacesContext.getCurrentInstance();
		Integer correoExistente;
		rutaPagina = RUTAAGREGARSINREDIRECT;
		try {
			correoExistente = clienteDAO.buscarCorreo(cliente.getEmail());
			if (correoExistente == 0) {
				clienteDAO.guardar(cliente);
				rutaPagina = RUTAINDEX;

			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo ingresado ya existe", ""));
			}

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERRORCREARCLIENTE, ""));
			Log4jUtil.registrarInfo(getClass(), TipoLog.ERROR, ERRORCREARCLIENTE + e);
		}

		return rutaPagina;

	}

	public List<Cliente> obtenerClientes() {

		return clienteDAO.obtenerClientes();
	}

	public String editar(Long id) {
		Cliente cliente = clienteDAO.buscar(id);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("cliente", cliente);
		String correoActual = cliente.getEmail();
		sessionMap.put("correoActual", correoActual);
		return RUTAEDITAR;
	}

	public String actualizar(Cliente cliente) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		String correoActual = sessionMap.get("correoActual").toString();
		// guarda la fecha de actualizacion
		Date fechaActual = new Date();
		cliente.setFactualizar(new java.sql.Date(fechaActual.getTime()));
		FacesContext context = FacesContext.getCurrentInstance();
		Integer correo;
		rutaPagina = RUTAEDITARSINREDIRECT;
		try {
			correo = clienteDAO.buscarCorreo(cliente.getEmail());
			if (correo == 0 || (correoActual.equals(cliente.getEmail()))) {
				clienteDAO.editar(cliente);
				rutaPagina = RUTAINDEX;

			} else {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo ingresado ya existe", ""));
			}

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ERRORCREARCLIENTE, ""));
			Log4jUtil.registrarInfo(getClass(), TipoLog.ERROR, ERRORCREARCLIENTE + e);

		}
		return rutaPagina;
	}

	// eliminar un cliente
	public void eliminar(Long id) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			clienteDAO.eliminar(id);
			context.addMessage("MessageId",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente eliminado correctamente", ""));
		} catch (Exception e) {
			context.addMessage("MessageId",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ha ocurrido un error eliminando el cliente", ""));
		}

	}

}