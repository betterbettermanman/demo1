package com.example.demo1.test;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Socket 测试工具
 */
public class SocketTest {

    //Logger和LoggerFactory导入的是org.slf4j包
    private final static Logger logger = LoggerFactory.getLogger(SocketTest.class);

    private static String HOST = "192.168.2.46";//本地
//    private static String HOST = "172.16.4.192";//测试环境
//    private static String HOST = "172.16.4.192:32300";//测试环境172.16.4.192:32300
    private static int PORT = 50080;
//    private static int PORT = 32300;

    private static final int UPS_CLIENT_NUM = 1;
    private static final int EXTERNALENV_CLIENT_NUM = 0;
    private static final int FLOW_CLIENT_NUM = 0;
    private static final int CALORIFIC_CLIENT_NUM = 0;
    private static int Count = 1;

    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static final int SLEEP_TIME = 10000;

    public static void main(String[] args) throws Exception {
        test(HOST, PORT);

    }

    public static void test(String _host, int _port) throws Exception {
        if (!StringUtils.isEmpty(_host) && _port != 0) {
            HOST = _host;
            PORT = _port;
        }
        logger.info("*****************推送socket服务地址ip:{},port:{}***************", HOST, PORT);

        int count = UPS_CLIENT_NUM + EXTERNALENV_CLIENT_NUM + FLOW_CLIENT_NUM + CALORIFIC_CLIENT_NUM;

     /*   try {
            Socket socket = new Socket(HOST, PORT);
        } catch (Exception e) {
            logger.error("*****************链接失败，推送socket服务地址ip:{},port:{}***************", HOST, PORT);
            throw new Exception(e);
        }*/

        for (int i = 0; i < UPS_CLIENT_NUM; i++) {
            createConnect("ups", i);
        }
        System.out.println("并发开始，并发连接总数：" + count);
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("===============" + atomicInteger.get() + "==========================");
    }

    public static void createConnect(String type, int i) {

        new Thread(() -> {
            int c = 10000;
            Socket client = null;
            try {
                client = new Socket(HOST, PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (InputStream is = client.getInputStream();
                 OutputStream os = client.getOutputStream();) {
                while (c > 0) {
//                    os.write((createups()).getBytes("utf-8"));
                    // 数据的结尾加上换行符才可让服务器端的readline()停止阻塞
                    atomicInteger.incrementAndGet();
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
                    Thread.sleep(SLEEP_TIME);
                    c--;
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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置你想要的格式
        String dateStr = df.format(calendar.getTime());
        HashMap<Object, Object> request = new HashMap<>();
        HashMap<String, Object> one = new HashMap<>();
        request.put("1", one);
        one.put("time", dateStr);
        one.put("tempFront", (double) 0);
        one.put("tempAfter", (double) 0);
        one.put("shockFront", (double) 0);
        one.put("shockAfter", (double) 0);
        one.put("output", (double) 0);
        one.put("runSpeed", (double) 0);
        one.put("runRate", (double) 0);
        one.put("setRate", (double) 0);
        one.put("inVal", (double) 0);
        one.put("inValFeed", (double) 0);
        one.put("runShow", (double) 0);
        one.put("alarm", (double) 0);
        one.put("ventOpen", (double) 0);
        one.put("ventClose", (double) 0);
        one.put("alarmRest", (double) 0);
        one.put("local", (double) 0);
        one.put("control", (double) 0);
        one.put("site", "秦川-测试环境");
        HashMap<String, Object> two = new HashMap<>();
        request.put("2", two);
        two.put("crudeVal", (double) 0);
        two.put("time", dateStr);
        two.put("site", "秦川-测试环境");
        two.put("loop", "回路-测试数据");
        HashMap<String, Object> three = new HashMap<>();
        request.put("3", three);
        three.put("fineVal", (double) 0);
        three.put("time", dateStr);
        three.put("site", "秦川-测试环境");
        three.put("loop", "回路-测试数据");
        HashMap<String, Object> four = new HashMap<>();
        request.put("4", four);
        four.put("totalSC01", (double) 0);
        four.put("totalSC02", (double) 0);
        four.put("streamSC", (double) 0);
        four.put("streamWC", (double) 0);
        four.put("realPre", (double) 0);
        four.put("realTem", (double) 0);
        four.put("totalWC01", (double) 0);
        four.put("totalWC02", (double) 0);
        four.put("battery", (double) 0);
        four.put("site", "秦川-测试环境");
        four.put("loop", "回路-测试数据");
        four.put("time", dateStr);
        request.put("5", null);
        HashMap<String, Object> six = new HashMap<>();
        request.put("6", six);
        six.put("fineTem", (double) 0);
        six.put("time", dateStr);
        HashMap<String, Object> seven = new HashMap<>();
        request.put("7", seven);
        seven.put("finePre", (double) 0);
        seven.put("time", dateStr);
        HashMap<String, Object> eight = new HashMap<>();
        request.put("8", eight);
        /*eight.put("time", dateStr);
        eight.put("n2", (double) 0);
        eight.put("ch4", (double) 0);
        eight.put("co2", (double) 0);
        eight.put("c2h6", (double) 0);
        eight.put("c3h8", (double) 0);
        eight.put("c4h10iso", (double) 0);
        eight.put("c4h10n", (double) 0);
        eight.put("c5h12neo", (double) 0);
        eight.put("c5h12iso", (double) 0);
        eight.put("c5h12n", (double) 0);
        eight.put("c6p", (double) 0);
        eight.put("nsTotal", (double) 0);
        eight.put("sn2", (double) 0);
        eight.put("sch4", (double) 0);
        eight.put("sco2", (double) 0);
        eight.put("sc2h6", (double) 0);
        eight.put("sc3h8", (double) 0);
        eight.put("sc4h10iso", (double) 0);
        eight.put("sc4h10n", (double) 0);
        eight.put("sc5h12neo", (double) 0);
        eight.put("sc5h12iso", (double) 0);
        eight.put("sc5h12n", (double) 0);
        eight.put("sc6p", (double) 0);
        eight.put("tHeatMass", (double) 0);
        eight.put("nHeatMass", (double) 0);
        eight.put("tHeatVol", (double) 0);
        eight.put("nHeatVol", (double) 0);
        eight.put("sumFactor", (double) 0);
        eight.put("moWeight", (double) 0);
        eight.put("density", (double) 0);
        eight.put("densityRel", (double) 0);
        eight.put("wobbeTotal", (double) 0);
        eight.put("wobbeNet", (double) 0);
        eight.put("spare", (double) 0);*/
        HashMap<String, Object> nine = new HashMap<>();
        request.put("9", nine);
        nine.put("alarmGas", (double) 2.3);
        nine.put("time", dateStr);
        nine.put("site", "秦川-测试环境");
        HashMap<String, Object> ten = new HashMap<>();
        request.put("10", ten);
        ten.put("alarm", (double) 0);
        ten.put("time", dateStr);
        ten.put("site", "秦川-测试环境");
        return JSONObject.toJSONString(request);
    }
}
