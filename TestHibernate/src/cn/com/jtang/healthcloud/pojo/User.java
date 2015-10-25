package cn.com.jtang.healthcloud.pojo;

public class User {             // 用户基本信息

    private int userId;         // 用户id
    private String subscribe;   // 是否关注公众号
    private String openId;      // 微信号
    private String nickname;    // 昵称
    private String sex;         // 性别
    private String age;         // 年龄
    private String language;    // 语言
    private String realName;    // 真实姓名
    private String cardId;      // 身份证号
    private String telephone;   // 手机号
    private String avatarUrl;   // 头像链接（服务器）
    private String city;        // 所在城市
    private String province;    // 所在省份
    private String country;     // 所在国家
    private String subscribeTime;  // 关注时间
    private String unionId;
    private String remark;
    private String groupId;

    public User() {}
    public User(String subscribe, String openId, String nickname, String sex, String age,
                String language, String realName, String cardId, String telephone,
                String avatarUrl, String city, String province, String country,
                String subscribeTime, String unionId, String remark, String groupId) {
        this.subscribe = subscribe;
        this.openId = openId;
        this.nickname = nickname;
        this.sex = sex;
        this.age = age;
        this.language = language;
        this.realName = realName;
        this.cardId = cardId;
        this.telephone = telephone;
        this.avatarUrl = avatarUrl;
        this.city = city;
        this.province = province;
        this.country = country;
        this.subscribe = subscribeTime;
        this.unionId = unionId;
        this.remark = remark;
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }
    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getSubscribe() {
        return subscribe;
    }
    public User setSubscribe(String subscribe) {
        this.subscribe = subscribe;
        return this;
    }

    public String getOpenId() {
        return openId;
    }
    public User setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }
    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getSex() {
        return sex;
    }
    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getAge() {
        return age;
    }
    public User setAge(String age) {
        this.age = age;
        return this;
    }

    public String getLanguage() {
        return language;
    }
    public User setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getRealName() {
        return realName;
    }
    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getCardId() {
        return cardId;
    }
    public User setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }
    public User setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public User setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getCity() {
        return city;
    }
    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getProvince() {
        return province;
    }
    public User setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCountry() {
        return country;
    }
    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }
    public User setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
        return this;
    }

    public String getUnionId() {
        return unionId;
    }
    public User setUnionId(String unionId) {
        this.unionId = unionId;
        return this;
    }

    public String getRemark() {
        return remark;
    }
    public User setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }
    public User setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }
}
