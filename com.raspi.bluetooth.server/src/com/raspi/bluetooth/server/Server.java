package com.raspi.bluetooth.server;

import java.io.*;

import javax.bluetooth.*;
import javax.microedition.io.*;

public class Server {

	 public final UUID uuid = new UUID("106d2780b38811e3a5e20800200c9a68",false);                    
		
	    public final String name = "Echo Server";                       
	    public final String url  =  "btspp://localhost:" + uuid         
	                                + ";name=" + name 
	                                + ";authenticate=false;encrypt=false;";
	    LocalDevice local = null;
	    StreamConnectionNotifier server = null;
	    StreamConnection conn = null;
	    FileWriter fw=null;
	    BufferedWriter bw=null;
	    DataInputStream din=null;
	    BufferedReader bReader=null;
	    public Server() {
	        try {
	            System.out.println("Setting device to be discoverable...");
	            local = LocalDevice.getLocalDevice();
	            local.setDiscoverable(DiscoveryAgent.GIAC);
	            System.out.println("Start advertising service...");
	            server = (StreamConnectionNotifier)Connector.open(url);
	            while(true){
	            System.out.println("Waiting for incoming connection...");
	            conn = server.acceptAndOpen();
	           
	            new Thread(){
	            	public void run() {
						
					
	            System.out.println("Client Connected...");
	            try {
					din   = new DataInputStream(conn.openInputStream());
				
	          
	            File file = new File("/home/pi/file.json");
	            
	            
	            if (!file.exists()) {
					file.createNewFile();
				}else{
					file.delete();
					file.createNewFile();
				}
	            fw = new FileWriter(file.getAbsoluteFile());
				bw = new BufferedWriter(fw);
	            
	            
	            String cmd="";
	            bReader=new BufferedReader(new InputStreamReader(din));
	           while(( cmd=bReader.readLine())!=null){
	        	   bw.write(cmd+"\n");
	            System.out.println(cmd);
	           							}
	           bw.close();
	            } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	}
	            }.start();
	           // execution du web service ave argument chemain du fichier : exec
	            }
	        } catch (Exception  e) {System.out.println("Exception Occured: " + e.toString());}



}
	    }