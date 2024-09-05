package com.gkemayo.library.book;

import java.time.LocalDate;

import com.gkemayo.library.category.CategoryDTO;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(defaultValue = "Book Model")
public class BookDTO implements Comparable<BookDTO>{
	
	@Schema(defaultValue = "Book id")
	private Integer id;

	@Schema(defaultValue = "Book title")
	private String title;
	
	@Schema(defaultValue = "Book isbn")
	private String isbn;
	
	@Schema(defaultValue = "Book release date by the editor")
	private LocalDate releaseDate;
	
	@Schema(defaultValue = "Book register date in the library")
	private LocalDate registerDate;
	
	@Schema(defaultValue = "Book total examplaries")
	private Integer totalExamplaries;
	
	@Schema(defaultValue = "Book author")
	private String author;
	
	@Schema(defaultValue = "Book category")
	private CategoryDTO category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getTotalExamplaries() {
		return totalExamplaries;
	}

	public void setTotalExamplaries(Integer totalExamplaries) {
		this.totalExamplaries = totalExamplaries;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	@Override
	public int compareTo(BookDTO o) {
		return title.compareToIgnoreCase(o.getTitle());
	}
	
	
}
