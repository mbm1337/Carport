package app.util;

import app.entities.Carport;



public class Calculator {
    Carport carport;


    public int calculateArea(int width, int length) {
        return width * length;

    }

    public int numberOfPosts(int carportlength) {
        int distanceBetweenPosts = 240;
        int totalpost = 0;
        if (carportlength <= 570 ) {
            totalpost = 2 * 2;
        } else if (carportlength >= 600 && carportlength <= 800) {
            totalpost = 3 * 2;
        } else {
            if (carportlength > 800) {
                totalpost = (int) Math.ceil(carportlength / distanceBetweenPosts);
                totalpost = totalpost * 2;
            }
        }

        return totalpost;
    }

    public static int spaerAmount(int carportLength) {
        //spær
        int numberOfRafts = (int) Math.ceil((double) carportLength / 55);
        if (carportLength >= 600) {
            int i = numberOfRafts + 1;
        }
        return numberOfRafts;
    }

    public static int beamAmount(int carportLength) {
        int numberOfrem = 2;

        int additionalRem = 0;
        if (carportLength > 600) {
            numberOfrem = 3;
        }

        return numberOfrem + additionalRem;
    }

    public int screwPost(int carportLength) {
        int screwsPerPost = 10;
        int totalPosts = numberOfPosts(carportLength);
        return totalPosts * screwsPerPost;
    }

    public int screwSpaer(int length) {
        int screwsPerSpaer= 20;
        int totalspaer = spaerAmount(length);
        return totalspaer * screwsPerSpaer;
    }

    public int beslagPost(int length) {
        int numberOfposts = numberOfPosts(length);

        int beslagPost = numberOfposts;


        return beslagPost;
    }


    public int beslagspaer(int length) {

        int numberOfbeslag = spaerAmount(length);

        numberOfbeslag *= 2;

        return numberOfbeslag;
    }

    public int shedArea(int length, int width) {
        return length * width;

    }

    public int beklaedning(int shedLength, int shedWidth) {
        int numberOfBraedtPerHeight = 27;
        int lengthOfbraedt = 210;
        int total = 0;

        if (shedWidth <= lengthOfbraedt && shedLength <= lengthOfbraedt) {
            total = 27 * 4;
        } else if (shedWidth >= lengthOfbraedt && shedLength >= lengthOfbraedt) {
            int subtractWidthFromLengthOfBraedt = shedWidth - lengthOfbraedt;
            int subtractLengthFromLengthOfBraedt = shedLength - lengthOfbraedt;

            int multiplyWidth = subtractWidthFromLengthOfBraedt * 27;
            int multiplyLength = subtractLengthFromLengthOfBraedt * 27;

            int extraBraedtWidth = (int) Math.ceil((double) multiplyWidth / 210) * 2 + (27 * 2);
            int extraBraedtLength = (int) Math.ceil((double) multiplyLength / 210) * 2 + (27 * 2);

            total = extraBraedtWidth + extraBraedtLength;
        }

        return total;
    }

    public int numberOfStolperPerSkur(int shedlength, int shedwidth) {
        int distanceBetweenPosts = 210;


        int totalpost = 0;

        if (shedlength <= 500 && shedlength <= 500) {
            totalpost = 2 * 4;
        } else {
            if (shedwidth <= 600 && shedwidth <= 800 && shedlength <= 600 && shedlength <= 800) {
                totalpost = 3 * 4;

            }
        }

        return totalpost;
    }
}

