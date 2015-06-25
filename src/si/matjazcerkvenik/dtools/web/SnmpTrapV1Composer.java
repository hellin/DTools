package si.matjazcerkvenik.dtools.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import si.matjazcerkvenik.dtools.context.DToolsContext;
import si.matjazcerkvenik.dtools.tools.localhost.LocalhostInfo;
import si.matjazcerkvenik.dtools.xml.DAO;
import si.matjazcerkvenik.dtools.xml.SnmpTrap;
import si.matjazcerkvenik.dtools.xml.VarBind;

@ManagedBean
@ViewScoped
public class SnmpTrapV1Composer {

	private String trapName;
	private String community = "public";
	private String sourceIp = LocalhostInfo.getLocalIpAddress();
	// Generic trap types: coldStart trap (0), warmStart trap (1),
	// linkDown trap(2), linkUp trap (3), authenticationFailure trap (4),
	// egpNeighborLoss trap (5), enterpriseSpecific trap (6)
	private String genericTrap = "6";
	private String specificTrap = "0";
	private String enterpriseOid = "1.";
	private String timestamp = "0";
	private List<VarBind> varbinds;
	private boolean modifyMode = false;
	
	private SnmpTrap originalTrap;
	
	@PostConstruct
	public void init() {
		Map<String, Object> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SnmpTrap trap = (SnmpTrap) requestParameterMap.get("trap");
		if (trap != null) {
			modifyMode = true;
			originalTrap = trap;
			trapName = trap.getTrapName();
			community = trap.getCommunity();
			genericTrap = trap.getGenericTrap();
			specificTrap = trap.getSpecificTrap();
			enterpriseOid = trap.getEnterpriseOid();
			sourceIp = trap.getSourceIp();
			timestamp = trap.getTimestamp();
			varbinds = trap.cloneVarbinds(); // always use copy of varbinds (in case they are changed)
		}
	}

	public String getTrapName() {
		return trapName;
	}

	public void setTrapName(String trapName) {
		this.trapName = trapName;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getGenericTrap() {
		return genericTrap;
	}

	public void setGenericTrap(String genericTrap) {
		this.genericTrap = genericTrap;
	}

	public String getSpecificTrap() {
		return specificTrap;
	}

	public void setSpecificTrap(String specificTrap) {
		this.specificTrap = specificTrap;
	}

	public String getEnterpriseOid() {
		return enterpriseOid;
	}

	public void setEnterpriseOid(String enterpriseOid) {
		this.enterpriseOid = enterpriseOid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<VarBind> getVarbinds() {
		if (varbinds == null) {
			varbinds = new ArrayList<VarBind>();
			varbinds.add(new VarBind("customVarBind", "1.2.3.4", VarBind.TYPE_OCTET_STRING, "abcd"));
		}
		return varbinds;
	}

	public void setVarbinds(List<VarBind> varbinds) {
		this.varbinds = varbinds;
	}
	
	public void addNewOid() {
		varbinds.add(new VarBind("oidName", "1.", VarBind.TYPE_OCTET_STRING, "value"));
	}
	
	public void removeOid(VarBind vb) {
		varbinds.remove(vb);
	}
	
	/**
	 * Save trap data.
	 * @return url to snmpAgent.xhtml
	 */
	public String saveTrap() {
		
		if (modifyMode) {
			SnmpTrap trap = findTrap(trapName);
			if (trap == null) {
				// trap not found because trapName is changed in modify mode
				trap = originalTrap.makeClone(); // clone original trap with deep copy of varbinds
				trap.setTrapName(trapName);
				trap = populateTrap(trap);
				DAO.getInstance().addSnmpTrap(trap);
				Growl.addGrowlMessage("Trap " + trapName + " saved", FacesMessage.SEVERITY_INFO);
			} else {
				trap = populateTrap(trap);
				DAO.getInstance().saveSnmpTraps();
				Growl.addGrowlMessage("Trap " + trapName + " modified", FacesMessage.SEVERITY_INFO);
			}
		} else {
			SnmpTrap trap = new SnmpTrap();
			trap.setTrapName(trapName);
			trap = populateTrap(trap);
			DAO.getInstance().addSnmpTrap(trap);
			Growl.addGrowlMessage("Trap " + trapName + " saved", FacesMessage.SEVERITY_INFO);
		}
		
		resetTrap();
		modifyMode = false;
		
		return "snmpAgent";
	}
	
	/**
	 * Find trap according to trap name. Return null if trap not found.
	 * @param trapName
	 * @return trap
	 */
	private SnmpTrap findTrap(String trapName) {
		List<SnmpTrap> list = DAO.getInstance().loadSnmpTraps().getTraps();
		for (SnmpTrap trap : list) {
			if (trap.getTrapName().equals(trapName)) {
				return trap;
			}
		}
		return null;
	}
	
	/**
	 * Fill the values from GUI into trap (except trapName!)
	 * @param trap
	 * @return trap
	 */
	private SnmpTrap populateTrap(SnmpTrap trap) {
		trap.setVersion("v1");
		trap.setCommunity(community);
		trap.setGenericTrap(genericTrap);
		trap.setSpecificTrap(specificTrap);
		trap.setEnterpriseOid(enterpriseOid);
		trap.setSourceIp(sourceIp);
		trap.setTimestamp(timestamp);
		trap.setVarbind(varbinds);
		return trap;
	}
	
	/**
	 * Reset trap values to default values and remove trap object from session.
	 */
	public void resetTrap() {
		trapName = null;
		community = "public";
		sourceIp = LocalhostInfo.getLocalIpAddress();
		genericTrap = "6";
		specificTrap = "0";
		enterpriseOid = "1.";
		timestamp = "" + DToolsContext.getSysUpTime()/1000;
		varbinds = null;
		originalTrap = null;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("trap");
	}

}