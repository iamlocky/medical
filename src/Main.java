import model.MedicalCase;
import presenter.IPresenterListener;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements IPresenterListener<MedicalCase> {
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JPanel innerPanel;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private Presenter presenter;

    @Override
    public void done(MedicalCase data) {

    }

    public Main() {
        getP();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn1");
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn2");
            }
        });
    }

    public static void main(String[] args) {
        initView();

    }

    public static Frame initView(){
        JFrame frame = new JFrame("Main");
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


    public Presenter getP(){
        if (presenter==null) {
            presenter=new Presenter(this);
        }
        return presenter;
    }

}
