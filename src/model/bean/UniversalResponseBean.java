package model.bean;

/**
 * Created by LockyLuo on 2017/9/18.
 * 返回的对象
 */


public class UniversalResponseBean<E>{
    private int status;
    private String info;//信息
    private E data;//里面是T泛型数据

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

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UniversalResponseBean{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
