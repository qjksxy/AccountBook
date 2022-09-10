package cc.piner.accountbook.web.pojo;

/**
 * <p>createDate 22-9-3</p>
 * <p>fileName   Cost</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class Cost {
    /*
    int 金额，以分为单位
    String 标题
    int 分类
    long 时间戳
    int 用户id
     */
    int consumption;
    String title;
    int category;
    long time;
    int userid;

    public Cost() {
    }

    public Cost(int consumption, String title, int category, long time, int userid) {
        this.consumption = consumption;
        this.title = title;
        this.category = category;
        this.time = time;
        this.userid = userid;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "consumption=" + consumption +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", time=" + time +
                ", userId=" + userid +
                '}';
    }
}