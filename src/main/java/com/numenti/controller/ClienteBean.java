package com.numenti.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.numenti.DAO.ClienteDAO;
import com.numenti.model.Cliente;

@ManagedBean(name = "clienteBean")
@RequestScoped
public class ClienteBean {
	private ClienteDAO clienteDAO = new ClienteDAO();

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

		clienteDAO.guardar(cliente);
		return "/index.xhtml?faces-redirect=true";
	}

	public List<Cliente> obtenerClientes() {

		return clienteDAO.obtenerClientes();
	}

	public String editar(Long id) {
		Cliente cliente = new Cliente();
		cliente = clienteDAO.buscar(id);
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("cliente", cliente);
		return "/editar.xhtml?faces-redirect=true";
	}

	public String actualizar(Cliente cliente) {
		// guarda la fecha de actualizacion
		Date fechaActual = new Date();
		cliente.setFactualizar(new java.sql.Date(fechaActual.getTime()));
		clienteDAO.editar(cliente);
		return "/index.xhtml?faces-redirect=true";
	}

	// eliminar un cliente
	public String eliminar(Long id) {
		clienteDAO.eliminar(id);
		System.out.println("Cliente eliminado..");
		return "/index.xhtml?faces-redirect=true";
	}

}