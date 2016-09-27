import javax.swing.*;
import java.awt.event.*;

public class MALAnimeForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxStatus;
    private JLabel animeName;
    private JSpinner spinnerEpisodes;
    private JLabel totalCount;
    private JComboBox comboBoxScore;

    MALAnime entry;
    boolean update;

    public MALAnimeForm(MALAnime e, boolean update) {
        entry = e;
        this.update = update;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        pack();
        setSize(175,215);
        setResizable(false);
        setTitle(update?"Update entry":"Add new entry");
        //setVisible(true);

        animeName.setText(e.getTitle());

        comboBoxStatus.setModel(new DefaultComboBoxModel(MALMyStatus.values()));
        comboBoxStatus.setSelectedItem(e.getMyStatus());

        comboBoxScore.setModel(new DefaultComboBoxModel(MALScore.values()));
        comboBoxScore.setSelectedItem(e.getMyScore());

        totalCount.setText("/"+e.getEpisodes());
        spinnerEpisodes.setModel(new SpinnerNumberModel(e.getWatchedEpisodes(), 0,e.getEpisodes(),1 ));

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
        // add your code here
        entry.setWatchedEpisodes((Integer)spinnerEpisodes.getValue());
        entry.setMyScore((MALScore)comboBoxScore.getSelectedItem());
        entry.setMyStatus((MALMyStatus)comboBoxStatus.getSelectedItem());

        try {
            if (update)
                MALHttp.updateEntry(MALSettings.getUser(), MALSettings.getPassword(), entry);
            else
                MALHttp.addEntry(MALSettings.getUser(), MALSettings.getPassword(), entry);

            MALMainWindow.update();
        } catch (MALException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving object!");
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
