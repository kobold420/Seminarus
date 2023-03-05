package Seminararbeit;
import java.util.ArrayList;

public class Rucksack {
    public static void main(String[] args) {
        ArrayList Gegesnstaende = new ArrayList<String>();

        /*TODO: ob und string variabel
           hashmap
           stream?
         *  MAYBE gegenstände.add mit eingabe aufforderung*/


        //Liste der Gegenstände die zu klauen sind
        Gegesnstaende.add("Fernseher");
        Gegesnstaende.add("Rolex");
        Gegesnstaende.add("Computer");
        Gegesnstaende.add("Baum");
        Gegesnstaende.add("Handy");

        System.out.println("Was soll ich denn mitnehmen?");

        //Volumen
        int volfernseher = 10, volrolex = 1 , volcomp = 20, volhandy = 5;

        //Preis
        int prfernseher = 100, prrolex = 200, precomp = 100, prhandy = 40;

        //Volumen/Preis= Volumen Preis
        int werfernseher = prfernseher / volfernseher, werrolex = prrolex / volrolex, wercomp = precomp / volcomp, werhandy = prhandy / volhandy;
        System.out.println(prrolex/volrolex);
        //Rucksackgröße
        int rucksack = 20;

        int fernseher = 0, rolex = 0, computer = 0, handy = 0;
        //Was ist in dem Haus zu finden
        for (Object item:Gegesnstaende) {
            if ("Fernseher".equals(item)) {
                System.out.println("Ein Fernsher?");
                ++fernseher;
            } else if ("Rolex".equals(item)) {
                System.out.println("Eine Rolex?");
                ++rolex;
            } else if ("Computer".equals(item)) {
                System.out.println("Ein Computer?");
                ++computer;
            } else if ("Handy".equals(item)) {
                System.out.println("Ein Handy?");
                ++handy;
            } else {
                System.out.println("Was ist denn das? Ein/e " + item + "?");
            }
        }

        while(rucksack>=0) {
            //Was ist verfügbar
            int[] max = {werfernseher * verfuebar(fernseher) * platz(rucksack, volfernseher), werrolex * verfuebar(rolex) * platz(rucksack, volrolex), wercomp * verfuebar(computer) * platz(rucksack, volcomp), werhandy * verfuebar(handy) * platz(rucksack, volhandy)};

            if (largest(max) == werfernseher) {
                rucksack = rucksack - volfernseher;
                fernseher = fernseher - 1;
                System.out.println("Ich nehm den Fernseher");

            } else if (largest(max) == werrolex) {
                rucksack = rucksack - volrolex;
                rolex = rolex - 1;
                System.out.println("Ich nehm die Rolex");

            } else if (largest(max) == wercomp) {
                rucksack = rucksack - volcomp;
                computer = computer - 1;
                System.out.println("Ich nehm den Computer");

            } else if (largest(max) == werhandy) {
                rucksack = rucksack - volhandy;
                handy = handy - 1;
                System.out.println("Ich nehm das Handy");

            } else if (largest(max)==0) {
                System.out.println("Des wars ich hab des teuerste");
                break;

            }

            System.out.println("Ich habe noch " + rucksack + " Plaetze");

        }
    }

    public static int largest(int[] arr) {
        int c;
        // Initialize maximum element
        int max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (c = 1; c < arr.length; c++)
            if (arr[c] > max)
                max = arr[c];
        return max;
    }

    public static int verfuebar(int c) {
        int available = 0;
        if (c >= 1) {
            available = 1;
        }
        return available;
    }

    public static int platz(int rucksack,int große){
        int available = 0;

        if(rucksack-große>=0){
            available = 1;
        }
        return available;
    }

}