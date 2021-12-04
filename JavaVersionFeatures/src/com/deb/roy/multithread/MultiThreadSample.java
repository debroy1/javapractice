package com.deb.roy.multithread;

public class MultiThreadSample {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Running " + Thread.currentThread().getName() + " thread, P = " + Thread.currentThread().getPriority());
			}
		});
		thread.setName("sample");
		thread.setPriority(Thread.MAX_PRIORITY);
		
		System.out.println("Running " + Thread.currentThread().getName() + " thread, P = " + Thread.currentThread().getPriority());
		thread.start();
		Thread.sleep(100);
		System.out.println("Running " + Thread.currentThread().getName() + " thread, P = " + Thread.currentThread().getPriority());
	}
}
