import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

/**
 * Created by barracuda on 20.09.16.
 */
public class MALMainWindow implements WindowListener {
    private JPanel panel1;
    private JButton buttonAnimeToManga;
    private JButton buttonReload;
    private JList listItems;
    DefaultListModel<MALEntry> listModel;
    private static final int MAX_ROW_COUNT = 3;

    private static MALMainWindow s = null;

    public MALMainWindow() {
        s = this;
        listModel = new DefaultListModel<>();

        reloadList();

        listItems.setModel(listModel);
        listItems.setCellRenderer(new MALEntryRenderer());
        listItems.setVisibleRowCount(MAX_ROW_COUNT);

        ListSelectionModel listSelectionModel = listItems.getSelectionModel();
        //listSelectionModel.addListSelectionListener(
                //new MALListSelectionHandler());

        listItems.addMouseListener(new MALListMouseListener(false));

        buttonReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reloadList();
            }
        });

        String title = MALSettings.isAnime() ? "Manga" : "Anime";
        buttonAnimeToManga.setText(title);
        buttonAnimeToManga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchMode();
            }
        });
    }

    public static void update() {
        s.reloadList();
    }

    private void switchMode() {
        MALSettings.setAnime(!MALSettings.isAnime());
        String title = MALSettings.isAnime() ? "Manga" : "Anime";
        buttonAnimeToManga.setText(title);
        reloadList();
    }

    private void reloadList()
    {
        listModel.clear();
        try {
            List<MALEntry> entries = MALXmlParser.parseData(MALHttp.loadUserList(
                    MALSettings.getUser(), MALSettings.getPassword(), MALSettings.isAnime()));
            for (MALEntry entry : entries)
                listModel.addElement(entry);
        } catch (MALException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occured during list update!");
            System.exit(-1);
        }
        listItems.updateUI();
    }

    public static void run() {
        JFrame frame = new JFrame("MALMainWindow");

        MALMainWindow mmw = new MALMainWindow();
        frame.setContentPane(mmw.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMaximumSize(new Dimension(800, 600));
        frame.pack();
        frame.addWindowListener(mmw);
        frame.setVisible(true);
    }

    public void windowClosing(WindowEvent e) {
        MALSettings.save();
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }
}
