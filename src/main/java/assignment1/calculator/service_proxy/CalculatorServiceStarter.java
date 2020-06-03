package assignment1.calculator.service_proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

public class CalculatorServiceStarter extends AbstractVerticle{
	
	public static void main(String[] args) {
		
		Launcher.executeCommand("run", CalculatorServiceStarter.class.getName());
	}
	
	@Override
	public void start(Future<Void> startFuture)throws Exception{
		
		vertx.deployVerticle(APIServerVerticle.class.getName(),  res -> {
																			if(res.succeeded()){
																				System.out.println("APIServerVerticle deployed successfully");
																			}
																			else{
																				System.out.println("APIServerVerticle deployment failed. failed cause :" + res.cause());
																			}
																		});
		vertx.deployVerticle(CalculatorServiceVerticle.class.getName(),  res -> {
																				if(res.succeeded()){
																					System.out.println("CalculatorServiceVerticle deployed successfully");
																				}
																				else{
																					System.out.println("CalculatorServiceVerticle deployment failed. failed cause :" + res.cause());
																				}
																			});
	}

}
