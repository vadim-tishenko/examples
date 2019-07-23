//package testapp;

import java.net.URI;

// https://kaazing.com/demos/echo-test/
// https://chrome.google.com/webstore/detail/smart-websocket-client/omalebghpgejjiaoknljcfmglgbpocdp?utm_source=chrome-app-launcher-info-dialog

public class TestApp {

    public static void main(String[] args) {
        String SEC_WS="wss://demos.kaazing.com/echo";
        String WS = "ws://demos.kaazing.com/echo";

        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint( URI.create(WS));

            // add listener
            clientEndPoint.addMessageHandler(message -> System.out.println(message));

            // send message to websocket
            clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");



            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);
            clientEndPoint.close();

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
    }
}