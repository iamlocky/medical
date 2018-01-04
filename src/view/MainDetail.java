package view;

import model.Model;
import model.StringUtil;
import model.bean.MedicalCase;
import model.bean.PatientInfo;
import model.bean.User;
import presenter.IPresenterListener;
import presenter.Presenter;
import sun.tools.jar.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class MainDetail implements IPresenterListener {
    private JPanel mainPanel;
    private JButton button1;
    private JButton button2;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JLabel l6;

    private static JFrame frame;
    private Font font = new Font("微软雅黑", Font.PLAIN, 20);
    private Presenter presenter;
    private static User user;

    @Override
    public void done(Object data) {
        int n = JOptionPane.showConfirmDialog(null, ((MedicalCase) data).getPatientInfo().getName(), "title", JOptionPane.OK_CANCEL_OPTION);
        if (n == JOptionPane.YES_OPTION) {

        }
    }

    @Override
    public void done(String data) {

    }

    public MainDetail() {
        initView();
        String pass = JOptionPane.showInputDialog(null, "输入患者密码：");
        getP().setPatientPassword(pass);
        getP().getPatientDo();




        SwingUtilities.invokeLater(()->{
            PatientInfo p= Presenter.getPatient();
            if (p==null) {
                JOptionPane.showMessageDialog(null,"密码不正确");
                frame.dispose();
                System.exit(-1);
                return;
            }
            l1.setText(p.getName());
            l2.setText(p.getId());
            l3.setText(p.getBirth());
            l4.setText(p.getGender());
            l5.setText(p.getBloodType());
            l6.setText(p.getDrugAllergies());
        });
    }

    public static void main(String[] args) {
        user = Presenter.getUser();
        if (user.getUsername() == null) {
            JOptionPane.showMessageDialog(null, "找不到医生，退出系统");
            System.exit(0);
        }
        frame = new JFrame("MainDetail");
        frame.setTitle("病历系统------当前医生:" + user.getUsername());
        frame.setContentPane(new MainDetail().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示
    }

    void initData() {
        getP().getDoctorRsa();

    }

    public void initView() {
        initData();
        button1.setText("新建病历");
        button2.setText("修改病历");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Edit.main(null);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir")+"/case");
                jFileChooser.showOpenDialog(null);
                File file=jFileChooser.getSelectedFile();
                if (file==null)
                    return;
                getP().getCase(file);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showConfirmDialog(null, msg, "title", JOptionPane.YES_NO_OPTION);
    }

    public Presenter getP() {
        if (presenter == null) {
            presenter = new Presenter(this, MedicalCase.class);
        }
        return presenter;
    }

    public void print(Object msg) {
        System.out.println(msg);
    }
}
