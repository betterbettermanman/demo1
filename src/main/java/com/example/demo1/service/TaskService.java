package com.example.demo1.service;

import com.example.demo1.bean.Task;
import com.example.demo1.dao.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CommonService commonService;

    public void remindTask() {
        List<Map<String, String>> maps = taskMapper.selectTask();
        List<Task> lists = new ArrayList<>();
        for (Map<String, String> m : maps) {
            assembleData(lists, m);
        }
        //发送信息
        for (Task t : lists) {
            if (StringUtils.isEmpty(t.getIsConnect())) {
                commonService.sendInfo(t.getTargetAddress(), dealMsg(t.getContexts()));
            } else {
                commonService.sendInfo(t.getTargetAddress(), dealMsg(t.getContexts()), t.getIsConnect());
            }
        }
    }

    /**
     * 组装数据，将所有任务安目标地址，消息类型分配
     *
     * @param lists
     * @param m
     */
    private void assembleData(List<Task> lists, Map m) {
        boolean isNew = false;
        for (Task t : lists) {
            isNew = t.add(m);
            if (isNew) {
                continue;
            }
        }
        if (!isNew) {
            Task task = new Task();
            task.setTargetAddress(m.get("targetAddress").toString());
            task.setMessageTye(m.get("messageType").toString());
            task.setIsConnect(m.get("isConnect").toString());
            ArrayList<String> list = new ArrayList<>();
            list.add(m.get("context").toString());
            task.setContexts(list);
            lists.add(task);
        }
    }

    /**
     * 处理信息，按照1，2，3信息展示
     *
     * @param source
     * @return
     */
    private String dealMsg(List<String> source) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= source.size(); i++) {
            stringBuilder.append("\n").append(i).append(":").append(source.get(i - 1));
        }
        return stringBuilder.toString();
    }
}
