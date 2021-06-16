package WindModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// a class to have key manipulation

public class KeyInput extends KeyAdapter {

    private Handler handler;
    public KeyInput(Handler handler){
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        // Loops through all the objects in the game
        for (int i = 0; i < handler.object.size();i++){
            Object tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_W) tempObject.setYVelocity(tempObject.getYVelocity()+10);
            }
            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_S) tempObject.setYVelocity(tempObject.getYVelocity()-10);
            }
            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_A) tempObject.setXVelocity(tempObject.getXVelocity()-10);
            }
            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_D) tempObject.setXVelocity(tempObject.getXVelocity()+10);
            }
            if(tempObject.getId() == ID.WindFlag){
                if (key == KeyEvent.VK_UP) tempObject.setXVelocity(tempObject.getXVelocity()-10);
            }
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();


        for (int i = 0; i < handler.object.size();i++){
            Object tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_W) tempObject.setYVelocity(0);
            }
            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_S) tempObject.setYVelocity(0);
            }
            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_A) tempObject.setXVelocity(0);
            }
            if(tempObject.getId() == ID.LandingModule){
                if (key == KeyEvent.VK_D) tempObject.setXVelocity(0);
            }
            if(tempObject.getId() == ID.WindFlag){
                if (key == KeyEvent.VK_UP) tempObject.setXVelocity(0);
            }
        }

    }

}
