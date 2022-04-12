import javax.swing.*;
import java.awt.*;

public class canvas extends JPanel{
    static JFrame f = new JFrame("WA01-Indoor Localization");
    static ImageIcon icon2 = new ImageIcon("./2F_coordinate.png");
    static ImageIcon icon3 = new ImageIcon("./UST 3F_Plan modified.png");
        canvas()
        {
        }
        public static void output(int floor, int x,int y,int xpre,int ypre)
        {
            drawPoint p = new drawPoint(floor,x,y,xpre,ypre);
            f.add(p);
            f.pack();
            f.setSize(new Dimension(1548,1122));
            f.setVisible(true);
            new canvas();
        }

}
