package Seminararbeit;

public class Item {
    private final double Wert;
    private final double Gewischt;

    private double Qou;

    public Item(double Wert, double Gewischt) {
        this.Wert = Wert;
        this.Gewischt = Gewischt;
    }

    public double getWert() {
        return Wert;
    }

    public double getGewischt() {
        return Gewischt;
    }

    public double getQou() {
        if (Gewischt != 0) {
            Qou = Wert / Gewischt;
        }
        return Qou;
    }
}