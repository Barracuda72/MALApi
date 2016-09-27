import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.*;

public class MALSearchQuery extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldQuery;

    public MALSearchQuery() {
        setContentPane(contentPane);
        setModal(true);
        //setVisible(true);
        setSize(160, 90);
        setResizable(false);

        setTitle("Query");
        getRootPane().setDefaultButton(buttonOK);
        pack();

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
        String query = textFieldQuery.getText();
        if (query.length() < 3)
            JOptionPane.showMessageDialog(null, "Query showld be at least 3 characters long!");
        else {
            MALSearchResults.run(query);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
