/* 
 * Copyright (C) 2015 Matjaz Cerkvenik
 * 
 * DTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DTools. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package si.matjazcerkvenik.dtools.test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class TestConnection {

	public static void main(String[] args) {
//		testFtp("centos6");
//		testFtp2();
//		availablePort(8080);
		portIsOpen("centos6", 7, 10000);
		portIsOpen("centos6", 21, 10000);
		portIsOpen("centos6", 22, 10000);
		portIsOpen("centos6", 23, 10000);
		portIsOpen("centos6", 161, 10000);
		portIsOpen("centos6", 8080, 10000);
	}

	public static void testFtp(String hostname) {

		try {
			Socket s = new Socket(hostname, 21);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Ok, but requires username and password
	 */
	public static void testFtp2() {
		try {
			URL url = new URL("ftp://ftpuser:ftpuser@centos6");
			URLConnection yc = url.openConnection();
			yc.connect();
			System.out.println("testFtp2: connected");
		} catch (UnknownHostException e) {
			System.err.println("UnknownHostException");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method checks local ports only. If avalable, no service is 
	 * running on that, if BindException is thrown a service is running.
	 * For ports lower than 1024 PermissionDenied exception is thrown.
	 * @param port
	 * @return
	 */
	public static boolean availablePort(int port) {
	    
	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        System.out.println("availablePort: " + port);
	        return true;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	                /* should not be thrown */
	            }
	        }
	    }

	    return false;
	}
	
	/**
	 * This is kinda efficient one, but for TCP ports only.
	 * @param ip
	 * @param port
	 * @param timeout
	 * @return
	 */
	public static boolean portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            System.out.println("portIsOpen: " + port);
            socket.close();
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;
    }

}
