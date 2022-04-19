import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class drawPoint extends JPanel{
    static BufferedImage image2 = null;
    static int floor = 0;//floor number
    static int x = 0;//labeled x coordinate
    static int y = 0;//labeled y coordinate
    static int xPre = 0;//predicted x coordinate
    static int yPre = 0;//predicted y coordinate
    float offsetX = 3.0f;
    float offsetY = 3.0f;
    float scaleX = 7.2f;
    float scaleY = 7.2f;
    drawPoint(int floorNum, int xPos, int yPos, int XprePos,int YprePos){
        floor=floorNum;
        x=xPos;
        y=yPos;
        xPre=XprePos;
        yPre=YprePos;
    }
    protected  void paintComponent(Graphics g){
        super.paintComponent(g);
        //select suitable floor map image base on the floor number
        //add another else if for additional floor
        if(floor==2){
            try {
                image2 = ImageIO.read (new File("./2F_coordinate.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(floor==3){
            try {
                image2 = ImageIO.read (new File("./UST 3F_Plan modified.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.red);
        try {
            g2.drawImage(resizeImage(image2,1548,1122),0,0,this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.fillOval((int)realX(x,offsetX,scaleX),(int)realY(y,offsetY,scaleY),15,15);
        Graphics2D g3 = (Graphics2D)g;
        g3.setColor(Color.green);
        g3.fillOval((int)realX(xPre,offsetX,scaleX),(int)realY(yPre,offsetY,scaleY),15,15);

    }
    //function for rescaling the image to suitable resolution
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    //function for converting the x y coordinate into correct coordinate in JPanel
    float realX(float x, float offX, float scale){
        return (x+offX)*scale;
    }
    float realY(float y, float offY, float scale){
        return (-y*scale+offY);
    }
}
