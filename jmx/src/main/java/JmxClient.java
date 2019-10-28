import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

public class JmxClient {
    public static void main(String[] args) throws IOException {
        String host = "localhost";  // or some A.B.C.D
        int port = 1234;
        String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
        JMXServiceURL serviceUrl = new JMXServiceURL(url);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceUrl, null);
        try {
            MBeanServerConnection mbeanConn = jmxConnector.getMBeanServerConnection();
            // now query to get the beans or whatever
            Set<ObjectName> beanSet = mbeanConn.queryNames(null, null);
            for (ObjectName objectName : beanSet) {
                System.out.println(objectName.getCanonicalName());
            }
            System.out.println(beanSet.size());
        } finally {
            jmxConnector.close();
        }
    }
}
