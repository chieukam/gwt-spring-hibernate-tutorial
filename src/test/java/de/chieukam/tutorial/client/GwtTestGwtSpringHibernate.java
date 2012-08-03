package de.chieukam.tutorial.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import de.chieukam.tutorial.shared.BookDTO;
import de.chieukam.tutorial.shared.Validator;

/**
 * GWT JUnit <b>integration</b> tests must extend GWTTestCase. Using
 * <code>"GwtTest*"</code> naming pattern exclude them from running with
 * surefire during the test phase.
 * 
 * If you run the tests using the Maven command line, you will have to navigate
 * with your browser to a specific url given by Maven. See
 * http://mojo.codehaus.org/gwt-maven-plugin/user-guide/testing.html for
 * details.
 */
public class GwtTestGwtSpringHibernate extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "de.chieukam.tutorial.GwtSpringHibernateJUnit";
	}

	/**
	 * Tests the Validator.
	 */
	public void testValidator() {
		assertTrue(Validator.isBlank(null));
		assertTrue(Validator.isBlank(""));
		assertFalse(Validator.isBlank(" a "));
		assertFalse(Validator.isBlank(" a"));
		assertFalse(Validator.isBlank("ab "));
	}

	public void testBookingService() {
		// Create the service that we will test.
		BookServiceAsync bookService = GWT.create(BookService.class);
		ServiceDefTarget target = (ServiceDefTarget) bookService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL()
				+ "GwtSpringHibernate/springGwtServices/bookService");

		// Since RPC calls are asynchronous, we will need to wait for a response
		// after this test method returns. This line tells the test runner to
		// wait up to 10 seconds before timing out.
		delayTestFinish(10000);

		// Send a request to the server.
		bookService.findAllEntries(new AsyncCallback<List<BookDTO>>() {

			public void onFailure(Throwable caught) {
				// The request resulted in an unexpected error.
				fail("Request failure: " + caught.getMessage());
			}

			public void onSuccess(List<BookDTO> lists) {
				// Verify that the response is correct.
				assertTrue(lists.size() == 0);

				// Now that we have received a response, we need to tell the
				// test runner
				// that the test is complete. You must call finishTest() after
				// an
				// asynchronous test finishes successfully, or the test will
				// time out.
				finishTest();
			}
		});
	}
}
