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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import si.matjazcerkvenik.dtools.tools.localhost.LocalhostInfo;

@XmlRootElement
public class SnmpSimulator {
	
	private List<SnmpAgent> snmpAgentsList;

	public List<SnmpAgent> getSnmpAgentsList() {
		return snmpAgentsList;
	}

	@XmlElement(name="snmpAgent")
	public void setSnmpAgentsList(List<SnmpAgent> snmpAgentsList) {
		this.snmpAgentsList = snmpAgentsList;
	}
	
	public void addSnmpAgent(SnmpAgent a) {
		snmpAgentsList.add(a);
	}
	
	public void removeSnmpAgent(SnmpAgent a) {
		snmpAgentsList.remove(a);
	}
	
	public void createDefaultAgent() {
		if (snmpAgentsList == null) {
			snmpAgentsList = new ArrayList<SnmpAgent>();
		}
		SnmpAgent a = new SnmpAgent("DefaultAgent0", LocalhostInfo.getLocalIpAddress(), 6161);
		snmpAgentsList.add(a);
	}
	
}