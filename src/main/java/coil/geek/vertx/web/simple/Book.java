package coil.geek.vertx.web.simple;

import java.util.stream.Stream;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Book {
	
	private String name;
	private Author[] authors;

	public Book(String name, Author ... authors) {
		this.name = name;
		this.authors = authors;
	}

	public JsonObject toJson() {
		return new JsonObject()
				.put("name", name)
				.put("authors", Stream.of(authors).map(Author::toJson)
						.collect(JsonArray::new, JsonArray::add, JsonArray::addAll));
	}

	public Author[] getAuthors() {
		return authors;
	}
}
