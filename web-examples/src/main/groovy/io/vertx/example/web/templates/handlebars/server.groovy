import io.vertx.groovy.ext.web.templ.HandlebarsTemplateEngine
import io.vertx.groovy.ext.web.Router

// In order to use a template we first need to create an engine
def engine = HandlebarsTemplateEngine.create()

// To simplify the development of the web components we use a Router to route all HTTP requests
// to organize our code in a reusable way.
def router = Router.router(vertx)

// Entry point to the application, this will render a custom template.
router.get().handler({ ctx ->
  // we define a hardcoded title for our application
  ctx.put("name", "Vert.x Web")

  // and now delegate to the engine to render it.
  engine.render(ctx, "templates/index.hbs", { res ->
    if (res.succeeded()) {
      ctx.response().putHeader(io.vertx.core.http.HttpHeaders.CONTENT_TYPE, "text/html").end(res.result())
    } else {
      ctx.fail(res.cause())
    }
  })
})

// start a HTTP web server on port 8080
vertx.createHttpServer().requestHandler(router.&accept).listen(8080)
