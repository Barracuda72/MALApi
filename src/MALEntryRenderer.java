import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by barracuda on 20.09.16.
 */
public class MALEntryRenderer extends JLabel implements ListCellRenderer<MALEntry> {
    private static final int WIDTH = 112;
    private static final int HEIGHT = 175;

    @Override
    public Component getListCellRendererComponent(JList<? extends MALEntry> jList, MALEntry malEntry, int i, boolean isSelected, boolean b1) {
        ImageIcon imageIcon = loadImage(malEntry.getImageUri());


        setIcon(imageIcon);
        setText(malEntry.getFullText(isSelected));

        return this;
    }

    private ImageIcon loadImage(String uri) {
        return new ImageIcon(ImagePool.loadImage(uri).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
    }
}
