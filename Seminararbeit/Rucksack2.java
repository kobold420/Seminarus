package Seminararbeit;

import java.util.ArrayList;
import java.util.List;
public class Rucksack2 {
    public static void main(String[] args) {
        List<Item> Gegenstände = new ArrayList<>();
        Gegenstände.add(new Item(10, 4));
        Gegenstände.add(new Item(3, 4));
        Gegenstände.add(new Item(5, 2));
        Gegenstände.add(new Item(7, 1));
        Gegenstände.add(new Item(7, 10));
        System.out.println("Was soll ich denn mitnehmen?");

        //Rucksackgroeße
        double rucksack = 20;
        double Inhalt = 0;
        double[] max = new double[Gegenstände.size()];


        while (Gegenstände.size()!=0) {
            for (int i = 0; i <= Gegenstände.size() - 1; i++) {
                Item item = Gegenstände.get(i);
                if(rucksack - item.getGewicht() <0){
                    Gegenstände.remove(i);}

                double Qou = item.getQou();
                max[i] = Qou;

                for (int c = 0; c <= Gegenstände.size() - 1; c++) {
                    if (max[c] == largest(max)) {
                        Item Wertvollst = Gegenstände.get(c);
                        double Gewicht = Wertvollst.getGewicht();
                        double Wert = Wertvollst.getWert();

                            rucksack = rucksack - Gewicht;
                            Inhalt = Inhalt + Wert;

                            Gegenstände.remove(c);


                            System.out.println("Ich habe noch Platz:");
                            System.out.println(rucksack);

                            System.out.println("Soviel Wert ist der Rucksack:");
                            System.out.println(Inhalt);

                            System.out.println("So viele Gegenstaende gibt es noch:");
                            System.out.println(Gegenstände.size());
                            System.out.println();
                        }
                        }
                    }
                }
            }




    public static double largest(double[] arr) {
        int c;
        // Initialize maximum element
        double max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (c = 1; c < arr.length; c++)
            if (arr[c] > max)
                max = arr[c];
        return max;
    }



}

