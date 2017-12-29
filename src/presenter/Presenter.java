package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.MedicalCase;
import sun.rmi.runtime.Log;

import java.io.*;

/**
 *
 */

public class Presenter {
    private MedicalCase data=new MedicalCase();
    private IPresenterListener<MedicalCase> iPresenterListener;
    private Gson gson=new GsonBuilder()
            .serializeNulls()
            .create();
    private String jsonData;
    File file=new File("D:\\data.txt");

    public Presenter(IPresenterListener<MedicalCase> iPresenterListener){
        this.iPresenterListener=iPresenterListener;
    }

    public void getData(){
        new Thread(getDataInBackground).start();
    }

    private Runnable getDataInBackground =new Runnable() {
        @Override
        public void run() {


            iPresenterListener.done(data);

        }
    };

    public void setData(MedicalCase data){
        this.data=data;
//        new Thread(setDataInBackground).start();
    }



    private Runnable saveDataInBackground=new Runnable() {
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
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                jsonData=gson.toJson(data);
                log(jsonData);
                fileOutputStream.write(jsonData.getBytes());
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
//            iPresenterListener.done(data);
        }
    };

    public void saveData(MedicalCase data){
        this.data=data;
        log(gson.toJson(data));
        new Thread(saveDataInBackground).start();
    }

    void log(Object msg){
        System.out.println(msg);
    }



}
