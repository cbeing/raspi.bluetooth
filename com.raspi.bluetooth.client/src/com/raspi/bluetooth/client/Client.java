package com.raspi.bluetooth.client;



import java.io.*;

import java.util.Vector;

import javax.microedition.io.*;
import javax.bluetooth.*;

public class Client implements DiscoveryListener{

	//object used for waiting
    private static Object lock=new Object();
   
    //vector containing the devices discovered
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
      /*
        System.out.println("Starting device inquiry...");
        agent.startInquiry(DiscoveryAgent.GIAC, client);
       
        try {
            synchronized(lock){
                lock.wait();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
       
       
        System.out.println("Device Inquiry Completed. ");
       
        //print all devices in vecDevices
        int deviceCount=vecDevices.size();
       
        if(deviceCount <= 0){
            System.out.println("No Devices Found .");
            System.exit(0);
        }
        else{
            //print bluetooth device addresses and names in the format [ No. address (name) ]
            System.out.println("Bluetooth Devices: ");
            for (int i = 0; i <deviceCount; i++) {
                RemoteDevice remoteDevice=(RemoteDevice)vecDevices.elementAt(i);
                
                System.out.println((i+1)+". "+remoteDevice.getBluetoothAddress()+" ("+remoteDevice.getFriendlyName(true)+")");
            }
        }
       
        System.out.print("Choose Device index: ");
        BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
       
        String chosenIndex=bReader.readLine();
        int index=Integer.parseInt(chosenIndex.trim());
       
        //check for spp service
        System.out.println("RemoteDevice:"+vecDevices.elementAt(index-1));
        RemoteDevice remoteDevice=(RemoteDevice)vecDevices.elementAt(index-1);
        
        UUID[] uuidSet = new UUID[1];
        uuidSet[0]=new UUID("106d2780b38811e3a5e20800200c9a66",false);
       
        System.out.println("\nSearching for service...");
        agent.searchServices(null,uuidSet,remoteDevice,client);
      */
       
       
        UUID[] uuidSet = new UUID[1];
        uuidSet[0]=new UUID("106d2780b38811e3a5e20800200c9a66",false);
        connectionURL=agent.selectService(uuidSet[0], ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
        System.out.println("DONE:"+connectionURL);
       
         
      /*  try {
            synchronized(lock){
                lock.wait();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
       */
        if(connectionURL==null){
            System.out.println("Device does not support Simple client Service.");
            System.exit(0);
        }
       
        //connect to the server and send a line of text
        System.out.println("streamconnection");
        StreamConnection streamConnection=(StreamConnection)Connector.open(connectionURL);
       
        //send string
        System.out.println("send string");
        OutputStream outStream=streamConnection.openOutputStream();
        PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
       
        //xml test 
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

      
        
        //xml
       
       // pWriter.write("Test String from Client\r\n");
        br.close();
        pWriter.flush();
        pWriter.close();
        outStream.close();
        streamConnection.close();
       
        //read response
    /*    InputStream inStream=streamConnection.openInputStream();
        BufferedReader bReader2=new BufferedReader(new InputStreamReader(inStream));
        String lineRead=bReader2.readLine();
        System.out.println(lineRead);
      */ 
               
    }//main
    
   
    
	
	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        //add the device to the vector
        if(!vecDevices.contains(btDevice)){
            vecDevices.addElement(btDevice);
        }
		
	}

	@Override
	public void inquiryCompleted(int discType) {
		
		 /* synchronized(lock){
	            lock.notify();
		  }
		*/
	}

	@Override
	public void serviceSearchCompleted(int transID, int respCode) {
        /*synchronized(lock){
            lock.notify();
        }*/
	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if(servRecord!=null && servRecord.length>0){
            connectionURL=servRecord[0].getConnectionURL(0,false);
            System.out.println("verif:"+connectionURL);
        }
        /*synchronized(lock){
            lock.notify();
        }
        */
		
	}
	
	
	
	

	              

}
