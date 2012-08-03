package de.chieukam.tutorial.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.chieukam.tutorial.shared.BookDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("springGwtServices/bookService")
public interface BookService extends RemoteService {

	public void saveOrUpdate(BookDTO book) throws Exception;
	
	public void delete(BookDTO book) throws Exception;
	
	public BookDTO find(long id);
	
	public List<BookDTO> findAllEntries();
	
}