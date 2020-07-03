package com.freshcaller.twiliostreamsjava;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Pause;
import com.twilio.twiml.voice.Say;
import com.twilio.twiml.voice.Start;
import com.twilio.twiml.voice.Stream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class TwiMLController {

    @PostMapping(value = "/twiml", produces = "application/xml")
    @ResponseBody
    public String getStreamsTwiml(UriComponentsBuilder uriInfo) {
        String wssUrl = "wss://" + uriInfo.build().getHost() + "/messages";

        return new VoiceResponse.Builder()
                .say(new Say.Builder("Hello! Start talking").build())
                .start(new Start.Builder().stream(new Stream.Builder().url(wssUrl).build()).build())
                .pause(new Pause.Builder().length(30).build())
                .build().toXml();
    }

}