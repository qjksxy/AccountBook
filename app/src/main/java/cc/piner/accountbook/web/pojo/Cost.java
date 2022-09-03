package cc.piner.accountbook.web.pojo;

import java.util.List;

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
    int-s 标签
    long 时间戳
     */
    int consumption;
    String title;
    int category;
    List<Integer> tags;
    long time;
    int userId;

    public Cost() {
    }

    public Cost(int consumption, String title, int category, List<Integer> tags, long time, int userId) {
        this.consumption = consumption;
        this.title = title;
        this.category = category;
        this.tags = tags;
        this.time = time;
        this.userId = userId;
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

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "consumption=" + consumption +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", tags=" + tags +
                ", time=" + time +
                ", userId=" + userId +
                '}';
    }
}