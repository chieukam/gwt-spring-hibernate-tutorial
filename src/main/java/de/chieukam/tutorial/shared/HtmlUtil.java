package de.chieukam.tutorial.shared;

public class HtmlUtil {

	  /**
	   * Escape an html string. Escaping data received from the client helps to
	   * prevent cross-site script vulnerabilities.
	   *
	   * @param html the html string to escape
	   * @return the escaped string
	   */
	  public static String escapeHtml(String html) {
	    if (html == null) {
	      return null;
	    }
	    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
	        ">", "&gt;");
	  }
}
