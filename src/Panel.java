import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Panel {

    public final java.util.Timer timer = new Timer();

    public long startTime;

    public int boosterCount = 0;

    public int boosterTimer = 1;

    JFrame main;

    public Panel(){
        main = new JFrame("BETA");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(1080, 720);
        main.setLocationRelativeTo(null);
        main.setLayout(null);
        dateTime();
        boostButton();
        indicatorBoost();
        timer.schedule(new UpdateUITask(), 0, 1);
        main.setVisible(true);
        startTime = System.currentTimeMillis();
    }


    private JLabel dateGlobal;

    public void dateTime(){
        dateGlobal = new JLabel("1/April/2020    00:00");
        dateGlobal.setFont(new Font("Arial", Font.BOLD, 30));
        dateGlobal.setSize(350, 75);
        dateGlobal.setLocation(20, 20);
        main.add(dateGlobal);
    }

    public void boostButton(){
        JButton buttonIncrease = new JButton(">>");
        buttonIncrease.setFont(new Font("Arial", Font.BOLD, 20));
        buttonIncrease.setBackground(new Color(192, 192, 192));
        buttonIncrease.setSize(70, 50);
        buttonIncrease.setLocation(150, 100);
        buttonIncrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(boosterCount < 3) {
                    boosterCount++;
                    switch (boosterCount) {
                        case 1:
                            boosterTimer = 10;
                            break;
                        case 2:
                            boosterTimer = 100;
                            break;
                        case 3:
                            boosterTimer = 1000;
                            break;
                    }
                }
            }
        });
        main.add(buttonIncrease);

        JButton buttonDecrease = new JButton("<<");
        buttonDecrease.setFont(new Font("Arial", Font.BOLD, 20));
        buttonDecrease.setBackground(new Color(192, 192, 192));
        buttonDecrease.setSize(70, 50);
        buttonDecrease.setLocation(75, 100);
        buttonDecrease.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(boosterCount > 0) {
                    boosterCount--;
                    switch (boosterCount) {
                        case 0:
                            boosterTimer = 1;
                            break;
                        case 1:
                            boosterTimer = 10;
                            break;
                        case 2:
                            boosterTimer = 100;
                            break;
                    }
                }
            }
        });
        main.add(buttonDecrease);
    }

    private JLabel indicator;

    public void indicatorBoost(){
        indicator = new JLabel("X1");
        indicator.setFont(new Font("Arial", Font.BOLD, 30));
        indicator.setSize(500, 50);
        indicator.setLocation(300, 100);
        main.add(indicator);
    }

    //ALGORITHMS TIMER
    private class UpdateUITask extends TimerTask {

        int countMinute = 0;
        int countHour = 0;
        int countDay = 1;
        int countMonth = 4;
        int countYear = 2020;


        @Override
        public void run(){
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {



                    if(time() >= 1000/boosterTimer) {
                        startTime = System.currentTimeMillis();
                        countMinute++;
                    }

                    if(countMinute >= 60){
                        countMinute = 0;
                        countHour++;
                    }

                        if (countHour >= 24) {
                            countHour = 0;
                            countDay++;
                        }

                        if (countDay >= 31) {
                            countDay = 1;
                            countMonth++;
                        }


                        if (countMonth >= 13) {
                            countMonth = 1;
                            countYear++;
                        }

                    dateGlobal.setText(countDay + "/" + countMonth + "/"  + countYear + "     " + countHour + ":" + countMinute);
                    indicator.setText("X" + boosterTimer);
                }
            });
        }

        private long time(){
            long endTime = System.currentTimeMillis();
            return (endTime-startTime);
        }
    }
}
