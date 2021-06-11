package WindModel;

import java.awt.*;
import java.util.LinkedList;

// will update objects in the room, update and render them
public class Handler {

    LinkedList<Object> object = new LinkedList<Object>();

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            Object tempObject = object.get(i); // getting the index of the object we are in
            tempObject.tick();
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < object.size(); i++) {
            Object tempObject = object.get(i); // getting the index of the object we are in
            tempObject.render(g);
        }
    }

    public void addObject(Object addObject){
        this.object.add(addObject);
    }

    public void removeObject(Object removeObject){
        this.object.remove(removeObject);
    }


}
