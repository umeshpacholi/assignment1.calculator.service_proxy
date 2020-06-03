package assignment1.calculator.service_proxy;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class CalculatorServiceImpl implements CalculatorService {

	@Override
	public void addition(int n1,int n2, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("calculator's addition service called");
		System.out.println("n1 : " + n1 +" n2: " + n2);
		int result = n1 + n2;
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void subtraction(int n1, int n2, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("calculator's subtraction service called");
		System.out.println("n1 : " + n1 +" n2: " + n2);
		int result = n1 - n2;
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void multiplication(int n1, int n2, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("calculator's multiplication service called");
		System.out.println("n1 : " + n1 +" n2: " + n2);
		int result = n1*n2;
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void division(int n1, int n2, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("calculator's division service called");
		System.out.println("n1 : " + n1 + " n2: " + n2);
		int result = n1/n2;
		resultHandler.handle(Future.succeededFuture(result));
	}
	
}
