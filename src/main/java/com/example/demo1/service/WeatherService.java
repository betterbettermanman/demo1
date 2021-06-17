package com.example.demo1.service;

import com.example.demo1.config.AppProperties;
import com.example.demo1.dao.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppProperties myAppProperties;

    /**
     * @param cityCode 城市编码
     */
    public String getWeather(int cityCode) {
        String url = String.format("%s?key=%s&city=%s", myAppProperties.getWeatherUrl(), myAppProperties.getWeatherKey(), cityCode);
        Map m = restTemplate.getForObject(url, Map.class);
        StringBuilder builder = new StringBuilder();
        if ("10000".equals(String.valueOf(m.get("infocode")))) {
            List lives = (List) m.get("lives");
            if (lives.size() == 1) {
                Map<String, String> weather = (Map) lives.get(0);
                builder.append("地区：").append(weather.get("province") + weather.get("city")).append("\n");
                /* 时间转换 */
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(weather.get("reporttime"), dateTimeFormatter);
                DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("MM-dd");
                String dateString = dateTimeFormatter2.format(localDateTime);

                builder.append("时间：").append(dateString).append("\n");
                builder.append("天气：").append(weather.get("weather")).append("\n");
                builder.append("温度：").append(weather.get("temperature")).append("\n");
                builder.append("风向：").append(weather.get("winddirection")).append("/" + weather.get("windpower")).append("\n");
            }
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    public String getWeather(String cityName) {
        List<Map<String, Object>> codes = cityMapper.selectCode(cityName);
        if (codes.size() == 1) {
            return getWeather(Integer.parseInt(codes.get(0).get("code").toString()));
        } else if (codes.size() == 0) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder("请输入以下准确地区名称：\n\r");
            for (Map<String, Object> m : codes) {
                sb.append(m.get("name")).append("\n\r");
            }
            return sb.toString();
        }
    }
}
