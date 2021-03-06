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

package si.matjazcerkvenik.dtools.tools.ping;

import java.util.Date;

import si.matjazcerkvenik.dtools.xml.Node;
import si.matjazcerkvenik.dtools.xml.Service;

public class AutoDiscoverWorker implements Runnable {
	
	private long id;
	private String ip;
	private AutoDiscoverThread adtp;

	public AutoDiscoverWorker(long id, String ip, AutoDiscoverThread adtp) {
		this.id = id;
		this.ip = ip;
		this.adtp = adtp;
	}
	
	@Override
	public void run() {
		
		IcmpPing p = new IcmpPing();
		PingStatus status = p.ping(ip);
		
		if (status.getErrorCode() == PingStatus.EC_OK) {
			Node n = new Node();
			n.setHostname(ip);
			n.setName("Node #" + id);
			n.setDescription("AutoDiscovered @ " + new Date());
			
			Service s = new Service();
			s.setName("ICMP");
			s.setMonitoringClass("ICMP_PING");
			n.addService(s);
			n.init();
			
			adtp.storeNode(n);
		}
		adtp.decreaseCount();
		
	}
	
	@Override
	public String toString() {
		return this.ip;
	}
	
}
