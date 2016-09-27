import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * Created by barracuda on 22.09.16.
 */
public class MALPopupMenu extends JPopupMenu {
    JMenuItem anItemAdd;
    JMenuItem anItemDel;
    JMenuItem anItemUpd;

    MALEntry entry;

    public MALPopupMenu(MALEntry e, boolean inSearch) {
        entry = e;
        anItemAdd = new JMenuItem("Add entry");
        add(anItemAdd);
        anItemAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (inSearch)
                    addEntry(false);
                else
                    searchEntry();
                hide();
            }
        });
        if (!inSearch && e != null) {
            anItemUpd = new JMenuItem("Update entry");
            anItemDel = new JMenuItem("Remove entry");
            add(anItemUpd);
            add(anItemDel);

            anItemDel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    deleteEntry();
                }
            });

            anItemUpd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    addEntry(true);
                }
            });
        }
    }

    private void addEntry(boolean update) {
        JDialog dialog = null;
        if (entry instanceof MALAnime)
            dialog = new MALAnimeForm((MALAnime) entry, update);
        else
            dialog = new MALMangaForm((MALManga) entry, update);
        dialog.show();
    }

    private void deleteEntry() {
        if (JOptionPane.showConfirmDialog (null,
                "Really delete?", "Delete", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION)
            try {
                MALHttp.deleteEntry(MALSettings.getUser(), MALSettings.getPassword(), entry);
                MALMainWindow.update();
                hide();
            } catch (MALException e) {
                JOptionPane.showMessageDialog(null, "Error deleting entry!");
            }
    }

    private void searchEntry() {
        MALSearchQuery q = new MALSearchQuery();
        q.show();
    }
}
