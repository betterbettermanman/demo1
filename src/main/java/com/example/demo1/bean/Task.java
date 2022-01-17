package com.example.demo1.bean;

import java.util.List;
import java.util.Map;

public class Task {
    private String messageTye;
    private String targetAddress;
    private String isConnect;
    private List<String> contexts;


    public Task() {
    }

    public String getMessageTye() {
        return messageTye;
    }

    public void setMessageTye(String messageTye) {
        this.messageTye = messageTye;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public String getIsConnect() {
        return isConnect;
    }

    public void setIsConnect(String isConnect) {
        this.isConnect = isConnect;
    }

    public List<String> getContexts() {
        return contexts;
    }

    public void setContexts(List<String> contexts) {
        this.contexts = contexts;
    }


    public boolean add(Map m) {
        if (messageTye.equals(m.get("messageType")) && targetAddress.equals(m.get("targetAddress")) && isConnect.equals(m.get("isConnect"))) {
            contexts.add(m.get("context").toString());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Task{" +
                "messageTye='" + messageTye + '\'' +
                ", targetAddress='" + targetAddress + '\'' +
                ", isConnect='" + isConnect + '\'' +
                ", contexts=" + contexts +
                '}';
    }
}
