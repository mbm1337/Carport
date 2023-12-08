package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    Carport carport;
    List<Materials> material = new ArrayList<>();


    public int calculateArea() {
        return carport.getLength() * carport.getWidth();


    }

    public int NumberOfPosts() {
        int distanceBetweenposts = 240;
        int post = 0;

        if (carport.getLength()-15*2 <= distanceBetweenposts) {
            post = 2 * 2;
        } else if (carport.getLength()-15*2 >= 240 && carport.getLength() <= 500) {
            post = 3 * 3;
        } else {
            post = 4*4;
        }

        return post;
    }

}





