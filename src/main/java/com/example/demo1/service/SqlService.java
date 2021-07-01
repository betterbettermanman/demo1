package com.example.demo1.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class SqlService {
    public String createSql(String startTime, String endTime) {
        StringBuilder sql = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        try {
            start = simpleDateFormat.parse(startTime);
            Date end = simpleDateFormat.parse(endTime);
            int s = start.compareTo(end);
            for (; start.compareTo(end) <= 0; start = addDate(start, 1)) {
                if (sql.length() == 0) {
                    sql.append("SELECT '").append(simpleDateFormat.format(start)).append("',count(amount),sum(amount) FROM index_details WHERE bidding='INVITE_BIDDING' and publish_at >= '").append(simpleDateFormat.format(start)).append(" 00:00:00' and publish_at <= '").append(simpleDateFormat.format(start)).append(" 23:59:59'");
                } else {
                    sql.append(" union all SELECT '").append(simpleDateFormat.format(start)).append("',count(amount),sum(amount) FROM index_details WHERE bidding='INVITE_BIDDING' and publish_at >= '").append(simpleDateFormat.format(start)).append(" 00:00:00' and publish_at <= '").append(simpleDateFormat.format(start)).append(" 23:59:59'");
                }
            }
            sql.append(";");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sql.toString();
    }

    public Date addDate(Date current, int num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(current);
        calendar.add(calendar.DATE, num);
        current = calendar.getTime();
        return current;
    }
}
