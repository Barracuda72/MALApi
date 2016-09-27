import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MALAuthWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldUsername;
    private JPasswordField passwordFieldPassword;

    public MALAuthWindow() {
        setContentPane(contentPane);
        setModal(true);
        setSize(270, 110);
        setTitle("Authentication");
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String user = textFieldUsername.getText();
        String pass = passwordFieldPassword.getText();
        try {
            MALHttp.verifyCreds(user, pass);
            MALSettings.setUser(user);
            MALSettings.setPassword(pass);
            MALSettings.save();
            hide();
            dispose();
            MALMainWindow.run();
        } catch (MALException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Invalid username/password!");
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        MALSettings.load();
        if (MALSettings.getUser() == null) {
            MALAuthWindow maw = new MALAuthWindow();
            maw.show();
        } else {
            MALMainWindow.run();
        }
    }
}
