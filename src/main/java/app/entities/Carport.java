package app.entities;

public class Carport {
    private int id;
    public static int counter = 0;
    private int width;
    private int length;
    private int height;
    private Shed shed;

    private boolean trapezroof;


    public Carport(int width, int length, int height, boolean trapezroof) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.trapezroof = trapezroof;


    }

    public Carport(int width, int length, int height,boolean trapezroof ,Shed shed) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.trapezroof = trapezroof;
        this.shed = shed;
    }

}
