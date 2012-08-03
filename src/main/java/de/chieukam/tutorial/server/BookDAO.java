package de.chieukam.tutorial.server;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import de.chieukam.tutorial.shared.BookDTO;

@Repository("bookDAO")
public class BookDAO extends AbstractHibernateJpaDAO<Long, BookDTO> {

	@PersistenceContext(unitName = "MyPUnit")
	EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}