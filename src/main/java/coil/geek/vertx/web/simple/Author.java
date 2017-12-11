package coil.geek.vertx.web.simple;

import io.vertx.core.json.JsonObject;

public class Author {

	private String name;
	
	public Author(String name) {
		this.name = name;
	}

	public JsonObject toJson() {
		return new JsonObject()
				.put("name", name);
	}
}
