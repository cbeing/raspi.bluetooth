package com.raspi.bluetooth.server;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


import com.raspi.bluetooth.server.MyThread;

//import java.io.*;

//import javax.bluetooth.*;
//import javax.microedition.io.*;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private MyThread myThread;

	static BundleContext getContext() {
		return context;
	}


	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting server bundle test");
		myThread= new MyThread();
		myThread.start();
		
		
	}

	
	
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopping server bundle test");
	    myThread.stopThread();
	    myThread.join();
	    context.getBundle().stop();
	    Activator.context = null;
	    
	}

}
