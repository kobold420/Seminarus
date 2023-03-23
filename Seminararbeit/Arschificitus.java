package Seminararbeit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Arschificitus {
    public static void main(String[] args) {
        List<Item> Gegenstaende = new ArrayList<>();
        Gegenstaende.add(new Item(10, 4));
        Gegenstaende.add(new Item(3, 4));
        Gegenstaende.add(new Item(5, 2));
        Gegenstaende.add(new Item(7, 1));
        int Rucksackgroesse = 10;


        int[] Parent0 = new int[]{0, 1, 0, 1};
        int[] Parent1 = new int[]{1, 0, 1, 0};
        int[] Parent2 = new int[]{1, 1, 1, 1};
        int[] Parent3 = new int[]{0, 1, 1, 1};

        List<int[]> Gen0 = new ArrayList<>();
        Gen0.add(Parent0);
        Gen0.add(Parent1);
        Gen0.add(Parent2);
        Gen0.add(Parent3);


        System.out.println(Arrays.deepToString(BesteMann(Gen0, Gegenstaende, Rucksackgroesse, 10).toArray()));


    }

    public static List<int[]> BesteMann(List<int[]> Generation, List<Item> Gegenstaende, int Rucksackgroesse, int Anzahl) {
        List<int[]> Genus = Generation;
        for (int i = 0; i < Anzahl; i++) {
            Genus = NextGen(Genus, Gegenstaende, Rucksackgroesse);
        }
        List<int[]> BesteKombi = largest(Genus, Gegenstaende, Rucksackgroesse);
        return BesteKombi;
    }

    public static List<int[]> Mutationussus(List<int[]> Generation) {
        boolean var = new Random().nextInt(2) == 0;
        if (var) {
            int ran1 = new Random().nextInt(Generation.size() - 1);
            int ran2 = new Random().nextInt(Generation.get(ran1).length - 1);
            int[] Hello = Generation.get(ran1);
            if (Hello[ran2] == 1) {
                Hello[ran2] = 0;
                Generation.set(ran1, Hello);
            } else {
                Hello[ran2] = 1;
                Generation.set(ran1, Hello);

            }
        }
        return Generation;
    }

    public static List<int[]> NextGen(List<int[]> Generation, List<Item> Gegenstaende, int Rucksackgroesse) {
        List<int[]> nextGen = new ArrayList<>();
        nextGen.addAll(GenChild(Generation));
        nextGen.addAll(Wert(Generation, Gegenstaende, Rucksackgroesse));
        Mutationussus(nextGen);


        return nextGen;
    }

    public static List<int[]> GenChild(List<int[]> Generation) {
        List<int[]> nextGenChild = new ArrayList<>();
        if (Generation.size() % 2 == 0) {
            for (int i = 0; i < Generation.size(); i = i + 2) {
                nextGenChild.addAll(Child(Generation.get(i), Generation.get(i + 1)));
            }
        } else {
            for (int i = 0; i < Generation.size() - 1; i = i + 2) {
                nextGenChild.addAll(Child(Generation.get(i), Generation.get(i + 1)));
            }
            nextGenChild.add(Generation.get(Generation.size() - 1));
        }

        return nextGenChild;
    }
// Funktion die die Kombinationen bewertet und die zwei besten zurückgibt
    public static List<int[]> Wert(List<int[]> Gen, List<Item> Gegenstaende, int Rucksackgroesse) {
//Array in dem die Werte der Kombinationen gespeichert werden
        int value;
        int[] values = new int[Gen.size()];

        for (int i = 0; i < Gen.size(); i++) {

            value = 0;
            int weight = 0;
// Berechnet den Wert einer Kombi, indem für jedes Item der Wert und das Gewicht gezählt wird
            for (int j = 0; j < Gen.get(i).length; j++) {

                if (Gen.get(i)[j] == 1) {

                    value = (int) (Gegenstaende.get(j).getWert() + value);
                    weight = (int) (Gegenstaende.get(j).getGewicht() + weight);
                }

            }
//Wenn Kombination zu schwer ist wird der Wert zu -1 gesetzt
            if (weight > Rucksackgroesse) {
                value = -1;
            }
            values[i] = value;
        }
//Wert der zwei besten Kombinationen mit Hilfe von twolargest() werden in einem Array gespeichert

        int[] zweigroesten = twoLargest(values);

        List<int[]> beste = new ArrayList<>();
//Wert der Kombinationen werden den Kombinationen zugeordnet und zu einem Array hinzugefügt
        for (int i = 0; i < 2; i++) {

            for (int p = 0; p < Gen.size(); p++)
                if (values[p] == zweigroesten[i]) {
                    values[p] = 0;
                    beste.add(i, Gen.get(p));

                    break;
                }
        }

//Die zwei besten Kombinationen werden zurückgegeben
        return beste;
    }

    public static List<int[]> Child(int[] Parent1, int[] Parent2) {
        int[] P1P1 = new int[2];
        int[] P2P1 = new int[2];
//      Part Parent
        int[] P1P2 = new int[2];
        int[] P2P2 = new int[2];

        int Lp1p1 = P1P1.length;
        int Lp1p2 = P1P2.length;

        int Lp2p1 = P2P1.length;
        int Lp2p2 = P2P2.length;

//              (src   , src-offset  , dest , offset, count)

        System.arraycopy(Parent1, 0, P1P1, 0, P1P1.length);
        System.arraycopy(Parent1, P1P1.length, P2P1, 0, P2P1.length);

        System.arraycopy(Parent2, 0, P1P2, 0, P1P2.length);
        System.arraycopy(Parent2, P1P2.length, P2P2, 0, P2P2.length);


        int[] result1 = new int[Lp1p2 + Lp2p1];
        System.arraycopy(P1P1, 0, result1, 0, Lp1p1);
        System.arraycopy(P2P2, 0, result1, Lp1p1, Lp1p2);

        int[] result2 = new int[Lp1p1 + Lp2p2];
        System.arraycopy(P1P2, 0, result2, 0, Lp2p2);
        System.arraycopy(P2P1, 0, result2, Lp1p2, Lp1p2);

        List<int[]> result = new ArrayList<>();
        result.add(result1);
        result.add(result2);


        return result;
    }

    public static int[] twoLargest(int[] values) {
        int largestA = Integer.MIN_VALUE;
        int largestB = Integer.MIN_VALUE;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > largestA) {
                largestB = largestA;
                largestA = values[i];
            } else if (values[i] > largestB) {
                largestB = values[i];
            }
        }
        return new int[]{largestA, largestB};
    }
