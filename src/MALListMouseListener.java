import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by barracuda on 22.09.16.
 */
public class MALListMouseListener extends MouseAdapter {
    public void mousePressed(MouseEvent e){
        if (SwingUtilities.isRightMouseButton(e) )
        {
            JList list = (JList)e.getSource();
            int row = list.locationToIndex(e.getPoint());
            list.setSelectedIndex(row);
        }

        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        MALPopupMenu menu = new MALPopupMenu();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
