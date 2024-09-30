package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.statefun.sdk.java.*;
import org.apache.flink.statefun.sdk.java.message.EgressMessageBuilder;
import org.apache.flink.statefun.sdk.java.message.Message;

import static org.example.Types.EGRESS_JSON_TYPE;
import static org.example.Types.Greet_JSON_TYPE;

final class GreeterFn implements StatefulFunction {

    static final TypeName TYPE = TypeName.typeNameOf("org.example", "greetings");
    static final ValueSpec<Integer> SEEN = ValueSpec.named("seen").withIntType();

    static final StatefulFunctionSpec SPEC =
            StatefulFunctionSpec.builder(TYPE)
                    .withSupplier(GreeterFn::new)
                    .withValueSpecs(SEEN)
                    .build();

    private static final TypeName PLAYGROUND_EGRESS =
            TypeName.typeNameOf("io.statefun.playground", "egress");

    @Override
    public CompletableFuture<Void> apply(Context context, Message message) {
        System.out.println("===========In apply==============");
        System.out.println("class=========="+message.getClass());
        System.out.println("toString=============="+message.toString());
        System.out.println("raw==========="+message.rawValue());
        GreetJson greetMessage=message.as(Greet_JSON_TYPE);
        String user=greetMessage.getWho();
        String greet=greetMessage.getGreeting();
        System.out.println("The message received={"+user+","+greet+"}");
        int seenCount = context.storage().get(SEEN).orElse(0);
        seenCount++;
        context.storage().set(SEEN, seenCount);
        final EgressJson egressJson = new EgressJson(user, greet,seenCount);
        if(seenCount==1){
            System.out.println(greet+" "+user);
            //To REST Endpoint
//            sendToRestEndpoint(egressJson);
        }
        else {
            System.out.println("Hi " + user + ", You have already greeted me before!");
            //To JDBC Egress
//            context.send(
//                    MessageBuilder.forAddress(INBOX, name)
//                            .withValue("Hello " + name + " for the " + seen + "th time!")
//                            .build());
        }
        System.out.println("===========Out of apply==============");
        return context.done();
    }

    //For sending data to REST API for saving it in DB

//    private void sendToRestEndpoint(EgressJson egressJson) {
//        try {
//            URL url = new URL("http://127.0.0.1:8086/flink/ToPostgres");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//            conn.setRequestProperty("Accept", "application/json");
//            conn.setDoOutput(true);
//
//            String jsonInputString="";
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                jsonInputString = objectMapper.writeValueAsString(egressJson);
//                System.out.println(jsonInputString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("In sendToRestEndpoint "+ jsonInputString);
//
//            try (OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            int code = conn.getResponseCode();
//            System.out.println("Response Code: " + code);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void restFunction()
//    {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpPut httpPost = new HttpPut("http://localhost:7071/process/");
//            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//                HttpEntity responseEntity = response.getEntity();
//                if (responseEntity != null) {
//                    System.out.println("Response: " + responseEntity.getContent().toString());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
