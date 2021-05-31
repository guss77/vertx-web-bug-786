package coil.geek.vertx.web.simple;

import java.util.stream.Stream;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

public class Books {

	Book[] SampleBooks = new Book[] { 
			new Book("To Say Nothing Of The Dog", new Author("Connie Willis")),
			new Book("The Mote In God's Eye", new Author("Larry Niven"), new Author("Jerry Pournelle")),
			new Book("Fallen Angels", new Author("Larry Niven"), new Author("Jerry Pournelle"),
					new Author("Michael Flynn"), new Author("James Baen")), 
	};

	public Handler<RoutingContext> list = r -> {
		okResponse(r,getBooks().encodePrettily());
	};

	public Handler<RoutingContext> fetchById = r -> {
		r.put("book", SampleBooks[Integer.parseInt(r.pathParam("id"))]);
		r.next();
	};

	public Handler<RoutingContext> display = r -> {
		okResponse(r, r.<Book>get("book").toJson().encodePrettily());
	};

	public Handler<RoutingContext> authors = r -> {
		okResponse(r, Stream.of(r.<Book>get("book").getAuthors())
				.map(Author::toJson)
				.collect(JsonArray::new, JsonArray::add, JsonArray::addAll).encodePrettily());
	};

	private void okResponse(RoutingContext r, String res) {
		r.response().setStatusCode(200).setStatusMessage("OK").putHeader("Content-Type", "application/json")
				.putHeader("Content-Length", "" + res.length()).send(res);
	}

	public JsonArray getBooks() {
		return Stream.of(SampleBooks).map(Book::toJson).collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}
}
