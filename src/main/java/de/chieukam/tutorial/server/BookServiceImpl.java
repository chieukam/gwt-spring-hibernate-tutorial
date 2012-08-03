package de.chieukam.tutorial.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.chieukam.tutorial.client.BookService;
import de.chieukam.tutorial.shared.BookDTO;
import de.chieukam.tutorial.shared.Validator;

@Service("bookService")
public class BookServiceImpl extends RemoteServiceServlet implements BookService {
	
	private static final long serialVersionUID = -6547737229424190373L;

	private static final Log LOG = LogFactory.getLog(BookServiceImpl.class);

	@Autowired
	private BookDAO bookDAO;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveOrUpdate(BookDTO book) throws Exception {
		
	    // Verify that the input is valid.
		if (Validator.isBlank(book.getTitle()) || Validator.isBlank(book.getAutor())) {
		      // If the input is not valid, throw an IllegalArgumentException back to
		      // the client.
		      throw new IllegalArgumentException("Please enter at least the Title and the Autor of the book");
		}
		
		try {
			if (book.getId() == null) {
				bookDAO.persist(book);
			} else {
				bookDAO.merge(book);
			}
		} catch (Exception e) {
			LOG.error(e);
			throw e;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(BookDTO book) throws Exception {
		if (book.getId() != null) {
			bookDAO.remove(book);
		}
	}

	public BookDTO find(long id) {
		return bookDAO.findById(id);
	}
	
	public List<BookDTO> findAllEntries() {
		List<BookDTO> findAll = bookDAO.findAll();
		List<BookDTO> result = new ArrayList<BookDTO>();
		for (BookDTO book : findAll) {
			result.add(new BookDTO(book));
		}
		return result;
	}

}
