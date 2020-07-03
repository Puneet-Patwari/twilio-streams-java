package com.freshcaller.twiliostreamsjava;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1p1beta1.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class TwilioMediaStreamsHandler extends AbstractWebSocketHandler {

    private Map<WebSocketSession, ClientStream<StreamingRecognizeRequest>> sessions = new HashMap<>();

    private StreamingRecognizeRequest request;

    private ResponseObserver<StreamingRecognizeResponse> responseObserver;

    private ClientStream<StreamingRecognizeRequest> clientStream = null;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.MULAW)
                .setSampleRateHertz(8000)
                .setLanguageCode("en-US")
                .setModel("default")
                .build();
        StreamingRecognitionConfig streamingRecognitionConfig = StreamingRecognitionConfig.newBuilder()
                .setConfig(config)
                .setInterimResults(true)//.setSingleUtterance(true)
                .build();
        request = StreamingRecognizeRequest.newBuilder()
                .setStreamingConfig(streamingRecognitionConfig)
                .build(); // The first request in a streaming call has to be a config

        System.out.println("Transcription: will start");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        JsonObject jsonObject = JsonParser.parseString(textMessage.getPayload()).getAsJsonObject();
//        System.out.println("IN");

        String event = jsonObject.get("event").getAsString();
        switch (event){
            case "connected": {
                SpeechClient client = null;
                try {
                    client = SpeechClient.create();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                responseObserver = new ResponseObserver<StreamingRecognizeResponse>() {

                    @Override
                    public void onStart(StreamController streamController) {
                        System.out.println("Started");
                    }

                    @Override
                    public void onResponse(StreamingRecognizeResponse response) {
                        System.out.println("Puneet");
                        if(response.getResultsList().size()>0){
                            StreamingRecognitionResult result = response.getResultsList().get(0);
                            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                            System.out.println("Transcription: "+alternative.getTranscript());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Completed");
                    }
                };

                clientStream = client.streamingRecognizeCallable().splitCall(responseObserver);
                clientStream.send(request);
                sessions.put(session, clientStream);

                System.out.println("A new call has connected.");
                break;
            }

            case "start": {
                System.out.println("Starting Media Stream "+ jsonObject.get("start").getAsJsonObject().get("streamSid").getAsString());
                break;
            }
            case "media": {
                JsonObject media = jsonObject.get("media").getAsJsonObject();
                String payload = media.get("payload").getAsString();
                ByteString audioContent = ByteString.copyFrom(payload, Charset.forName("US-ASCII"));
                System.out.println("Audio: "+audioContent.toString(Charset.forName("US-ASCII")));
                request = StreamingRecognizeRequest.newBuilder()
                        .setAudioContent(audioContent)
                        .build();
                clientStream.send(request);
                break;
            }
            case "stop": {
                clientStream.closeSend();
                break;
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
