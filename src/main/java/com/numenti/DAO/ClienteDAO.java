package com.numenti.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.numenti.model.Cliente;
import com.numenti.model.JPAUtil;

public class ClienteDAO {
	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	// Guardar Cliente en BD
	public void guardar(Cliente cliente) {
		entity.getTransaction().begin();
		entity.persist(cliente);
		entity.getTransaction().commit();
		// JPAUtil.shutdown();
	}

	// Editar cliente en BD
	public void editar(Cliente cliente) {
		entity.getTransaction().begin();
		entity.merge(cliente);
		entity.getTransaction().commit();
		// JPAUtil.shutdown();
	}

	// Buscar Cliente en bd
	public Cliente buscar(Long Id) {
		Cliente cliente = new Cliente();
		cliente = entity.find(Cliente.class, Id);
		// JPAUtil.shutdown();
		return cliente;
	}

	// obtener todos los clientes
	public List<Cliente> obtenerClientes() {
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		Query query = entity.createQuery("SELECT c FROM Cliente c");
		listaCliente = query.getResultList();
		return listaCliente;
	}

	// Eliminar cliente
	public void eliminar(Long Id) {
		Cliente cliente = new Cliente();
		cliente = entity.find(Cliente.class, Id);
		entity.getTransaction().begin();
		entity.remove(cliente);
		entity.getTransaction().commit();

	}

	public Integer buscarCorreo(String correo) {
		String sql = "SELECT COUNT(email) FROM clientes where email=?1";
		Query query = entity.createNativeQuery(sql);
		query.setParameter(1, correo);
		return Integer.valueOf(query.getSingleResult().toString());
	}
}
