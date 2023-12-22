package app.entities;

public class ShedLength {
    private int id;
    private int length;

    public ShedLength(int id, int length) {
        this.id = id;
        this.length = length;

    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }
}
