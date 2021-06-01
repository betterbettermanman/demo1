package com.example.demo1.bean;

public class ReceiverRequestMsg {

    //事件名称
    private String event;
    //机器人id
    private String robot_wxid;
    private String to_name;
    //来源群id
    private String from_wxid;
    //来源群名称
    private String from_name;
    //消息类型
    private String type;
    //具体发消息的群成员id/私聊时用户id
    private String final_from_wxid;
    //具体发消息的群成员昵称/私聊时用户昵称
    private String final_from_name;
    //发给谁，往往是机器人自己(也可能别的成员收到消息)
    private String to_wxid;
    //消息体(str/json) 不同事件和不同type都不一样，自己去试验吧
    private Object msg;
    private String msgid;
    private Object json_msg;

    public String getFinal_from_wxid() {
        return final_from_wxid;
    }

    public void setFinal_from_wxid(String final_from_wxid) {
        this.final_from_wxid = final_from_wxid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRobot_wxid() {
        return robot_wxid;
    }

    public void setRobot_wxid(String robot_wxid) {
        this.robot_wxid = robot_wxid;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom_wxid() {
        return from_wxid;
    }

    public void setFrom_wxid(String from_wxid) {
        this.from_wxid = from_wxid;
    }

    public String getFinal_from_name() {
        return final_from_name;
    }

    public void setFinal_from_name(String final_from_name) {
        this.final_from_name = final_from_name;
    }

    public String getTo_wxid() {
        return to_wxid;
    }

    public void setTo_wxid(String to_wxid) {
        this.to_wxid = to_wxid;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public Object getJson_msg() {
        return json_msg;
    }

    public void setJson_msg(Object json_msg) {
        this.json_msg = json_msg;
    }
}
