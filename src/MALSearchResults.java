import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by barracuda on 27.09.16.
 */
public class MALSearchResults {
    private JList listItems;
    private JPanel panel;
    DefaultListModel<MALEntry> listModel;
    private static final int MAX_ROW_COUNT = 3;

    public MALSearchResults(String query) {
        listModel = new DefaultListModel<>();

        loadList(query);

        listItems.setModel(listModel);
        listItems.setCellRenderer(new MALEntryRenderer());
        listItems.setVisibleRowCount(MAX_ROW_COUNT);

        ListSelectionModel listSelectionModel = listItems.getSelectionModel();
        //listSelectionModel.addListSelectionListener(
        //new MALListSelectionHandler());

        listItems.addMouseListener(new MALListMouseListener(true));
    }

    private void loadList(String query)
    {
        listModel.clear();
        try {
            List<MALEntry> entries = MALXmlParser.parseData(MALHttp.searchEntry(
                    MALSettings.getUser(), MALSettings.getPassword(), query, MALSettings.isAnime()));
            for (MALEntry entry : entries)
                listModel.addElement(entry);
        } catch (MALException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occured during list update!");
            System.exit(-1);
        }
        listItems.updateUI();
    }

    public static void run(String query) {
        JFrame frame = new JFrame("Search results");

        frame.setContentPane(new MALSearchResults(query).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMaximumSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }
}
