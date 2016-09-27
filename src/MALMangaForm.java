import javax.swing.*;
import java.awt.event.*;

public class MALMangaForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxStatus;
    private JSpinner spinnerChapters;
    private JSpinner spinnerVolumes;
    private JComboBox comboBoxScore;
    private JLabel mangaName;
    private JLabel labelAllChapters;
    private JLabel labelAllVolumes;

    MALManga entry;
    boolean update;

    public MALMangaForm(MALManga e, boolean update) {
        entry = e;
        this.update = update;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        pack();
        setSize(175,240);
        setResizable(false);
        setTitle(update?"Update entry":"Add new entry");

        mangaName.setText(e.getTitle());
        comboBoxStatus.setModel(new DefaultComboBoxModel(MALMyStatus.values()));
        comboBoxStatus.setSelectedItem(e.getMyStatus());

        comboBoxScore.setModel(new DefaultComboBoxModel(MALScore.values()));
        comboBoxScore.setSelectedItem(e.getMyScore());

        labelAllChapters.setText("/"+e.getChapters());
        labelAllVolumes.setText("/"+e.getVolumes());
        spinnerChapters.setModel(new SpinnerNumberModel(e.getReadChapters(), 0, e.getChapters(),1 ));
        spinnerVolumes.setModel(new SpinnerNumberModel(e.getReadVolumes(), 0, e.getVolumes(),1 ));

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
        entry.setReadChapters((Integer)spinnerChapters.getValue());
        entry.setReadVolumes((Integer)spinnerVolumes.getValue());
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
