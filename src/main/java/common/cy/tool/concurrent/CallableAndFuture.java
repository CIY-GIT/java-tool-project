package cy.learn.concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/** 
 * @Title: FutureTest.java 
 * @Package cy.common.cy.tool.concurrent
 * @Description: TODO
 * @author hzchenya
 * @date 2016年5月20日 上午9:28:28 
 * @version TODO 
 */
public class CallableAndFuture
{
	public static void main(String[] args)
	{
		System.out.println(Thread.currentThread().getId() + ": main thread");
		Callable<Integer> callable = new Callable<Integer>() {
			public Integer call() throws Exception
			{
				int num = new Random().nextInt(100);
				System.out.println(Thread.currentThread().getId() + ": call back - " + num);
				return num;
			}
		};
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		new Thread(future).start();
		try
		{
			while(!future.isDone())
			{
				Thread.sleep(100);// 可能做一些事情
			}
			System.out.println(future.get() + " : " +future.isDone());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
