package de.chieukam.tutorial.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.chieukam.tutorial.shared.BookDTO;
import de.chieukam.tutorial.shared.Validator;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtSpringHibernate implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Book service.
	 */
	private final BookServiceAsync bookService = GWT.create(BookService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		DateBox.DefaultFormat defaultFormatter = new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd.MM.yyyy"));

		// Add new Book Area
		final TextBox titleField = new TextBox();
		titleField.setFocus(true);
		
		final TextBox subtitleField = new TextBox();
		final TextBox autorField = new TextBox();
		final TextBox descriptionField = new TextBox();
		final TextBox isbnField = new TextBox();
		final TextBox genreField = new TextBox();
		
		final DateBox publishedDateField = new DateBox();
		publishedDateField.setFormat(defaultFormatter);
		
		final TextBox publisherField = new TextBox();
		
		final Button saveButton = new Button("Save");
		saveButton.addStyleName("button");

		final Button retrieveButton = new Button("Retrieve");
		retrieveButton.addStyleName("button");

		final Label errorLabel = new Label();

		// Add fields to the Rootpanel
		RootPanel.get("title").add(titleField);
		RootPanel.get("subtitle").add(subtitleField);
		RootPanel.get("autor").add(autorField);
		RootPanel.get("description").add(descriptionField);
		RootPanel.get("isbn").add(isbnField);
		RootPanel.get("genre").add(genreField);
		RootPanel.get("publishedDate").add(publishedDateField);
		RootPanel.get("publisher").add(publisherField);
		RootPanel.get("btnSave").add(saveButton);
		RootPanel.get("btnRetrieveAllEntries").add(retrieveButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending request to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				saveButton.setEnabled(true);
				saveButton.setFocus(true);
				retrieveButton.setEnabled(true);
			}
		});

		class SaveBookHandler implements ClickHandler, KeyUpHandler {

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					saveBook();
				}				
			}

			public void onClick(ClickEvent arg0) {
				saveBook();
			}

			private void saveBook() {
				errorLabel.setText("");
				
				String _title = titleField.getText();
				String _subtitle = subtitleField.getText();
				String _autor = autorField.getText();
				String _desc = descriptionField.getText();
				String _isbn = isbnField.getText();
				String _genre = genreField.getText();
				Date _publishedDate = publishedDateField.getValue();
				String _publisher = publisherField.getText();

				// First, we validate the input.
				if (Validator.isBlank(_title) || Validator.isBlank(_autor)) {
					String errorMessage = "Please enter at least the Title and the Autor of the book";
					errorLabel.setText(errorMessage);
					return;
				}

				BookDTO bookDTO = new BookDTO(null, _title, _subtitle, _autor, _desc, _isbn, _genre, _publishedDate, _publisher);

				saveButton.setEnabled(false);
				serverResponseLabel.setText("");
				textToServerLabel.setText(bookDTO.toString());
				
				bookService.saveOrUpdate(bookDTO, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR + caught.toString());
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(Void noAnswer) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("OK");
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}
		
		// Add a handler to send the book info to the server
		SaveBookHandler saveBookHandler = new SaveBookHandler();
		saveButton.addClickHandler(saveBookHandler);
		publisherField.addKeyUpHandler(saveBookHandler);

		// Create a handler for the retrieveButton
		class RetrieveAllEntriesHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the retrieveButton.
			 */
			public void onClick(ClickEvent event) {
				retrieveAllEntries();
			}

			private void retrieveAllEntries() {
				// Nothing to validate here

				// Then, we send the input to the server.
				retrieveButton.setEnabled(false);
				errorLabel.setText("");
				textToServerLabel.setText("");
				serverResponseLabel.setText("");

				bookService.findAllEntries(
					new AsyncCallback<List<BookDTO>>() {

						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							dialogBox.setText("Remote Procedure Call - Failure");
							serverResponseLabel.addStyleName("serverResponseLabelError");
							serverResponseLabel.setHTML(SERVER_ERROR + caught.toString());
							dialogBox.center();
							closeButton.setFocus(true);
						}
	
						public void onSuccess(List<BookDTO> data) {
							dialogBox.setText("Remote Procedure Call");
							serverResponseLabel.removeStyleName("serverResponseLabelError");

							if(data != null && !data.isEmpty()){
								StringBuffer buffer = new StringBuffer();
								for (BookDTO book : data) {
									buffer.append(book.toString());
									buffer.append("<br /><br />");
								}
								serverResponseLabel.setHTML(buffer.toString());
							} else {
								serverResponseLabel.setHTML("No book information store in the database.");
							}
							dialogBox.center();
							closeButton.setFocus(true);
						}
				});
			}
		}

		// Add a handler
		RetrieveAllEntriesHandler retrieveAllEntriesHandler = new RetrieveAllEntriesHandler();
		retrieveButton.addClickHandler(retrieveAllEntriesHandler);
	}
}