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

package si.matjazcerkvenik.dtools.tools.ftp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FtpClients {
	
	private List<FtpClient> ftpClientList;

	public List<FtpClient> getFtpClientList() {
		return ftpClientList;
	}

	@XmlElement(name="ftpClient")
	public void setFtpClientList(List<FtpClient> ftpClient) {
		this.ftpClientList = ftpClient;
	}
	
	public void addFtpClientAction(FtpClient c) {
		ftpClientList.add(c);
	}
	
	public void deleteFtpClient(FtpClient c) {
		ftpClientList.remove(c);
	}
	
	public List<FtpClient> getCustomFtpClientsList(String hostname) {
		
		List<FtpClient> list = new ArrayList<FtpClient>();
		
		for (int i = 0; i < getFtpClientList().size(); i++) {
			if (getFtpClientList().get(i).getHostname().equals(hostname)) {
				list.add(getFtpClientList().get(i));
			}
		}
		
		return list;
		
	}
	
}
