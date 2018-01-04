package model;

/**
 * Created by LockyLuo on 2017/9/20.
 *
 */

public class ApiUrl {
    public static void setClassUrl(String classUrl) {
        CLASS_URL = classUrl;
    }
    public static String BASE_URL ="https://api.bmob.cn/1/";
    public static String CLASS_URL = "https://api.bmob.cn/1/classes/";


    public static class Get {

        public static String LOGIN_USER_URL = BASE_URL + "login";//登录
        public static String DOCTOR_RSA_URL = CLASS_URL + "DoctorRsa";
    }

    public static class Post{
        public static String REGISTER_USER_URL = BASE_URL + "users";
        public static String MD5_USER_URL = CLASS_URL + "MD5";
    }

    public static class Put{
        public static final String DEL_DYNAMIC_FAKE_URL = CLASS_URL + "dynamic/batchDelete";


    }

    public static class Del{
        public static final String DEL_ATTEND_URL = CLASS_URL + "attend/";

    }
}
