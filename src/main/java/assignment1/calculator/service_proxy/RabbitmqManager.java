package assignment1.calculator.service_proxy;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class RabbitmqManager {
	
	private RabbitMQClient rabbitMQClient;

	RabbitmqManager() {
	}
	
	RabbitmqManager(Vertx vertx) {
		
		if(rabbitMQClient == null) {
			RabbitMQOptions rabbitMQOptions = new RabbitMQOptions();
			rabbitMQOptions.setHost("68.183.80.248").setPort(5672).setUser("IUDXDemoUser").setPassword("IUDXDemoUser@123").setVirtualHost("/");
			rabbitMQClient =RabbitMQClient.create(vertx, rabbitMQOptions);
			rabbitMQClient.start(resultHandler -> { if(resultHandler.succeeded()) 
														{
															System.out.println("rabbitMQClient started now");
															if(rabbitMQClient.isConnected()) {
																System.out.println("rabbitMQClient is connected, now creating exchange & queue");
																declareExchange("umesh_exchange");
																declareQueue("umesh_queue");
																bindQueue2Exchange("umesh_queue", "umesh_exchange");
															}
														}else {
															System.out.println("rabbitMQClient start failed");
															  }
												  });

		}
		
		
	}
	
	
	public void declareExchange(String exchangeName) {
		rabbitMQClient.exchangeDeclare(exchangeName, "topic", true, false, resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("'" + exchangeName + "'"+ " [ topic ] declare successfully");
			}else {
				System.out.println("failed to declare exchange : "+exchangeName);
				resultHandler.cause().printStackTrace();
			}
		});
	}
	
	public void declareQueue(String queueName) {
		rabbitMQClient.queueDeclare(queueName, true, true, false, resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("'" + queueName + "'" +" declare successfully");
			}else {
				System.out.println("failed to declare queue : " + queueName);
				resultHandler.cause().printStackTrace();
			}
		});
	}
	
	public void bindQueue2Exchange(String queueName,String exchangeName) {
		rabbitMQClient.queueBind(queueName, exchangeName, "umesh.routingKey.calculator", resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println( "'" + queueName + " bound to" + "'" +  exchangeName + "'" + "successfully");
			}else {
				System.out.println("failed to bind queue to exchange");
				resultHandler.cause().printStackTrace();
			}
		});
	}
	
	public void publish2Rabbitmq(String exchangeName, String request) {
		JsonObject json=new JsonObject();
		json.put("body", request);
		
		System.out.println(json);
		rabbitMQClient.basicPublish(exchangeName, "umesh.routingKey.calculator", json, resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("message published to queue");
			}else {
				System.out.println("failed to publish message to queue");
				resultHandler.cause().printStackTrace();
			}
		});
	}
}
