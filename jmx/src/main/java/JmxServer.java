/*
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.port=1234
-Dcom.sun.management.jmxremote.ssl=false

-Djava.rmi.server.hostname=A.B.C.D

взято тут: https://stackoverflow.com/questions/5552960/how-to-connect-to-a-java-program-on-localhost-jvm-using-jmx
 */
public class JmxServer {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <100 ; i++) {
            Thread.sleep(2000);
            System.out.print(".");
        }
    }
}
