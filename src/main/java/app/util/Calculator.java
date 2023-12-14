package app.util;

import app.entities.Carport;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.bcel.generic.ARETURN;

import java.util.ArrayList;
import java.util.List;


    public class Calculator {
        Carport carport;
        List<Integer> material = new ArrayList<Integer>();


        public int calculateArea(int width, int length) {
            return width * length;


        }

        public int numberOfPosts(int carportlength) {
            int distanceBetweenPosts = 240;

            int totalpost = 0;
            if (carportlength - 30 <= 600) {
                totalpost = 2 * 2;
            } else if (carportlength - 30 < 600 && carportlength <= 800) {
                totalpost = 3 * 2;
            } else {
                if (carportlength - 30 < 800) {
                    totalpost = (int) Math.ceil(carportlength / distanceBetweenPosts);
                    totalpost = totalpost * 2;


                }
            }

            return totalpost;


        }

        public static int spaerAmount(int carportLength) {
            //spær

            int numberOfRafts = (int) Math.ceil((double) carportLength / 55);


            if( carportLength > 600) {
                numberOfRafts = (numberOfRafts*2);
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

        public int screwPost(int carportLength) {
            int screwsPerPost = 10;
            int totalPosts = numberOfPosts(carportLength);
            return totalPosts * screwsPerPost;
        }

        public int screwSpaer(int length, int width) {
            int screwsPerPost = 20;
            int totalPosts = spaerAmount(length);
            return totalPosts * screwsPerPost;
        }

        public int beslagPost(int length) {
            int numberOfposts = numberOfPosts(length);

            int beslagPost = numberOfposts;


            return beslagPost;
        }





     public int beslagspaer(int length){

             int numberOfbeslag = spaerAmount(length);

             numberOfbeslag *= 2;

             return numberOfbeslag;
         }



     }
//en stolbe et beslag og 10 skruer
//et spær 2 beslag og 20 skruer

