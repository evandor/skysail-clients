package de.twenty11.skysail.client.dbviewer.cli;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;

import org.clamshellcli.api.Context;
import org.clamshellcli.core.ShellException;

public final class Management {
	public static final String VALUE_LOCALHOST = "localhost";
	public static final String KEY_ARGS_HOST = "host";
	public static final String KEY_ARGS_PID = "pid";
	public static final String KEY_VMINFO_MAP = "key.vminfo.map";
	public static final String KEY_JMX_MBEANSERVER = "key.jmx.mbServer";
	public static final String KEY_JMX_CONNECTOR = "key.jmx.connector";
	public static final String KEY_JMX_URL = "key.jmx.url";
	public static final String KEY_MBEANS_MAP = "key.mbeans.map";
	public static final String KEY_DEFAULT_MBEANS = "key.default.mbeans";

	protected static final String VALUE_FILE_SEP = File.separator;
	protected static final String VALUE_AGENT_JAR = "management-agent.jar";
	protected static final String VALUE_AGENT_INIT_PROP = "com.sun.management.jmxremote";
	protected static final String VALUE_AGENT_CONNECTOR_PROP = "com.sun.management.jmxremote.localConnectorAddress";

	private Management() {
	}

	/**
	 * Returns the host address from the passed argument map.
	 * 
	 * @param argsMap
	 *            a Map<String,String> for the argument values
	 * @return the host address if in argument or "localhost" if none.
	 */
	public static String getHostFromArgs(Map<String, Object> argsMap) {
		return (argsMap != null && argsMap.get(KEY_ARGS_HOST) != null) ? (String) argsMap
				.get(KEY_ARGS_HOST) : "localhost";
	}

	/**
	 * Returns HostIdentifier from the string form of hostName.
	 * 
	 * @param hostName
	 * @return HostIdentifier
	 */
//	public static HostIdentifier getHostIdentifier(String hostName) {
//		HostIdentifier hostIdentifier = null;
//		try {
//			hostIdentifier = new HostIdentifier((hostName != null) ? hostName
//					: "localhost");
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//		return hostIdentifier;
//	}

	/**
	 * Returns a MonitoredVm instance from the provided MonitoredHost and the
	 * jvm process id.
	 * 
	 * @param mHost
	 *            MonitoredHost instance
	 * @param jvmId
	 *            jvm process id.
	 * @return MonitoredVm
	 * @throws Exception
	 *             if there is a problem.
	 */
//	public static MonitoredVm getMonitoredVm(MonitoredHost mHost, Integer jvmId)
//			throws Exception {
//		String vmUri = "//" + jvmId + "?mode=r";
//		VmIdentifier vmId = new VmIdentifier(vmUri);
//		return mHost.getMonitoredVm(vmId, 0);
//	}

	/**
	 * Creates a Map<Integer,Management.LocalJVMInfo> map (VmMap) with
	 * information from specified host. as a key.
	 * 
	 * @param String
	 *            host address of VMs to map
	 * @throws Exception
	 *             if something bad happens.
	 */
//	public static Map<Integer, Management.VmInfo> mapVmInfo(String hostAddr)
//			throws Exception {
//		Map<Integer, Management.VmInfo> map = new LinkedHashMap<Integer, Management.VmInfo>();
//		HostIdentifier hostIdentifier = Management.getHostIdentifier(hostAddr);
//		MonitoredHost monitoredHost = MonitoredHost
//				.getMonitoredHost(hostIdentifier);
//		Set<Integer> jvmIds = monitoredHost.activeVms();
//		// harvest vm info from host
//		for (Integer jvmId : jvmIds) {
//			MonitoredVm monitoredVm = Management.getMonitoredVm(monitoredHost,
//					jvmId);
//			// save vm info.
//			map.put(jvmId, new Management.VmInfo(monitoredVm));
//			monitoredVm.detach();
//		}
//
//		return map;
//	}

	/**
	 * Returns a MonitoredVm instance based on the localVm process id
	 * 
	 * @param id
	 *            process id associated with vm
	 * @return MonitoredVm
	 * @throws Exception
	 *             if something goes wrong
	 */
//	public static MonitoredVm getMonitoredVmFromId(int id) throws Exception {
//		HostIdentifier hostIdentifier = Management
//				.getHostIdentifier(Management.VALUE_LOCALHOST);
//		MonitoredHost monitoredHost = MonitoredHost
//				.getMonitoredHost(hostIdentifier);
//		return Management.getMonitoredVm(monitoredHost, id);
//	}

	// private static Pattern defaultAddrPattern = Pattern.compile(".+");
	private static Pattern simpleAddrPattern = Pattern.compile(".+:[0-9]+");

	/**
	 * Returns a fully constructed JMXServiceURL based on passed address. It
	 * accepts default form "hostname", "hostname:port", or the verobse form
	 * "service:jmx:rmi://host:port/jmxrmi". If the param is not the first two
	 * it assumes the verbose form.
	 * 
	 * @param hostUrl
	 *            - a String form of the host address.
	 * @return JMXServiceURL.
	 */
	public static JMXServiceURL getJmxUrlFrom(String hostUrl) throws Exception {
		if (hostUrl == null || hostUrl.isEmpty())
			return null;
		String urlString = hostUrl;

		// if scheme,protocol, port omitted, assume "localhost"
		if (hostUrl.equalsIgnoreCase(VALUE_LOCALHOST)) {
			urlString = "service:jmx:rmi:///jndi/rmi://" + hostUrl
					+ ":1099/jmxrmi";
		}

		// if host:port provided, decorate with scheme,protocol, and path
		if (simpleAddrPattern.matcher(hostUrl).matches()) {
			urlString = "service:jmx:rmi:///jndi/rmi://" + hostUrl + "/jmxrmi";
		}

		// else use url as provided

		JMXServiceURL svcUrl = null;
		try {
			svcUrl = new JMXServiceURL(urlString);
		} catch (MalformedURLException ex) {
			throw new Exception(ex);
		}
		return svcUrl;
	}

