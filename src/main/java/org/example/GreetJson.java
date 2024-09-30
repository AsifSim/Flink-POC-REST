package org.example;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

public final class GreetJson {

    @JsonProperty
    String who;

    @JsonProperty
    String Greeting;

    public String getGreeting() {
        return Greeting;
    }

    public String getWho() {
        return who;
    }

    public GreetJson() {}
}
