package cn.com.jtang.healthcloud.pojo;

public class Relation {
    public String openId;
    public String reportId;
    public String deviceId;
    public String timestamp;

    public Relation() {}
    public Relation(String openId, String reportId, String deviceId, String timestamp) {
        this.openId = openId;
        this.reportId = reportId;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public String getOpenId() { return openId; }
    public Relation setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getReportId() { return reportId; }
    public Relation setReportId(String reportId) {
        this.reportId = reportId;
        return this;
    }

    public String getDeviceId() { return deviceId; }
    public Relation setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getTimestamp() { return timestamp; }
    public Relation setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
