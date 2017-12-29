import model.MedicalCase;
import presenter.IPresenterListener;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Main implements IPresenterListener<MedicalCase> {
    private static Frame frame;
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JPanel innerPanel;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private Presenter presenter;
    private Font font=new Font("微软雅黑",Font.PLAIN,20);
    @Override
    public void done(MedicalCase data) {

    }

    public Main() {
        getP();
        initView();
    }

    public static void main(String[] args) {
        frame=initFrame();
    }

    public static Frame initFrame(){
        JFrame frame = new JFrame("Main");
        frame.setTitle("病历");
        frame.setContentPane(new Main().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        int windowWidth = frame.getWidth(); //获得窗口宽
        int windowHeight = frame.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        frame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示

        return frame;
    }

    private void initView(){
        passwordField1.setFont(font);
        textField1.setFont(font);
        button1.setFont(font);
        button2.setFont(font);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn1");
                MedicalCase data=new MedicalCase();
                data.setDate(new Date());
                data.setDoctor("doc");
                data.setPatientID("123");
                data.setPatientName("pat");
                getP().saveData(data);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,"msg","title",JOptionPane.YES_NO_OPTION);
                System.out.println("btn2");
            }
        });

    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showConfirmDialog(null,msg,"title",JOptionPane.YES_NO_OPTION);
    }

    public Presenter getP(){
        if (presenter==null) {
            presenter=new Presenter(this);
        }
        return presenter;
    }

}
