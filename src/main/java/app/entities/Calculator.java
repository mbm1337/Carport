package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    Carport carport;
    List<Integer> material = new ArrayList<Integer>();


    public int calculateArea(int width, int length) {
        return width * length;
    }

    public int numberOfPosts(int carportLength) {
        int distanceBetweenPosts = 240;
        int totalPosts = 0;

        if (carportLength - 30 <= 600) {
            totalPosts = 4; // Assuming 2 posts on each side
        } else if (carportLength - 30 <= 800) {
            totalPosts = 6; // Assuming 3 posts on each side
        } else {
            totalPosts = (int) Math.ceil((carportLength - 30) / distanceBetweenPosts) * 2;
        }

        return totalPosts;
    }

    public static int numberOfRafts(int carportLength, int carportWidth) {
        int numberOfRafts = (int) Math.ceil((double) carportLength / 55);

        if (carportWidth > 600) {
            numberOfRafts *= 2;
        }

        return numberOfRafts;
    }

    public int beamAmount(int carportLength) {
        return (carportLength <= 600) ? 2 : 4; // Assuming 2 beams for carportLength <= 600, else 4
    }

    public int numberOfScrews() {
        // Provide the implementation for the number of screws calculation
        return 0;
    }

    public void calculateMaterialsList(int carportLength, int carportWidth) {
        material.add(numberOfPosts(carportLength));
        material.add(numberOfRafts(carportLength, carportWidth));
        material.add(beamAmount(carportLength));
    }
}
