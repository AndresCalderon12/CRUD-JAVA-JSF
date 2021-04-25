package com.numenti.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.numenti.DAO.ClienteDAO;
import com.numenti.model.Cliente;

@ManagedBean(name = "clienteBean")
@RequestScoped
public class ClienteBean {
	private ClienteDAO clienteDAO = new ClienteDAO();
	private String correoActual;

	public String nuevo() {
		Cliente cliente = new Cliente();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("cliente", cliente);
		return "/agregar.xhtml?faces-redirect=true";
	}

	public String guardar(Cliente cliente) {
		// guarda la fecha de registro
		Date fechaActual = new Date();
		cliente.setFregistro(new java.sql.Date(fechaActual.getTime()));
		FacesContext context = FacesContext.getCurrentInstance();
		Integer correo;
		Boolean existeCorreo = false;
		try {
			correo = clienteDAO.buscarCorreo(cliente.getEmail());
			if (correo == 0) {
				existeCorreo = false;
				clienteDAO.guardar(cliente);

			} else {
				existeCorreo = true;
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo ingresado ya existe", ""));
			}

		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ha ocurrido un error creando el cliente", ""));
		}
		if (existeCorreo) {
			return "/agregar.xhtml";
		} else {
			return "/index.xhtml?faces-redirect=true";
		}

	}

	public List<Cliente> obtenerClientes() {

		return clienteDAO.obtenerClientes();
	}

	public String editar(Long id) {
		Cliente cliente = new Cliente();
		cliente = clienteDAO.buscar(id);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("cliente", cliente);
		correoActual = cliente.getEmail();
		sessionMap.put("correoActual", correoActual);
		return "/editar.xhtml?faces-redirect=true";
	}

	public String actualizar(Cliente cliente) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		String correoActual = sessionMap.get("correoActual").toString();
		// guarda la fecha de actualizacion
		Date fechaActual = new Date();
		cliente.setFactualizar(new java.sql.Date(fechaActual.getTime()));
		FacesContext context = FacesContext.getCurrentInstance();
		Integer correo;
		Boolean existeCorreo = false;
		try {
			correo = clienteDAO.buscarCorreo(cliente.getEmail());
			if (correo == 0 || (correoActual.equals(cliente.getEmail()))) {
				existeCorreo = false;
				clienteDAO.editar(cliente);

			} else {
				existeCorreo = true;
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo ingresado ya existe", ""));
			}

		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ha ocurrido un error creando el cliente", ""));
		}
		if (existeCorreo) {
			return "/editar.xhtml";
		} else {
			return "/index.xhtml?faces-redirect=true";
		}
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