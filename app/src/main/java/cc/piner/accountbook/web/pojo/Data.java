package cc.piner.accountbook.web.pojo;

/**
 * <p>createDate 22-9-2</p>
 * <p>fileName   Data</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class Data {
    int statusCode;
    String description;

    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "statusCode=" + statusCode +
                ", description='" + description + '\'' +
                '}';
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Data(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }
}
