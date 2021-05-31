package coil.geek.vertx.web.simple;

import io.vertx.core.*;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;

public class App extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startFuture) throws Exception {
		try {
			vertx.createHttpServer(new HttpServerOptions())
					.requestHandler(getRouter(vertx))
					.listen(config().getInteger("port", 8080), r -> {
						if (r.failed())
							startFuture.fail(r.cause());
						else
							startFuture.complete();
					});
		} catch (Exception e) {
			startFuture.fail(e);
		}
	}

	private Handler<HttpServerRequest> getRouter(Vertx vertx) {
		Router router = Router.router(vertx);
		
		Books books = new Books();
		
		router.get("/books").handler(books.list);
		router.get("/books/:id").handler(books.fetchById);
		router.get("/books/:id").handler(books.display);
		router.get("/books/:id/*").handler(books.fetchById);
		router.get("/books/:id/authors").handler(books.authors);
		
		return router;
	}

}
