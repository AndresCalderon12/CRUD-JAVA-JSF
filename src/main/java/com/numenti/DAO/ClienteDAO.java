package com.numenti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.numenti.model.Cliente;
import com.numenti.utils.JPAUtil;

public class ClienteDAO {
	EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();

	// Guardar Cliente en BD
	public void guardar(Cliente cliente) {
		entity.getTransaction().begin();
		entity.persist(cliente);
		entity.getTransaction().commit();
	}

	// Editar cliente en BD
	public void editar(Cliente cliente) {
		entity.getTransaction().begin();
		entity.merge(cliente);
		entity.getTransaction().commit();
	}

	// Buscar Cliente en bd
	public Cliente buscar(Long Id) {
		return entity.find(Cliente.class, Id);
	}

	// obtener todos los clientes
	public List<Cliente> obtenerClientes() {
		Query query = entity.createQuery("SELECT c FROM Cliente c");
		List<Cliente> listaCliente = query.getResultList();
		return listaCliente;
	}

	// Eliminar cliente
	public void eliminar(Long Id) {
		Cliente cliente = entity.find(Cliente.class, Id);
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
