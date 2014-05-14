package com.raspi.bluetooth.client;



import java.io.*;

import java.util.Vector;

import javax.microedition.io.*;
import javax.bluetooth.*;

public class Client implements DiscoveryListener{

    private static Object lock=new Object();
   

    private static Vector vecDevices=new Vector();
   
    private static String connectionURL=null;
    
    
    
    
   public void connect() throws IOException {
        
        Client client=new Client();
       
        //display local device address and name
        LocalDevice localDevice = LocalDevice.getLocalDevice();
        System.out.println("Address: "+localDevice.getBluetoothAddress());
        System.out.println("Name: "+localDevice.getFriendlyName());
       
        //find devices
       DiscoveryAgent agent = localDevice.getDiscoveryAgent();
     
       
       
        UUID[] uuidSet = new UUID[1];
        uuidSet[0]=new UUID("106d2780b38811e3a5e20800200c9a68",false);
        connectionURL=agent.selectService(uuidSet[0], ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
        System.out.println("DONE:"+connectionURL);
       
         
        if(connectionURL==null){
            System.out.println("Device does not support Simple client Service.");
            System.exit(0);
        }
       
        
        System.out.println("streamconnection");
        StreamConnection streamConnection=(StreamConnection)Connector.open(connectionURL);
        if(streamConnection==null){System.out.println("stramconnection error!!!");}
       
        
        System.out.println("send string");
        OutputStream outStream=streamConnection.openOutputStream();
        PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
       
       
        System.out.println("xml test");
       BufferedReader br=null;
        try {
        	 
			String sCurrentLine;
 
		br = new BufferedReader(new FileReader("C:\\note.xml"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				
				System.out.println(sCurrentLine);
				pWriter.write(sCurrentLine+"\n");
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

      
        
         br.close();
        pWriter.flush();
        pWriter.close();
        outStream.close();
        streamConnection.close();
       
         
               
    }//main
    
   
    
	
	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        if(!vecDevices.contains(btDevice)){
            vecDevices.addElement(btDevice);
        }
		
	}

	@Override
	public void inquiryCompleted(int discType) {
		
		
	}

	@Override
	public void serviceSearchCompleted(int transID, int respCode) {
        
	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if(servRecord!=null && servRecord.length>0){
            connectionURL=servRecord[0].getConnectionURL(0,false);
            System.out.println("verif:"+connectionURL);
        }
      
		
	}
	
	
	
	

	              

}
