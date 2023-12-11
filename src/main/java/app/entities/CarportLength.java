package app.entities;

public class CarportLength {

    private int id;
    private int length;


    public CarportLength(int id, int length) {
        this.id = id;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }
}