	/**
	 * This method returns the Connector address exported by the agent.
	 * 
	 * @param vm
	 *            process id
	 */
//	public static String getLocalVmAddress(VirtualMachine vm) throws Exception {
//
//		// load management-agent.jar from java_home/lib/
//		String homeDir = vm.getSystemProperties().getProperty("java.home");
//		String agentPath = homeDir + Management.VALUE_FILE_SEP + "lib"
//				+ Management.VALUE_FILE_SEP + Management.VALUE_AGENT_JAR;
//
//		File agentFile = new File(agentPath);
//		if (!agentFile.exists() || !agentFile.isFile()) {
//			throw new Exception("Unable to find management agent file.");
//		}
//
//		vm.loadAgent(agentFile.getAbsolutePath(),
//				Management.VALUE_AGENT_INIT_PROP);
//
//		Properties agentProps = vm.getAgentProperties();
//		String address = (String) agentProps
//				.get(Management.VALUE_AGENT_CONNECTOR_PROP);
//		return address;
//	}

//	public static void verifyServerConnection(Context ctx)
//			throws ShellException {
//		MBeanServerConnection server = (MBeanServerConnection) ctx
//				.getValue(Management.KEY_JMX_MBEANSERVER);
//		if (server == null) {
//			throw new ShellException("No JMX server connection found. "
//					+ "Connect to a JMX server first (see help).");
//		}
//	}

	/**
	 * Convenience wrapper for MBeanServerConnection.getObjectInstance(). It
	 * rolls up all exception into ShellException.
	 * 
	 * @param server
	 * @param nameStr
	 * @return
	 * @throws ShellException
	 */
	public static ObjectInstance getObjectInstance(
			MBeanServerConnection server, String nameStr) throws ShellException {
		ObjectInstance result = null;
		try {
			result = server.getObjectInstance(new ObjectName(nameStr));
		} catch (MalformedObjectNameException ex) {
			throw new ShellException(ex);
		} catch (NullPointerException ex) {
			throw new ShellException(ex);
		} catch (InstanceNotFoundException ex) {
			throw new ShellException(ex);
		} catch (IOException ex) {
			throw new ShellException(ex);
		}

		return result;
	}

	/**
	 * Returns a collection of fully-realized object instances. It uses the
	 * server instance to retrieve the instances if they exist.
	 * 
	 * @param server
	 *            - MBeanServerConnection instance
	 * @param nameStr
	 *            - ObjectName expression used to retrieve beans
	 * @return ObjectInsatnce[] a collection of ObjectInstance
	 * @throws ShellException
	 *             - if anything is not correct.
	 */
	public static ObjectInstance[] getObjectInstances(
			MBeanServerConnection server, String nameStr) throws ShellException {
		ObjectInstance[] result = null;
		try {
			ObjectName objName = new ObjectName(nameStr);
			Set<ObjectInstance> objs = server.queryMBeans(objName, null);
			result = (objs != null) ? objs.toArray(new ObjectInstance[] {})
					: null;
		} catch (IOException ex) {
			throw new ShellException(ex);
		} catch (MalformedObjectNameException ex) {
			throw new ShellException(ex);
		} catch (NullPointerException ex) {
			throw new ShellException(ex);
		}

		return result;
	}

	/**
	 * Look for object instances in cache first. Then, if none is found, load
	 * object instances from mbean server.
	 * 
	 * @param ctx
	 *            - Context
	 * @param name
	 *            - the ObjectName or the label used for caching the instance
	 * @return - one or more instances that may match the name
	 * @throws ShellException
	 */
	public static ObjectInstance[] findObjectInstances(Context ctx, String name)
			throws ShellException {
		Map<String, ObjectInstance> map = (Map<String, ObjectInstance>) ctx
				.getValue(Management.KEY_MBEANS_MAP);

		if (name == null) {
			if (map == null || map.get(Management.KEY_DEFAULT_MBEANS) == null) {
				throw new ShellException(
						String.format("You must specify MBean(s) for command or "
								+ "set a default MBean using 'mbean' command (see help)."));
			}
			return new ObjectInstance[] { map
					.get(Management.KEY_DEFAULT_MBEANS) };
		} else {
			ObjectInstance[] objs = null;
			if (map != null && map.get(name) != null) {
				objs = new ObjectInstance[] { map.get(name) };
			} else {
				MBeanServerConnection conn = (MBeanServerConnection) ctx
						.getValue(Management.KEY_JMX_MBEANSERVER);
				objs = Management.getObjectInstances(conn, name);
			}
			return objs;
		}
	}

	/**
	 * A domain class to cache information for discovered local JVM instances.
	 * 
	 */
	public static class VmInfo {
		
		private String address;
		private boolean attachable;

		

		public String getAddress() {
			return address;
		}

		public boolean isAttachable() {
			return attachable;
		}

		
	}

}