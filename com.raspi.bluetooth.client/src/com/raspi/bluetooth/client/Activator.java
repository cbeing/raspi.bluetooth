package com.raspi.bluetooth.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


import javax.microedition.io.*;
import javax.bluetooth.*;
import java.io.*;

public class Activator implements BundleActivator {

	private static BundleContext context;

	Client client;
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		client= new Client();
		client.connect();
		context.getBundle().stop();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
