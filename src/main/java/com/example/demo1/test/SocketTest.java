package com.example.demo1.test;

import com.alibaba.fastjson.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Socket 测试工具
 */
public class SocketTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 2000;

    private static final int UPS_CLIENT_NUM = 1;
    private static final int EXTERNALENV_CLIENT_NUM = 1;
    private static final int FLOW_CLIENT_NUM = 1;
    private static final int CALORIFIC_CLIENT_NUM = 1;

    public static void main(String[] args) throws Exception {
        int count = UPS_CLIENT_NUM + EXTERNALENV_CLIENT_NUM + FLOW_CLIENT_NUM + CALORIFIC_CLIENT_NUM;
        for (int i = 0; i < UPS_CLIENT_NUM; i++) {
            createConnect("ups", i);
        }
        for (int i = 0; i < EXTERNALENV_CLIENT_NUM; i++) {
            createConnect("externalenv", i);
        }
        for (int i = 0; i < FLOW_CLIENT_NUM; i++) {
            createConnect("flow", i);
        }
        for (int i = 0; i < CALORIFIC_CLIENT_NUM; i++) {
            createConnect("calorific", i);
        }
        System.out.println("并发开始，并发连接总数：" + count);

    }

    public static void createConnect(String type, int i) {
        new Thread(() -> {
            Socket client = null;
            try {
                client = new Socket(HOST, PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (InputStream is = client.getInputStream();
                 OutputStream os = client.getOutputStream();) {
                while (true) {
                    if ("ups".equals(type)) {
                        os.write((createups()).getBytes("utf-8"));
                    } else if ("externalenv".equals(type)) {
                        os.write((createexternalenv()).getBytes("utf-8"));
                    } else if ("flow".equals(type)) {
                        os.write((createflow()).getBytes("utf-8"));
                    } else if ("calorific".equals(type)) {
                        os.write((createcalorific()).getBytes("utf-8"));
                    }

                    // 数据的结尾加上换行符才可让服务器端的readline()停止阻塞
                    os.flush();
                    DataInputStream input = new DataInputStream(is);
                    byte[] b = new byte[1024];
                    int len = 0;
                    String response = "";
                    len = input.read(b);
                    if (len != -1) {
                        response = new String(b, 0, len);
                    }
                    System.out.println(Thread.currentThread().getName() + response);
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName());
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, type + i).start();

    }

    public static String createups() {
        HashMap<Object, Object> request = new HashMap<>();
        request.put("data_type", "ups");
        HashMap<String, String> content = new HashMap<>();
        request.put("content", content);
        content.put("loopName", "ups loopName");
        content.put("remark", "ups remark");
        content.put("siteName", "ups siteName");
        content.put("status", "1");
        return JSONObject.toJSONString(request);
    }

    public static String createexternalenv() {
        HashMap<Object, Object> request = new HashMap<>();
        request.put("data_type", "externalenv");
        HashMap<String, String> content = new HashMap<>();
        request.put("content", content);
        content.put("c1_density", "1.1");
        content.put("c2_density", "1.1");
        content.put("c3_density", "1.1");
        content.put("c6_density", "1.1");
        content.put("co2_density", "1.1");
        content.put("h2_density", "1.1");
        content.put("h2o2_density", "1.1");
        content.put("h2s_density", "1.1");
        content.put("he_density", "1.1");
        content.put("hms", "1");
        content.put("ic4_density", "1.1");
        content.put("ic5_density", "1.1");
        content.put("n2_density", "1.1");
        content.put("nc4_density", "1.1");
        content.put("nc5_density", "1.1");
        content.put("neoc5_density", "1.1");
        content.put("o2_density", "1.1");
        content.put("ymd", "2");
        content.put("siteName", "externalenv siteName");
        content.put("loopName", "externalenv loopName");
        return JSONObject.toJSONString(request);
    }

    public static String createflow() {
        HashMap<Object, Object> request = new HashMap<>();
        request.put("data_type", "flow");
        HashMap<String, String> content = new HashMap<>();
        request.put("content", content);
        content.put("flow_velocity", "1.1");
        content.put("hms", "1");
        content.put("pressure", "1.1");
        content.put("sound_velocity", "1.1");
        content.put("standard_density", "1.1");
        content.put("standard_factor", "1.1");
        content.put("standard_flow", "1.1");
        content.put("temperature", "1.1");
        content.put("working_density", "1.1");
        content.put("working_flow", "1.1");
        content.put("ymd", "2");
        content.put("siteName", "flow siteName");
        content.put("loopName", "flow loopName");
        return JSONObject.toJSONString(request);
    }

    public static String createcalorific() {
        HashMap<Object, Object> request = new HashMap<>();
        request.put("data_type", "calorific");
        HashMap<String, String> content = new HashMap<>();
        request.put("content", content);
        content.put("c1_rate", "1.1");
        content.put("c2_rate", "1.1");
        content.put("c3_rate", "1.1");
        content.put("c6_rate", "1.1");
        content.put("calorific_value", "1.1");
        content.put("co2_rate", "1.1");
        content.put("density", "1.1");
        content.put("h2o2_rate", "1.1");
        content.put("h2s_rate", "1.1");
        content.put("he_rate", "1.1");
        content.put("hms", "1");
        content.put("ic4_rate", "1.1");
        content.put("ic5_rate", "1.1");
        content.put("n2_rate", "1.1");
        content.put("nc4_rate", "1.1");
        content.put("nc5_rate", "1.1");
        content.put("neoc5_rate", "1.1");
        content.put("o2_rate", "1.1");
        content.put("wobbe", "1.1");
        content.put("ymd", "2");
        content.put("siteName", "calorific siteName");
        content.put("loopName", "calorific loopName");
        System.out.println(request);
        return JSONObject.toJSONString(request);
    }
}

