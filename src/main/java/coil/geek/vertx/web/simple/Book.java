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
		JsonArray auths = Stream.of(authors).map(Author::toJson)
				.collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
		return new JsonObject()
				.put("name", name)
				.put("authors", auths);
	}

	public Author[] getAuthors() {
		return authors;
	}
}
