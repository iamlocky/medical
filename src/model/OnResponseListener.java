package model;

import model.bean.UniversalResponseBean;


/**
 * Created by LockyLuo on 2017/9/20.
 * 成功则返回T，否则返回Exception
 */

public interface OnResponseListener<E> {
    void onFinish(E responseBean, Exception e);
}


