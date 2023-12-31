package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    Carport carport;
    List<Integer> material = new ArrayList<Integer>();


    public int calculateArea(int width, int length) {
        return width* length;


    }

    public int numberOfPosts(int carportlength) {
        int distanceBetweenPosts = 240;
        int totalpost = 0;
        if (carportlength - 30 <= 600) {
            totalpost = 2 * 2;
        } else if (carportlength- 30 < 600 && carportlength <= 800) {
            totalpost = 3 * 2;
        } else {
            if (carportlength - 30 < 800) {
                totalpost = (int) Math.ceil(carportlength/ distanceBetweenPosts );
                totalpost= totalpost *2;


                }
            }

        return totalpost;


    }
    public static int numberOfraft(int carportLength, int carportWidth) {
        //spær

        int numberOfRafts = (int) Math.ceil((double) carportLength / 55);
        return numberOfRafts;
    }



    public int beamAmount(int carportLength) { //rem
        int totalBeam =0;
        if (carportLength <= 600) {
             totalBeam = 2;
        }else{
            totalBeam =2*2;
        }
        return totalBeam;
    }
    public int numberOfScrews (){
        return numberOfScrews();
    }
    public void calculateMaterialsList(int carportLength, int carportWidth) {

        int numberOfPosts = numberOfPosts(carportLength);
        material.add(numberOfPosts);

        int numberOfRafts = numberOfraft(carportLength, carportWidth);
        material.add(numberOfRafts);

        int numberOfBeams = beamAmount(carportLength);

        material.add(numberOfBeams);


    }



    }