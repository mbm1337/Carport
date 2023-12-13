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
        public int numberOfRaft(int carportLength, int carportWidth) {
            int distanceBetweenRafts = 55;

            int numberOfRafts = carportLength / distanceBetweenRafts;

            if (carportLength % distanceBetweenRafts != 0) {
                numberOfRafts++;
            }

            return numberOfRafts;
        }


        public static int beamAmount(int carportLength) {
            int numberOfrem = 2;

            int additionalRem = 0;

            if (carportLength > 600) {
                int extraLength = carportLength - 600;
                int extraRem = extraLength / 200;
                additionalRem = extraRem;
            }

            return numberOfrem + additionalRem;
        }

        public int numberOfScrews (){
            return numberOfScrews();
        }
        public void calculateMaterialsList(int carportLength, int carportWidth) {

            int numberOfPosts = numberOfPosts(carportLength);
            material.add(numberOfPosts);

            int numberOfRafts = numberOfRaft(carportLength, carportWidth);
            material.add(numberOfRafts);

            int numberOfBeams = beamAmount(carportLength);

            material.add(numberOfBeams);


        }



    }

