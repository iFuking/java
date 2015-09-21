package cn.com.jtang.healthcloud.pojo;

public class Device {
    public String deviceId;
    public String place;

    public Device() {}
    public Device(String deviceId, String place) {
        this.deviceId = deviceId;
        this.place = place;
    }

    public String getDeviceId() { return deviceId; }
    public Device setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getPlace() { return place; }
    public Device setPlace(String place) {
        this.place = place;
        return this;
    }
}
