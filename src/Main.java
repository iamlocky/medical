import com.sun.deploy.util.StringUtils;
import model.StringUtil;
import model.bean.User;
import presenter.IPresenterListener;
import presenter.Presenter;
import view.Edit;
import view.JTextFieldHintListener;
import view.MainDetail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main implements IPresenterListener<User> {
    private static Frame frame;
    private JPanel panel;
    private JButton button1;

    private JPanel innerPanel;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JLabel label1;
    private JLabel label2;
    private JProgressBar progressBar1;
    private JButton button2;
    private Presenter presenter;
    private Font font = new Font("微软雅黑", Font.PLAIN, 20);
    private Font fontMin = new Font("微软雅黑", Font.PLAIN, 10);

    @Override
    public void done(User data) {
        if (data == null)
            return;
        progressBar1.setVisible(false);

        JOptionPane.showMessageDialog(null, "医生 " + data.getUsername() + "登录成功！", "欢迎", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
        MainDetail.main(null);

    }

    @Override
    public void done(String data) {
        JOptionPane.showMessageDialog(null, data, "登录失败", JOptionPane.ERROR_MESSAGE);
        progressBar1.setVisible(false);

    }

    public Main() {
        getP();
        initView();
    }

    public static void main(String[] args) {
        frame = initFrame();
    }


    private void initView() {
        label1.setFont(fontMin);
        label2.setFont(fontMin);
        button2.setFont(fontMin);
        textField1.setFont(font);
        passwordField1.setFont(font);
        progressBar1.setVisible(false);

        textField1.addFocusListener(new JTextFieldHintListener("医生账号", textField1));
        passwordField1.setFocusable(true);
        passwordField1.grabFocus();
        passwordField1.requestFocus(true);
        passwordField1.requestFocusInWindow();
        button1.setFont(font);

        JOptionPane.showMessageDialog(null,"组长\n" +
                "骆庭蔚 2015082009\n" +
                "组员\n" +
                "张文辉 2015082009\n" +
                "吴瑞源 2015082008\n" +
                "戴云 2015082046");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setVisible(true);
                getP().login(textField1.getText(), new String(passwordField1.getPassword()));
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (StringUtil.isEmpty(textField1.getText().trim()) || StringUtil.isEmpty(new String(passwordField1.getPassword()).trim())) {
                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空", "注册失败", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                progressBar1.setVisible(true);
                getP().register(textField1.getText(), new String(passwordField1.getPassword()), progressBar1);
            }
        });
    }


    public static Frame initFrame() {
        JFrame frame = new JFrame("Main");
        frame.setTitle("登录病历系统");
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
        frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);//设置窗口居中显示

        return frame;
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showConfirmDialog(null, msg, "title", JOptionPane.YES_NO_OPTION);
    }

    public Presenter getP() {
        if (presenter == null) {
            presenter = new Presenter(this, User.class);
        }
        return presenter;
    }

}