// Funktion die die Kombinationen bewertet und die zwei besten zurückgibt
    public static List<int[]> largest(List<int[]> Gen, List<Item> Gegenstaende, int Rucksackgroesse) {
        int value;
//Array in dem die Werte der Kombinationen gespeichert werden
        int[] values = new int[Gen.size()];

        for (int i = 0; i < Gen.size(); i++) {

            value = 0;
            int weight = 0;
// Berechnet den Wert einer Kombi, indem für jedes Item der Wert und das Gewicht gezählt wird
            for (int j = 0; j < Gen.get(i).length; j++) {

                if (Gen.get(i)[j] == 1) {

                    value = (int) (Gegenstaende.get(j).getWert() + value);
                    weight = (int) (Gegenstaende.get(j).getGewicht() + weight);
                }

            }
//Wenn Kombination zu schwer ist wird der Wert zu -1 gesetzt
            if (weight > Rucksackgroesse) {
                value = -1;
            }
//Wert der Kombinationen wird in einem Array gespeichert
            values[i] = value;
        }

// Beste Kombination wird zu einer Liste hinzugefügt
        List<int[]> beste = new ArrayList<>();
        for (int p = 0; p < Gen.size(); p++)
            if (values[p] == largest(values)) {

                beste.add(0, Gen.get(p));

                break;
            }
//Beste Kombination werden zurückgeben
        return beste;
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
}