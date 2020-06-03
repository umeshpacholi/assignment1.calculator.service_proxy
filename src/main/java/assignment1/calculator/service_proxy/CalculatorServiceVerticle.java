package assignment1.calculator.service_proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class CalculatorServiceVerticle extends AbstractVerticle{

	
	@Override
	public void start() {
		CalculatorService calculatorService = new CalculatorServiceImpl();
		// Register the handler on event bus with address (this address will be used to send messages to this verticle proxy.)
		new ServiceBinder(vertx).setAddress("calculator.address").register(CalculatorService.class, calculatorService);
		System.out.println("CalculatorServiceVerticle started successfully");
	}

}
