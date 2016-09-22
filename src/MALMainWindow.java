import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by barracuda on 20.09.16.
 */
public class MALMainWindow {
    private JPanel panel1;
    private JButton buttonAnimeToManga;
    private JButton buttonReload;
    private JList listItems;
    DefaultListModel<MALEntry> listModel;
    private static final int MAX_ROW_COUNT = 3;

    public MALMainWindow() {
        listModel = new DefaultListModel<>();

        reloadList();

        listItems.setModel(listModel);
        listItems.setCellRenderer(new MALEntryRenderer());
        listItems.setVisibleRowCount(MAX_ROW_COUNT);

        ListSelectionModel listSelectionModel = listItems.getSelectionModel();
        //listSelectionModel.addListSelectionListener(
                //new MALListSelectionHandler());

        listItems.addMouseListener(new MALListMouseListener());

        buttonReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reloadList();
            }
        });
    }

    private void reloadList()
    {
        listModel.clear();
        List <MALEntry> entries = MALXmlParser.parseUserList("/home/barracuda/malappinfo.xml");
        for (MALEntry entry : entries)
            listModel.addElement(entry);
        listItems.updateUI();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MALMainWindow");

        frame.setContentPane(new MALMainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMaximumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }
}
