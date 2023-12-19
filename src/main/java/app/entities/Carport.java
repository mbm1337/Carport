package app.entities;


public class Carport {
    private int id;
    public static int counter = 0;
    private int width;
    private int length;
    private int height;
    private Shed shed;
    private String roof;

    private boolean shedside;




    public boolean isShedside() {
        return shedside;
    }

    public void setShedside(boolean shedside) {
        this.shedside = shedside;
    }

    public Carport(int width, int length, int height) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
    }


    public Carport(int width, int length, int height, String roof) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roof = roof;


    }


    public Carport(int length) {
        this.length = length;
    }

    public Carport(int width, int length, int height, Shed shed) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.shed = shed;
    }
    public Carport(int width, int length, int height,String roof, Shed shed) {
        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.roof = roof;
        this.shed = shed;
    }

    public Carport(int width, int length, int height, boolean shedside, Shed shed) {

        this.id = counter++;
        this.width = width;
        this.length = length;
        this.height = height;
        this.shed = shed;
        this.shedside = shedside;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getRoof() {
        return roof;
    }

    public Shed getShed() {
        return shed;
    }

}
