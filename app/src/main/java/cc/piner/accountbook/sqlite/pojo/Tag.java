package cc.piner.accountbook.sqlite.pojo;

/**
 * <p>createDate 22-3-11</p>
 * <p>fileName   Tag</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class Tag {
    long id;
    long costId;
    String tag;

    public Tag() {
    }

    public Tag(long id, long costId, String tag) {
        this.id = id;
        this.costId = costId;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCostId() {
        return costId;
    }

    public void setCostId(long costId) {
        this.costId = costId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", costId=" + costId +
                ", tag='" + tag + '\'' +
                '}';
    }
}
