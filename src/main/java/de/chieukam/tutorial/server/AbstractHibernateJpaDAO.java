package de.chieukam.tutorial.server;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class AbstractHibernateJpaDAO<K, E> {

	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractHibernateJpaDAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public void persist(E entity) {
		getEntityManager().persist(entity);
	}

	public void remove(E entity) {
		getEntityManager().remove(entity);
	}

	public void refresh(E entity) {
		getEntityManager().refresh(entity);
	}

	public E merge(E entity) {
		return getEntityManager().merge(entity);
	}

	public E findById(K id) {
		return getEntityManager().find(entityClass, id);
	}

	public E flush(E entity) {
		getEntityManager().flush();
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		String queryStr = "SELECT h FROM " + entityClass.getName() + " h";
		Query query = getEntityManager().createQuery(queryStr, entityClass);
		List<E> resultList = query.getResultList();
		return resultList;
	}

	public Integer removeAll() {
		String queryStr = "DELETE FROM " + entityClass.getName() + " h";
		Query query = getEntityManager().createQuery(queryStr);
		return query.executeUpdate();
	}

	protected abstract EntityManager getEntityManager();
}
