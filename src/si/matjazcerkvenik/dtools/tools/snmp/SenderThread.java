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

package si.matjazcerkvenik.dtools.tools.snmp;

import java.util.List;

import si.matjazcerkvenik.dtools.web.SnmpTrapSenderBean;
import si.matjazcerkvenik.dtools.xml.DAO;
import si.matjazcerkvenik.dtools.xml.SnmpTrap;

public class SenderThread extends Thread {
	
	private SnmpTrapSenderBean senderBean;
	
	private boolean isRunning = true;
	
	
	
	public SenderThread(SnmpTrapSenderBean senderBean) {
		this.senderBean = senderBean;
	}

	public void setSenderBean(SnmpTrapSenderBean senderBean) {
		this.senderBean = senderBean;
	}

	@Override
	public void run() {
		
		List<SnmpTrap> traps = DAO.getInstance().loadSnmpTraps().getTraps();
		int index = 0;
		
		while (isRunning) {
			
			String ip = senderBean.getDestinationIp();
			int port = senderBean.getDestinationPort();
			senderBean.getTrapSender().sendTrap(ip, port, traps.get(index));
			
			if (index == traps.size() - 1) {
				index = 0;
			} else {
				index++;
			}
			
			try {
				sleep((int) senderBean.getSendInterval());
			} catch (InterruptedException e) {
			}
			
		}
		
	}
	
	public void startThread() {
		
		isRunning = true;
		start();
		
	}
	
	public void stopThread() {
		
		isRunning = false;
		
	}
	
}
