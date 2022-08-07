import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main {
    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    static class PickInfo
    {
        public final int x;
        public final int y;
        public final Color color;

        public PickInfo(int x, int y, Color color)
        {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        @Override
        public String toString()
        {
            return "[PickInfo]-[X:" + x + ",Y:" + y + ",Color:" + color.toString() + "]";
        }
    }

    public static boolean needD = false;
    public static boolean needF = false;
    public static boolean needJ = false;
    public static boolean needK = false;

    static class Check
    {
        private final int x;
        private final int y;
        private final int keyCode;

        public Check(int x, int y, int keyCode) {
            this.x = x;
            this.y = y;
            this.keyCode = keyCode;
        }

        public boolean checkColors(ArrayList<Color> colors)
        {
            for (Color color : colors)
            {
                if (color.getRed() > 10 && color.getGreen() > 10 && color.getBlue() > 10)
                    return true;
            }
            return false;
        }

        public void run() {
            //System.out.println(x + " " + y + " " + pickColor());
            if (checkColors(pickColors())) {
                if (keyCode == KeyEvent.VK_D)
                    needD = true;

                if (keyCode == KeyEvent.VK_F)
                    needF = true;

                if (keyCode == KeyEvent.VK_J)
                    needJ = true;

                if (keyCode == KeyEvent.VK_K)
                    needK = true;
            }
            else
            {
                if (keyCode == KeyEvent.VK_D)
                    needD = false;

                if (keyCode == KeyEvent.VK_F)
                    needF = false;

                if (keyCode == KeyEvent.VK_J)
                    needJ = false;

                if (keyCode == KeyEvent.VK_K)
                    needK = false;
            }

        }

        public ArrayList<Color> pickColors() {
            ArrayList<Color> color = new ArrayList<>();
            for (int i = 0; i < 3; i++)
            {
                if (i == 0)
                    color.add(robot.getPixelColor(x, y - 3));
                if (i == 1)
                    color.add(robot.getPixelColor(x, y));
                if (i == 2)
                    color.add(robot.getPixelColor(x, y + 3));
            }
            return color;
        }
    }

    public static void main(String[] args) throws AWTException {
        JFrame jf = new JFrame();

        Dimension dimension = new Dimension();
        dimension.setSize(400, 300);

        Point p = new Point();
        p.setLocation(400, 300);

        jf.setSize(dimension);
        jf.setLocation(p);
        jf.setVisible(true);

        while (true)
        {
            new Check(1160, 1140, KeyEvent.VK_D).run();
            new Check(1350, 1140, KeyEvent.VK_F).run();
            new Check(1455, 1140, KeyEvent.VK_J).run();
            new Check(1600, 1140, KeyEvent.VK_K).run();

            PickInfo info = pickColor();
            System.out.println(info);
            jf.getContentPane().setBackground(needD ? Color.GREEN : Color.RED); // Debug
        }
    }

    public static PickInfo pickColor() {
        Point mousepoint = MouseInfo.getPointerInfo().getLocation();

        return new PickInfo(mousepoint.x, mousepoint.y, robot.getPixelColor(mousepoint.x, mousepoint.y));
    }
}
