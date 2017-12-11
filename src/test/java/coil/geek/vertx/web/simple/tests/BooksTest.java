package coil.geek.vertx.web.simple.tests;

import java.util.Random;

import org.junit.*;
import org.junit.runner.RunWith;

import coil.geek.vertx.web.simple.App;
import coil.geek.vertx.web.simple.Books;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class BooksTest {

	@ClassRule
	public static RunTestOnContext rule = new RunTestOnContext();
	@Rule
	public Timeout timeoutRule = Timeout.seconds(3600);
	
	protected final Integer port = new Random().nextInt(30000)+10000;
	
	Books verifyBooks = new Books();

	protected HttpClient getClient() {
		return rule.vertx().createHttpClient(new HttpClientOptions().setIdleTimeout(0));
	}

	@Before
	public void deploy(TestContext context) {
		DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("port", port));
		rule.vertx().deployVerticle(new App(), options, context.asyncAssertSuccess());
	}

	@Test
	public void testBookList(TestContext context) {
		Async f = context.async();
		getClient().get(port, "localhost", "/books")
		.exceptionHandler(context::fail)
		.handler(compareBodyHandler(verifyBooks.getBooks().encodePrettily(), context, f)).end();
	}

	@Test
	public void testBookListTrailingSlash(TestContext context) {
		Async f = context.async();
		getClient().get(port, "localhost", "/books/")
		.exceptionHandler(context::fail)
		.handler(compareBodyHandler(verifyBooks.getBooks().encodePrettily(), context, f)).end();
	}

	@Test
	public void testGetBook(TestContext context) {
		Async f = context.async();
		getClient().get(port, "localhost", "/books/1")
		.exceptionHandler(context::fail)
		.handler(compareBodyHandler(verifyBooks.getBooks().getJsonObject(1).encodePrettily(), context, f)).end();
	}

	@Test
	public void testGetAuthors(TestContext context) {
		Async f = context.async();
		getClient().get(port, "localhost", "/books/2/authors")
		.exceptionHandler(context::fail)
		.handler(compareBodyHandler(verifyBooks.getBooks().getJsonObject(2).getJsonArray("authors").encodePrettily(), context, f)).end();
	}

	@Test
	public void testGetAuthorsTrailingSlash(TestContext context) {
		Async f = context.async();
		getClient().get(port, "localhost", "/books/2/authors/")
		.exceptionHandler(context::fail)
		.handler(compareBodyHandler(verifyBooks.getBooks().getJsonObject(2).getJsonArray("authors").encodePrettily(), context, f)).end();
	}

	private Handler<HttpClientResponse> compareBodyHandler(String message, TestContext context, Async f) {
		return r -> {
			context.assertEquals(200, r.statusCode(), "Failed to call consumes test '" + message + "'");
			r.exceptionHandler(context::fail).bodyHandler(body -> {
				context.assertEquals(message, body.toString());
				f.complete();
			});
		};
	}
}
