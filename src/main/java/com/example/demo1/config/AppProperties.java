package com.example.demo1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppProperties {
    private String remoteUrl;
    private String robotId;
    private String pictureServer;
    private String pictureSourcePath;
    private String pictureTargetPath;
    private String pictureQrPath;
    private String weatherKey;
    private String weatherUrl;

    public AppProperties() {

    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getPictureServer() {
        return pictureServer;
    }

    public void setPictureServer(String pictureServer) {
        this.pictureServer = pictureServer;
    }

    public String getPictureSourcePath() {
        return pictureSourcePath;
    }

    public void setPictureSourcePath(String pictureSourcePath) {
        this.pictureSourcePath = pictureSourcePath;
    }

    public String getPictureTargetPath() {
        return pictureTargetPath;
    }

    public void setPictureTargetPath(String pictureTargetPath) {
        this.pictureTargetPath = pictureTargetPath;
    }

    public String getPictureQrPath() {
        return pictureQrPath;
    }

    public void setPictureQrPath(String pictureQrPath) {
        this.pictureQrPath = pictureQrPath;
    }

    public String getWeatherKey() {
        return weatherKey;
    }

    public void setWeatherKey(String weatherKey) {
        this.weatherKey = weatherKey;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }

    public void setWeatherUrl(String weatherUrl) {
        this.weatherUrl = weatherUrl;
    }
}
