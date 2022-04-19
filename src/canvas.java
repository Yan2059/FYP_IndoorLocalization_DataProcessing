import javax.swing.*;
import java.awt.*;

public class canvas extends JPanel{
    static JFrame f = new JFrame("WA01-Indoor Localization");
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
