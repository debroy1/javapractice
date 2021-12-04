package com.deb.roy.multithread;

public class MultiThreadSample2 {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Running " + Thread.currentThread().getName() + " thread, P = " + Thread.currentThread().getPriority());
				throw new RuntimeException("Intentional Exception");
			}
		});
		thread.setName("sample");
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("Encountered critical exception while running " + Thread.currentThread().getName() + " thread");
				System.err.println("Exception: " + e.getMessage());
			}
		});
		
		System.out.println("Running " + Thread.currentThread().getName() + " thread, P = " + Thread.currentThread().getPriority());
		thread.start();
		Thread.sleep(100);
		System.out.println("Running " + Thread.currentThread().getName() + " thread, P = " + Thread.currentThread().getPriority());
	}
}
