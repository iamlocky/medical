package view;

import com.google.gson.Gson;
import model.Model;
import model.bean.MedicalCase;
import model.bean.Patient;
import model.bean.PatientInfo;
import presenter.IPresenterListener;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Edit {
    private JPanel panel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField8_condition;
    private JTextField textField9_di;
    private JTextField textField10_handle;
    private JTextField textField11_date;
    private JButton button1;
    private JButton button2;
    private JLabel id;

    private static JFrame frame;
    private Presenter presenter;
    private static MedicalCase medicalCase;
    private Date date;

    public static void setMedicalCase(MedicalCase medicalCase) {
        Edit.medicalCase = medicalCase;
    }

    private IPresenterListener listener = new IPresenterListener() {
        @Override
        public void done(Object data) {

        }

        @Override
        public void done(String data) {

        }

        @Override
        public void showMessage(String msg) {
            JOptionPane.showMessageDialog(null,msg);
        }
    };

    public Edit() {
        if (medicalCase == null) {
            medicalCase = new MedicalCase();
            date = new Date();
            Gson gson=Model.getGson();
            medicalCase.setPatientInfo(gson.fromJson(gson.toJson(Presenter.getPatient()),PatientInfo.class));
            medicalCase.setDate(date);
        }else {
            date=medicalCase.getDate();
            textField8_condition.setText(medicalCase.getCondition());
            textField9_di.setText(medicalCase.getDiagnose());
            textField10_handle.setText(medicalCase.getHandle());
        }
        presenter = new Presenter(listener, null);
        initView();
    }

    void initView() {


        PatientInfo patientInfo=medicalCase.getPatientInfo();
        textField1.setText(patientInfo.getId());
        textField2.setText(patientInfo.getName());
        textField3.setText(patientInfo.getGender());
        textField4.setText(patientInfo.getBirth());
        textField5.setText(patientInfo.getDrugAllergies());
        textField6.setText(patientInfo.getBloodType());
        textField11_date.setText(medicalCase.getDate().toString());

        textField1.setEditable(false);
        textField2.setEditable(false);
        textField3.setEditable(false);
        textField4.setEditable(false);
        textField5.setEditable(false);
        textField6.setEditable(false);
        textField11_date.setEditable(false);
        button1.setText("另存为");
        button2.setText("保存修改");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                medicalCase.setPatientInfo(Presenter.getPatient());
                medicalCase.setCondition(textField8_condition.getText().trim());
                medicalCase.setDiagnose(textField9_di.getText().trim());
                medicalCase.setHandle(textField10_handle.getText().trim());
                medicalCase.setDoctor(Presenter.getUser().getUsername());
                medicalCase.setDoctorID(Presenter.getUser().getObjectId());
                medicalCase.setDate(date);
                presenter.saveCase(medicalCase,date);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (medicalCase.getDoctor() == null) {
                    JOptionPane.showMessageDialog(null, "没有旧版病历");
                    return;
                } else {
                    medicalCase.setCondition(textField8_condition.getText().trim());
                    medicalCase.setDiagnose(textField9_di.getText().trim());
                    medicalCase.setHandle(textField10_handle.getText().trim());
                    medicalCase.setDoctor(Presenter.getUser().getUsername());
                    medicalCase.setDoctorID(Presenter.getUser().getObjectId());
                    medicalCase.setDate(date);
                    presenter.saveCase(medicalCase,date);
                }
            }
        });
    }

    public static void main(String[] args) {
        if (args==null)
            setMedicalCase(null);
        frame = new JFrame("Edit");
        frame.setTitle("编辑病历");
        frame.setContentPane(new Edit().panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
        frame.setVisible(true);

    }



}
