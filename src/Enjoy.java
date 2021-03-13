package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

public class Enjoy {

    public Enjoy() {
        JFrame main = new JFrame("TITAN");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main.setSize(1080, 720);
        main.setLocationRelativeTo(null);

        JPanel panel = null;

        try {
            Image img = ImageIO.read(new File("Background.jpg"));
            panel = new ImagePanel(img);
            main.add(panel);
            main.setLocationRelativeTo(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton start = new JButton("START");
        start.setFont(new Font("Arial", Font.BOLD, 20));
        start.setBackground(new Color(192, 192, 192));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new Panel();
            main.dispose();
            }
        });

        panel.setLayout(null);
        JPanel finalPanel = panel;
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                start.setLocation(main.getWidth()/4, main.getHeight()/2+(main.getHeight()/9));
                start.setSize(main.getWidth()/3, main.getHeight()/10);
                finalPanel.add(start);
            }
        });

        main.setVisible(true);

    }

    static class ImagePanel extends JPanel {

        private final Image img;
        private Image scaled;

        public ImagePanel(Image img) {
            this.img = img;
        }

        @Override
        public void invalidate() {
            super.invalidate();
            int width = getWidth();
            int height = getHeight();

            if (width > 0 && height > 0) {
                scaled = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(this), img.getHeight(this));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(scaled, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        new Enjoy();
    }
}
