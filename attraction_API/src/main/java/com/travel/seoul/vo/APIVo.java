package com.travel.seoul.vo;

import lombok.Data;

@Data
public class APIVo {
	
	private String bookId;
	private String title;
	private String author;
	private String publisher;
	private String releaseDate;
	private String isbn;
	
	public void Book() {}
	
	public void Book(String bookId, String title, String author, String publisher, String releaseDate, String isbn) {
		this.setBookId(bookId);
		this.setTitle(title);
		this.setAuthor(author);
		this.setPublisher(publisher);
		this.setReleaseDate(releaseDate);
		this.setIsbn(isbn);
	}
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Override
	public String toString() {
		return "bookId:" + bookId + ",title:" + title + ",author:" + author 
				+ ",publisher:" + publisher + ",releaseDate:" + releaseDate + ",isbn:" + isbn;
	}
}