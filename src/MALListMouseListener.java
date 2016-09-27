import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by barracuda on 22.09.16.
 */
public class MALListMouseListener extends MouseAdapter {
    private boolean inSearch = false;

    public MALListMouseListener(boolean inSearch) {
        this.inSearch = inSearch;
    }

    public void mousePressed(MouseEvent e){
        if (SwingUtilities.isRightMouseButton(e) )
        {
            JList list = (JList)e.getSource();
            int row = list.locationToIndex(e.getPoint());
            list.setSelectedIndex(row);
            doPop((MALEntry)list.getSelectedValue(), e);
        }
/*
        if (e.isPopupTrigger())
            doPop(e);*/
    }

    /*public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }*/

    private void doPop(MALEntry entry, MouseEvent e){
        MALPopupMenu menu = new MALPopupMenu(entry, inSearch);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
