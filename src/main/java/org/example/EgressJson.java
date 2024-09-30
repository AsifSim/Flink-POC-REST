package org.example;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

public class EgressJson {
    @JsonProperty
    String who;

    @JsonProperty
    String greeting;

    @JsonProperty
    Integer count;

    public void setWho(String who) {
        this.who = who;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getWho() {
        return who;
    }
    EgressJson(String name, String greet, int count){
        this.count=count;
        this.who=name;
        this.greeting =greet;
    }

//    public EgressJson() {}
}
