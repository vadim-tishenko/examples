import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Properties;
import java.util.Set;

public class Tools {
    public static void main(String[] args) {
       /* List<VirtualMachineDescriptor> vms = VirtualMachine.list();
        for (VirtualMachineDescriptor desc : vms) {
            VirtualMachine vm;
            try {
                vm = VirtualMachine.attach(desc);
            } catch (AttachNotSupportedException e) {
                continue;
            }
            Properties props = vm.getAgentProperties();
            String connectorAddress =
                    props.getProperty("com.sun.management.jmxremote.localConnectorAddress");
            if (connectorAddress == null) {
                continue;
            }
            JMXServiceURL url = new JMXServiceURL(connectorAddress);
            JMXConnector connector = JMXConnectorFactory.connect(url);
            try {
                MBeanServerConnection mbeanConn = connector.getMBeanServerConnection();
                Set<ObjectName> beanSet = mbeanConn.queryNames(null, null);

            } finally {
                jmxConnector.close();
            }
        }*/
    }
}
