package de.twenty11.skysail.client.cli.test;

import aQute.launcher.Launcher;

public class SkysailServerLauncher {

	private static Thread serverThread;
	private static ServerRunnable runnable;
	
	public static class ServerRunnable implements Runnable {
		
		private String[] args;
		private volatile boolean running = true;

		public ServerRunnable(String[] args) {
			this.args = args;
		}

		public void terminate() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				Launcher.main(args);
			}
		}
	}

	public static void start(String[] args) throws Exception {
		System.setProperty("launcher.properties",
				".\\resources\\launcher.properties");
		runnable = new ServerRunnable(new String[0]) ;
		serverThread = new Thread(runnable);
		serverThread.start();
		Thread.sleep(5000);
	}

	public static ServerRunnable getRunnable() {
		return runnable;
	}
	
	public static Thread getServerThread() {
		return serverThread;
	}
}
