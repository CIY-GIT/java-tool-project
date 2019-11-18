package cy.learn.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * @Title: ReentrantLockTest.java 
 * @Package cy.common.cy.tool.concurrent
 * @Description: TODO
 * @author hzchenya
 * @date 2017年9月7日 下午2:44:48 
 * @version TODO 
 */
public class ReentrantLockTest
{
	public static AtomicInteger cnt = new AtomicInteger(0);

	public static void main(String[] args)
	{
		for (int i = 0; i < 10; i++)
		{
			final int num = i;
			new Thread(new Runnable() {
				public void run()
				{
					try
					{
						test(num);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}

	}

	final static ReentrantLock lock = new ReentrantLock();
	public static void test(int num) throws InterruptedException
	{
		System.err.println(Thread.currentThread().getId() + " : " + num + " : " + lock);
		lock.lock();
		try
		{
			System.out.println("######" + cnt.incrementAndGet());
			Thread.currentThread().sleep(1000);
		}
		finally
		{
			System.out.println(Thread.currentThread().getId() + " be4");
			lock.unlock();
			System.out.println(Thread.currentThread().getId() + " aft");
		}
	}
	
	public static void test2(int num) throws InterruptedException
	{
		System.out.println(Thread.currentThread().getId() + " : " + num + " : " + cnt.incrementAndGet());
	}

}
