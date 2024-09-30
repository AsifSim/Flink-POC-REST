package org.example;

import io.undertow.Undertow;
import org.apache.flink.statefun.sdk.java.StatefulFunctions;
import org.apache.flink.statefun.sdk.java.handler.RequestReplyHandler;


public final class ConnectedComponentsAppServer {

    public static void main(String[] args) {
        final StatefulFunctions functions = new StatefulFunctions();
        functions.withStatefulFunction(GreeterFn.SPEC);

        final RequestReplyHandler requestReplyHandler = functions.requestReplyHandler();
        final Undertow httpServer =
                Undertow.builder()
                        .addHttpListener(1108, "0.0.0.0")
                        .setHandler(new org.example.UndertowHttpHandler(requestReplyHandler))
                        .build();
        httpServer.start();
    }
}
