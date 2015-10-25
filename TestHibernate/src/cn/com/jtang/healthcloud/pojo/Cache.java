package cn.com.jtang.healthcloud.pojo;

public class Cache {           // 缓存报告基本信息
    private String reportId;   // 报告id
    private String content;    // 内容

    public Cache() {
    }

    public Cache(String reportId, String content) {
        this.reportId = reportId;
        this.content = content;
    }

    public String getReportId() { return reportId; }
    public Cache setReportId(String reportId) {
        this.reportId = reportId;
        return this;
    }

    public String getContent() { return content; }
    public Cache setContent(String content) {
        this.content = content;
        return this;
    }
}
