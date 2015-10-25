package cn.com.jtang.healthcloud.pojo;

public class Report { // 报告基本信息

    private String reportId;         // 报告id
    private String timestamp;        // 时间戳
    private String height;           // 身高
    private String weight;           // 体重
    private String bodyFatRate;      // 体脂
    private String spo2h;            // 血氧
    private String systolicPressure;    // 收缩压（高压）
    private String diastolicPressure;   // 舒张压（低压）
    private String beatsPerMinute;      // 心率
    private String viscera;          // 脏腑
    private String spine;            // 脊椎
    private String digestion;        // 消化
    private String urinary;          // 泌尿
    private String advice;           // 建议

    public Report() {}
    public Report(String reportId, String timestamp, String height, String weight,
                  String bodyFatRate, String spo2h, String systolicPressure,
                  String diastolicPressure, String beatsPerMinute, String viscera,
                  String spine, String digestion, String urinary, String advice) {
        this.reportId = reportId;
        this.timestamp = timestamp;
        this.height = height;
        this.weight = weight;
        this.bodyFatRate = bodyFatRate;
        this.spo2h = spo2h;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.beatsPerMinute = beatsPerMinute;
        this.viscera = viscera;
        this.spine = spine;
        this.digestion = digestion;
        this.urinary = urinary;
        this.advice = advice;
    }

    public String getReportId() {
        return reportId;
    }
    public Report setReportId(String reportId) {
        this.reportId = reportId;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public Report setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getHeight() {
        return height;
    }
    public Report setHeight(String height) {
        this.height = height;
        return this;
    }

    public String getWeight() {
        return weight;
    }
    public Report setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getBodyFatRate() {
        return bodyFatRate;
    }
    public Report setBodyFatRate(String bodyFatRate) {
        this.bodyFatRate = bodyFatRate;
        return this;
    }

    public String getSpo2h() {
        return spo2h;
    }
    public Report setSpo2h(String spo2h) {
        this.spo2h = spo2h;
        return this;
    }

    public String getSystolicPressure() {
        return systolicPressure;
    }
    public Report setSystolicPressure(String systolicPressure) {
        this.systolicPressure = systolicPressure;
        return this;
    }

    public String getDiastolicPressure() {
        return diastolicPressure;
    }
    public Report setDiastolicPressure(String diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
        return this;
    }

    public String getBeatsPerMinute() {
        return beatsPerMinute;
    }
    public Report setBeatsPerMinute(String beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
        return this;
    }

    public String getViscera() {
        return viscera;
    }
    public Report setViscera(String viscera) {
        this.viscera = viscera;
        return this;
    }

    public String getSpine() {
        return spine;
    }
    public Report setSpine(String spine) {
        this.spine = spine;
        return this;
    }

    public String getDigestion() {
        return digestion;
    }
    public Report setDigestion(String digestion) {
        this.digestion = digestion;
        return this;
    }

    public String getUrinary() {
        return urinary;
    }
    public Report setUrinary(String urinary) {
        this.urinary = urinary;
        return this;
    }

    public String getAdvice() {
        return advice;
    }
    public Report setAdvice(String advice) {
        this.advice = advice;
        return this;
    }
}
