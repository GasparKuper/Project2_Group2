package WindModel;

import Body.Vector.Vector2d;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindModel extends Canvas implements Runnable{

    private static final long serialVersionUID = 1;

    public static final int HEIGHT = 600, WIDTH = 900;
    private Thread thread;

    private boolean running = false;
    private Handler handler;

    public WindModel(){
        handler = new Handler();

        new RandomWind(handler); // initializing the random wind

        new Window(WIDTH,HEIGHT,"Let's Start the Wind!", this);

        // adding the Landing Module
        handler.addObject(new LandingModule(new Vector2d(225,600),new Vector2d(1,-1.352),0,new Vector2d(0,0),new Vector2d(0,0),ID.LandingModule));

        // adding the Wind Flags
        double count = 0;
        for(int i = 0; i < 14; i++){
            handler.addObject(new windFlag(new Vector2d(20,10+count)));
            count = count + 40;
        }
    }

    // starting the thread
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // popular game loop
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();

    }

    public static void main(String []args){
        new WindModel();

    }

}
