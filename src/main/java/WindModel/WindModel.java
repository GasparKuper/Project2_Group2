package WindModel;

import Body.Vector.Vector2d;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindModel extends Canvas implements Runnable{

    public static final int HEIGHT = 600, WIDTH = 900;
    private Thread thread;

    private boolean running = false;
    private Handler handler;

    public WindModel(LandingModule lander){
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        // adding the Landing Module
        handler.addObject(lander);

        // adding the Wind Flags
        double count = 0;
        for(int i = 0; i < 14; i++){
            handler.addObject(new WindFlag(new Vector2d(20,10+count)));
            count = count + 40;
        }

        new Window(WIDTH,HEIGHT,"Let's Start the Wind!", this);

        //hud = new HUD();
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

    // renders the window
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

    public static int clamp(int var, int min, int max){
        if(var >= max)
                return max;
            else if(var <= min)
                return min;
            else
                return var;
            }

}
