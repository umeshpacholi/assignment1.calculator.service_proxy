package assignment1.calculator.service_proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class APIServerVerticle extends AbstractVerticle {
	private HttpServer server;
	private CalculatorService calculatorService;
	private RabbitmqManager rabbitmqManager ;

	@Override
	public void start() {
		
		// RabbitmqManager initialized in advance for exchange and queue declaration and Rabbitmq client start
		rabbitmqManager = new RabbitmqManager(vertx) ;
		
		Router router = Router.router(vertx);
		router.get("/calculator/addition").handler(this::addition);
		router.get("/calculator/subtraction").handler(this::subtraction);
		router.get("/calculator/multiplication").handler(this::multiplication);
		router.get("/calculator/division").handler(this::division);

		server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(8080);

		//service CalculatorService building using ServiceProxyBuilder 
		ServiceProxyBuilder builder = new ServiceProxyBuilder(vertx).setAddress("calculator.address");
		calculatorService = builder.build(CalculatorService.class);

		System.out.println("APIServerVerticle started successfully");
	}
	

	public void addition(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.addition(Integer.parseInt(json.getString("n1")),
				Integer.parseInt(json.getString("n2")), handler -> {
					if (handler.succeeded()) {
						System.out.println("Calculator addition service successfully called");
						rabbitmqManager.publish2Rabbitmq("umesh_exchange", "successfully called for Addition: n1="+json.getString("n1")+" n2="+json.getString("n2")+" Result :"+handler.result().toString());
						response.putHeader("content-type", "application/json").setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
								.withStatusCode(200).build().toJson()
								.put("result", handler.result().toString())
								.toString());
					}
					else {
							System.out.println("Failed to invoke Calculator addition service");
					}
				});
	}

	public void subtraction(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.subtraction(Integer.parseInt(json.getString("n1")),
				Integer.parseInt(json.getString("n2")), handler -> {
					if (handler.succeeded()) {
						System.out.println("Calculator subtraction service successfully called");
						rabbitmqManager.publish2Rabbitmq("umesh_exchange", "successfully called for subtraction: n1="+json.getString("n1")+" n2="+json.getString("n2")+" Result :"+handler.result().toString());
						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {
						System.out.println("Failed to invoke subtraction addition service");
					}
				});
	}

	public void multiplication(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.multiplication(Integer.parseInt(json.getString("n1")),
				Integer.parseInt(json.getString("n2")), handler -> {
					if (handler.succeeded()) {
						System.out.println("Calculator multiplication service successfully called");
						rabbitmqManager.publish2Rabbitmq("umesh_exchange", "successfully called for multiplication: n1="+json.getString("n1")+" n2="+json.getString("n2")+" Result :"+handler.result().toString());
						
						
						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {

					}
				});
	}

	public void division(RoutingContext routingContext) {
		System.out.println("SP:divide called");
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.division(Integer.parseInt(json.getString("n1")),
				Integer.parseInt(json.getString("n2")), handler -> {
					if (handler.succeeded()) {
						System.out.println("Calculator division service successfully called");
						rabbitmqManager.publish2Rabbitmq("umesh_exchange", "successfully called for division: n1="+json.getString("n1")+" n2="+json.getString("n2")+" Result :"+handler.result().toString());
						
						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {

					}
				});
	}

	private JsonObject toJson(RoutingContext routingContext) {
		MultiMap params = routingContext.queryParams();
		JsonObject json = new JsonObject();
		params.forEach(entry -> {
			json.put(entry.getKey(), entry.getValue());
		});
		return json;
	}

	
}
