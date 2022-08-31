package cc.piner.accountbook.sqlite.pojo;

/**
 * <p>createDate 22-3-11</p>
 * <p>fileName   Cost</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class Cost {
    long id;
    long cost;
    long time;
    String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + id +
                ", cost=" + cost +
                ", time=" + time +
                ", desc='" + desc + '\'' +
                '}';
    }

    public Cost(long id, long cost, long time, String desc) {
        this.id = id;
        this.cost = cost;
        this.time = time;
        this.desc = desc;
    }

    public Cost() {
    }
}
