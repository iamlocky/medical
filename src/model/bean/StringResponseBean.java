package model.bean;

/**
 * Created by LockyLuo on 2017/12/1.
 * 处理data为String的情况
 */

public class StringResponseBean{
    private int status;
    private String info;//信息
    private String data;//里面是String数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UniversalResponseBean{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", data=" + data+
                '}';
    }
}
