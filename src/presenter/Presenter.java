package presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.MedicalCase;

import java.util.logging.Handler;

public class Presenter {
    private MedicalCase data;
    private IPresenterListener<MedicalCase> iPresenterListener;
    private Gson gson=new GsonBuilder()
            .serializeNulls()
            .create();

    public Presenter(IPresenterListener<MedicalCase> iPresenterListener){
        this.iPresenterListener=iPresenterListener;
    }

    public void getData(){
        new Thread(getDataInBack).start();
    }

    private Runnable getDataInBack=new Runnable() {
        @Override
        public void run() {
            iPresenterListener.done(data);

        }
    };

    public void setData(MedicalCase data){

    }

    public void saveData(MedicalCase data){

    }


    public void done(MedicalCase data) {

    }
}
