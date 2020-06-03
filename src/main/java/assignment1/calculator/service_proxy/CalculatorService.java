package assignment1.calculator.service_proxy;

import assignment.calculator.service_proxy.CalculatorServiceVertxEBProxy;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@VertxGen
@ProxyGen
public interface CalculatorService {


	void addition(int n1,int n2,Handler<AsyncResult<Integer>> resultHandler);
	void subtraction(int n1,int n2,Handler<AsyncResult<Integer>> resultHandler);
	void multiplication(int n1,int n2,Handler<AsyncResult<Integer>> resultHandler);
	void division(int n1,int n2,Handler<AsyncResult<Integer>> resultHandler);
	
	@Fluent
	static CalculatorService create(Vertx vertx) {
		return new CalculatorServiceImpl();
	}
	//create proxy
	@Fluent
	static CalculatorService createProxy(Vertx vertx,String address) {
		return new CalculatorServiceVertxEBProxy(vertx,address);
	}
}
