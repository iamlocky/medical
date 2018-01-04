package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.*;

import model.bean.*;
import view.Edit;


import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 */

public class Presenter<T> {

    private T data;
    private static DoctorRsa doctorRsa;
    private static Patient patient;
    private IPresenterListener<T> iPresenterListener;
    private Gson gson = new GsonBuilder().serializeNulls().create();
    private String jsonData;
    File file = new File("data.txt");
    private Type type;
    private static User user=new User();
    private static String patientPassword;
    private static MedicalCase medicalCase;
    public Presenter(IPresenterListener<T> iPresenterListener, Type type) {
        this.iPresenterListener = iPresenterListener;
        this.type = type;
    }

    public static MedicalCase getMedicalCase() {
        return medicalCase;
    }

    public static void setMedicalCase(MedicalCase medicalCase) {
        Presenter.medicalCase = medicalCase;
    }

    public static DoctorRsa getDoctorRsa() {
        return doctorRsa;
    }

    public void setDoctorRsa(DoctorRsa doctorRsa) {
        this.doctorRsa = doctorRsa;
    }

    public static Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }



    public void setPatientPassword(String patientPassword) {
        this.patientPassword = leftPading(patientPassword,"a",16);;
    }

    public static User getUser() {
        return user;
    }
    public void login(String u, String p) {
        Model model = new Model();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(u);
        loginUser.setPassword(p);
        model.getData(ApiUrl.Get.LOGIN_USER_URL, loginUser.getMap(), new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {

                try {
                    JsonObject json=gson.fromJson(responseBean,JsonObject.class);
                    if (json.get("code")!=null) {
                        iPresenterListener.done(responseBean);
                    }else {
                        data=Model.getGson().fromJson(responseBean,type);
                        user=(User)data;
                        iPresenterListener.done(data);
                    }
                }catch (Exception e1){
                    iPresenterListener.done(responseBean);
                }
            }
        });
    }

    public void register(String u, String p,JProgressBar progressBar){
        Model model = new Model();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(u);
        loginUser.setPassword(p);
        model.postData(ApiUrl.Post.REGISTER_USER_URL, loginUser, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                SwingUtilities.invokeLater(()->{
                    progressBar.setVisible(false);
                });
                try {
                    JsonObject json=gson.fromJson(responseBean,JsonObject.class);
                    if (json.get("code")!=null) {
                        JOptionPane.showMessageDialog(null,responseBean,"注册失败",JOptionPane.ERROR_MESSAGE);
                    }else if(json.get("objectId")!=null){
                        JOptionPane.showMessageDialog(null,"注册成功");
                    }else {
                        JOptionPane.showMessageDialog(null,responseBean,"注册失败",JOptionPane.ERROR_MESSAGE);
                    }
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(null,responseBean,"注册失败",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void getData() {
        new Thread(getDataInBackground).start();
    }

    private Runnable getDataInBackground = new Runnable() {
        @Override
        public void run() {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                byte[] buffer = new byte[1024];
                int flag = 0;
                while ((flag = bufferedInputStream.read(buffer)) != -1) {
                    stringBuffer.append(new String(buffer, 0, flag));
                }
                jsonData = stringBuffer.toString();
                log(jsonData);
                if (type!=null) {
                    data = gson.fromJson(jsonData, type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (type != null) {

                        iPresenterListener.done(data);
                    }else {
                        iPresenterListener.done(jsonData);
                    }
                }
            });
        }
    };

    public void savePa(){
        File filePatient=new File("patient.info");
        patient=new Patient();
        PatientRsa patientRsa=new PatientRsa();
        patient.setGender("男");
        patient.setBirth("1989年6月3日");
        patient.setBloodType("O");
        patient.setDrugAllergies("无");
        patient.setId("44100000000000000");
        patient.setName("张三");
        try {
            Map<String, Object> keyMap = RsaUtils.genKeyPair();
            patientRsa.setPrivateKey(RsaUtils.getPrivateKey(keyMap));
            patientRsa.setPublicKey(RsaUtils.getPublicKey(keyMap));
            patient.setPatientRsa(patientRsa);
            FileOutputStream fileOutputStream = new FileOutputStream(filePatient);
            jsonData = gson.toJson(patient);
            log(jsonData);
            jsonData=AESUtil.encrypt(jsonData,patientPassword);
            fileOutputStream.write(jsonData.getBytes());
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCase(MedicalCase medicalCase, Date date){
        File fileCase=new File("case/");
        if (!fileCase.exists()) {
            fileCase.mkdir();
        }
        fileCase=new File("case/"+date.toString().replace(":","")+".case");

        try {
            jsonData = gson.toJson(medicalCase);
            MD5 md5=new MD5();
            md5.setName(fileCase.getName());
            md5.setMd5(MD5Util.md5(jsonData));
            String rsaData=RsaUtils.sign(jsonData.getBytes(),patient.getPatientRsa().getPrivateKey());
            md5.setData(rsaData);

            Model model=new Model();
            model.postData(ApiUrl.Post.MD5_USER_URL, md5, new OnStringResponseListener() {
                @Override
                public void onFinish(String responseBean, Exception e) {

                }
            });

            FileOutputStream fileOutputStream = new FileOutputStream(fileCase);

            log(jsonData);
            jsonData=AESUtil.encrypt(jsonData,patientPassword);
            fileOutputStream.write(jsonData.getBytes());
            fileOutputStream.close();
            SwingUtilities.invokeLater(()->{
                JOptionPane.showMessageDialog(null,"保存成功！");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCase(File file){
        try {
            if (!file.exists())
            {
                SwingUtilities.invokeLater(()->{
                    JOptionPane.showMessageDialog(null,"打开失败");
                });
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[1024];
            int flag = 0;
            while ((flag = bufferedInputStream.read(buffer)) != -1) {
                stringBuffer.append(new String(buffer, 0, flag));
            }
            jsonData = stringBuffer.toString();
            jsonData= AESUtil.decrypt(jsonData,patientPassword);
            log(jsonData);
            medicalCase = gson.fromJson(jsonData, MedicalCase.class);
            Edit.setMedicalCase(medicalCase);
            SwingUtilities.invokeLater(()->{
                Edit.main(new String[]{"1"});
            });
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(()->{
                JOptionPane.showMessageDialog(null,"打开失败");
            });
        }

    }

    public void getPatientDo(){
        new Thread(getPatientInBackground).start();
    }

    private Runnable getPatientInBackground = new Runnable() {
        @Override
        public void run() {
            try {

                File filePatient=new File("patient.info");
                if (!filePatient.exists())
                {
                    SwingUtilities.invokeLater(()->{
                        JOptionPane.showMessageDialog(null,"找不到患者信息");
                    });
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(filePatient);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                byte[] buffer = new byte[1024];
                int flag = 0;
                while ((flag = bufferedInputStream.read(buffer)) != -1) {
                    stringBuffer.append(new String(buffer, 0, flag));
                }
                jsonData = stringBuffer.toString();
                jsonData= AESUtil.decrypt(jsonData,patientPassword);
                log(jsonData);
                patient = gson.fromJson(jsonData, Patient.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public void setData(T data) {
        this.data = data;
//        new Thread(setDataInBackground).start();
    }

    public void getDoctorRsaDo(){
        Model model=new Model();

        model.getData(ApiUrl.Get.DOCTOR_RSA_URL,null, new OnStringResponseListener() {
            @Override
            public void onFinish(String responseBean, Exception e) {
                try {
                    JsonObject json=gson.fromJson(responseBean,JsonObject.class);
                    if (json.get("code")!=null) {

                    }else {
                        Type type=new TypeToken<List<DoctorRsa>>(){}.getType();
                        doctorRsa=Model.getGson().fromJson(responseBean,type);
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }


    private Runnable saveDataInBackground = new Runnable() {
        @Override
        public void run() {

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                jsonData = gson.toJson(data);
                log(jsonData);
                fileOutputStream.write(jsonData.getBytes());
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
//            iPresenterListener.done(data);
        }
    };

    public void saveData(T data) {
        this.data = data;
        new Thread(saveDataInBackground).start();
    }

    void log(Object msg) {
        StackTraceElement[] trace = new Throwable().getStackTrace();
        for (StackTraceElement traceElement : trace)
            System.out.println("\tat " + traceElement);
        System.out.println(msg);
    }

    public static String leftPading(String strSrc,String flag,int strSrcLength) {
        String strReturn = "";
        String strtemp = "";
        int curLength = strSrc.trim().length();
        if (strSrc != null && curLength > strSrcLength) {
            strReturn = strSrc.trim().substring(0, strSrcLength);
        } else if (strSrc != null && curLength == strSrcLength) {
            strReturn = strSrc.trim();
        } else {

            for (int i = 0; i < (strSrcLength - curLength); i++) {
                strtemp = strtemp + flag;
            }
            strReturn = strtemp + strSrc.trim();
        }
        return strReturn;
    }

}
