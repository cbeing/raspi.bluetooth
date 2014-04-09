package com.raspi.bluetooth.server;

public class MyThread extends Thread {
	  
	  Server echoserver;
	  
	  public void run() {
		  echoserver = new Server(); 
		  
	  }

	  public void stopThread() throws Exception {
		  echoserver.server.close();
		  
	  }
	} 