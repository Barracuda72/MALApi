import javax.swing.*;

/**
 * Created by barracuda on 22.09.16.
 */
public class MALPopupMenu extends JPopupMenu {
    JMenuItem anItem;
    public MALPopupMenu(){
        anItem = new JMenuItem("Click Me!");
        add(anItem);
    }
}
