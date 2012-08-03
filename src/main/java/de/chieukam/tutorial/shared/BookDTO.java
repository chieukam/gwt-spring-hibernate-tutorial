package de.chieukam.tutorial.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBL_BOOK")
public class BookDTO implements Serializable {

	private static final long serialVersionUID = -687874117917352477L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "SUBTITLE")
	private String subtitle;

	@Column(name = "AUTOR", nullable = false)
	private String autor;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ISBN")
	private String isbn;

	@Column(name = "GENRE")
	private String genre;

	@Temporal(TemporalType.DATE)
	@Column(name = "PUBLISHED_DATE")
	private Date publishedDate;

	@Column(name = "PUBLISHER")
	private String publisher;

	public BookDTO() {
	}

	public BookDTO(Long id, String title, String subtitle, String autor,
			String description, String isbn, String genre, Date publishedDate,
			String publisher) {
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.autor = autor;
		this.description = description;
		this.isbn = isbn;
		this.genre = genre;
		this.publishedDate = publishedDate;
		this.publisher = publisher;
	}

	public BookDTO(BookDTO bookDTO) {
		this.id = bookDTO.getId();
		this.title = bookDTO.getTitle();
		this.subtitle = bookDTO.getSubtitle();
		this.autor = bookDTO.getAutor();
		this.description = bookDTO.getDescription();
		this.isbn = bookDTO.getIsbn();
		this.genre = bookDTO.getGenre();
		this.publishedDate = bookDTO.getPublishedDate();
		this.publisher = bookDTO.getPublisher();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result
				+ ((publishedDate == null) ? 0 : publishedDate.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result
				+ ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDTO other = (BookDTO) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (publishedDate == null) {
			if (other.publishedDate != null)
				return false;
		} else if (!publishedDate.equals(other.publishedDate))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (subtitle == null) {
			if (other.subtitle != null)
				return false;
		} else if (!subtitle.equals(other.subtitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", subtitle=" + subtitle
				+ ", author=" + autor + ", description=" + description
				+ ", isbn=" + isbn + ", genre=" + genre + ", publishedDate="
				+ publishedDate + ", publisher=" + publisher + "]";
	}

}
