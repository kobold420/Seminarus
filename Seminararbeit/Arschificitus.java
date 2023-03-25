package Seminararbeit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Arschificitus {
    public static void main(String[] args) {
//      Liste von Gegenstaende
        List<Item> Gegenstaende = new ArrayList<>();
        Gegenstaende.add(new Item(10, 4));
        Gegenstaende.add(new Item(3, 4));
        Gegenstaende.add(new Item(5, 2));
        Gegenstaende.add(new Item(7, 1));
        Gegenstaende.add(new Item(3, 5));
        Gegenstaende.add(new Item(1, 10));

        int Rucksackgroesse = 10;

//      Vorgegebene Kombinationen
        int[] Parent0 = new int[]{0, 1, 0, 1, 0, 0};
        int[] Parent1 = new int[]{1, 0, 1, 0, 1, 0};
        int[] Parent2 = new int[]{1, 1, 1, 1, 0, 0};
        int[] Parent3 = new int[]{0, 1, 1, 1, 1, 1};

//      Liste von vorgegebenen Kombinationen
        List<int[]> Gen0 = new ArrayList<>();
        Gen0.add(Parent0);
        Gen0.add(Parent1);
        Gen0.add(Parent2);
        Gen0.add(Parent3);

        System.out.println(Arrays.deepToString(BesteKombi(Gen0, Gegenstaende, Rucksackgroesse, 10).toArray()));


    }

    // Methode erstellt "neue Generation" x-Mal unhd gibt beste Kombination aus
    public static List<int[]> BesteKombi(List<int[]> Generation, List<Item> Gegenstaende, int Rucksackgroesse, int Anzahl) {
        List<int[]> Genus = Generation;
//      "Neue" Generation wird erstellt
        for (int i = 0; i < Anzahl; i++) {
            Genus = NextGen(Genus, Gegenstaende, Rucksackgroesse);
        }
//      Beste Kombi der Liste
        return Beschtekombi(Genus, Gegenstaende, Rucksackgroesse);
    }

    //  Methode laesst zufaelige Kombination mutieren
    public static List<int[]> Mutation(List<int[]> Generation) {
//      Warscheinlichkeit, dass Mutation stattfindet
        boolean var = new Random().nextInt(2) == 0;
        if (var) {
//          Es wird zufaellig eine Kombination ausgesucht
            int RandomKombi = new Random().nextInt(Generation.size() - 1);
//          Es wird zufaellig ein Index aus der Kombination ausgesucht
            int RandomIndex = new Random().nextInt(Generation.get(RandomKombi).length - 1);
            int[] RanKombi = Generation.get(RandomKombi);
//          Aus einer "0" wird eine "1" und aus einer "1" wird eine "0"
            if (RanKombi[RandomIndex] == 1) {
                RanKombi[RandomIndex] = 0;
                Generation.set(RandomKombi, RanKombi);
            } else {
                RanKombi[RandomIndex] = 1;
                Generation.set(RandomKombi, RanKombi);

            }
        }
//      Die mutierte Generation wird zurueckgegeben
        return Generation;
    }

    //  Methode vereeint GenChild() und Wert() und Mutation()
    public static List<int[]> NextGen(List<int[]> Generation, List<Item> Gegenstaende, int Rucksackgroesse) {
        List<int[]> nextGen = new ArrayList<>();
        nextGen.addAll(GenChild(Generation));
        nextGen.addAll(Wert(Generation, Gegenstaende, Rucksackgroesse));
        Mutation(nextGen);

//      "Neue" Generation wird zurueckgegeben
        return nextGen;
    }

    //  Methode erstellt alle "Children" einer Generation
    public static List<int[]> GenChild(List<int[]> Generation) {
        List<int[]> nextGenChild = new ArrayList<>();
//      Wenn die Anzahl der "Eltern" gerade ist, werden die "Children" "normal" gebildet
        if (Generation.size() % 2 == 0) {
            for (int i = 0; i < Generation.size(); i = i + 2) {
                nextGenChild.addAll(Child(Generation.get(i), Generation.get(i + 1)));
            }
//      Wenn die Anzahl der "Eltern" ungerade ist, werden die "Children" gebildet unter vernachlaessigung des letzten "Elternteils"
        } else {
            for (int i = 0; i < Generation.size() - 1; i = i + 2) {
                nextGenChild.addAll(Child(Generation.get(i), Generation.get(i + 1)));
            }
            nextGenChild.add(Generation.get(Generation.size() - 1));
        }

        return nextGenChild;
    }

    //  Methode, die die Kombinationen bewertet und die zwei besten mithilfe von twolargest() zurueckgibt
    public static List<int[]> Wert(List<int[]> Gen, List<Item> Gegenstaende, int Rucksackgroesse) {

        int value;
//      values enthaelt den Wert jeder Kombination
        int[] values = new int[Gen.size()];

        for (int i = 0; i < Gen.size(); i++) {

            value = 0;
            int weight = 0;

//          Berechnet den Wert einer Kombi, indem fuer jedes Item der Wert und das Gewicht gezaehlt wird
            for (int j = 0; j < Gen.get(i).length; j++) {

                if (Gen.get(i)[j] == 1) {

                    value = (int) (Gegenstaende.get(j).getWert() + value);
                    weight = (int) (Gegenstaende.get(j).getGewischt() + weight);
                }

            }
//          Wenn Kombination zu schwer ist, wird der Wert zu -1 gesetzt
            if (weight > Rucksackgroesse) {
                value = -1;
            }
            values[i] = value;
        }
//      Wert der zwei besten Kombinationen mithilfe von twolargest() werden in einem Array gespeichert
        int[] zweigroesten = twoLargest(values);

        List<int[]> beste = new ArrayList<>();
//      Wert der Kombinationen werden den Kombinationen zugeordnet und zu einem Array hinzugefuegt
        for (int i = 0; i < 2; i++) {

            for (int p = 0; p < Gen.size(); p++)
                if (values[p] == zweigroesten[i]) {
                    values[p] = 0;
                    beste.add(i, Gen.get(p));

                    break;
                }
        }

//      Die zwei besten Kombinationen werden zurueckgegeben
        return beste;
    }

    //  Methode, die beide "Parents" an zufaelliger Stelle in zwei Arrays "schneidet" und neukombiniert und diese dann zurueckgibt
    public static List<int[]> Child(int[] Parent1, int[] Parent2) {
//  Es wird ein zufaellige Stelle ausgesucht, um die Arrays zu vertauschen, wenn diese Stelle 0 ist, wird sie zu 1, da sonst keine veraenderung stattfindet
//  ran ist somit die Laenge der 1. Haelfte des 1. Parent
        int ran = new Random().nextInt(Parent1.length - 1);
        if (ran == 0) {
            ran = ran + 1;
        }

//      ran ist die Laenge der 1. Haelfte des Parent
//      -> ran + .length -ran = parrent.length
        int[] Part1Parent1 = new int[ran];
        int[] Part2Parent1 = new int[Parent1.length - ran];

        int[] Part1Parent2 = new int[ran];
        int[] Part2Parent2 = new int[Parent2.length - ran];

        int Lpart1parent1 = Part1Parent1.length;
        int Lpart1parent2 = Part1Parent2.length;

        int Lpart2parent1 = Part2Parent1.length;
        int Lpart2parent2 = Part2Parent2.length;


//              (src   , src-offset  , dest , offset, count)

//      1. Haelfte des 1. Parent wir in Part1Parent1 kopiert
        System.arraycopy(Parent1, 0, Part1Parent1, 0, Part1Parent1.length);
//      2. Haelfte des 1. Parent wir in Part2Parent1 kopiert
        System.arraycopy(Parent1, Part1Parent1.length, Part2Parent1, 0, Part2Parent1.length);


//      1. Haelfte des 1. Parent wir in Part1Parent2 kopiert
        System.arraycopy(Parent2, 0, Part1Parent2, 0, Part1Parent2.length);
//      2. Haelfte des 1. Parent wir in Part2Parent2 kopiert
        System.arraycopy(Parent2, Part1Parent2.length, Part2Parent2, 0, Part2Parent2.length);

//      result1 besteht aus Part1parent1 und Part2Parent2
        int[] result1 = new int[Lpart1parent2 + Lpart2parent1];
//      Beide Haelften werden in result1 kopiert
        System.arraycopy(Part1Parent1, 0, result1, 0, Lpart1parent1);
        System.arraycopy(Part2Parent2, 0, result1, Lpart1parent1, Lpart2parent2);

//      result1 besteht aus Part1parent2 und Part2Parent1
        int[] result2 = new int[Lpart1parent1 + Lpart2parent2];
//      Beide Haelften werden in result2 kopiert
        System.arraycopy(Part1Parent2, 0, result2, 0, Lpart1parent2);
        System.arraycopy(Part2Parent1, 0, result2, Lpart1parent2, Lpart2parent1);

//      Beide neukombinationen werden zu results hinzugefuegt
        List<int[]> results = new ArrayList<>();
        results.add(result1);
        results.add(result2);


        return results;
    }

    //  Methode gibt die Zweigroeßtenwerte eines Arrays zurueck
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

    // Methode, die die Kombinationen bewertet und die beste zurueckgibt mithilfe von largest()
    public static List<int[]> Beschtekombi(List<int[]> Gen, List<Item> Gegenstaende, int Rucksackgroesse) {
        int value;

        int[] values = new int[Gen.size()];

        for (int i = 0; i < Gen.size(); i++) {

            value = 0;
            int weight = 0;

//          Berechnet den Wert einer Kombi, indem fuer jedes Item der Wert und das Gewicht gezaehlt wird
            for (int j = 0; j < Gen.get(i).length; j++) {

                if (Gen.get(i)[j] == 1) {

                    value = (int) (Gegenstaende.get(j).getWert() + value);
                    weight = (int) (Gegenstaende.get(j).getGewischt() + weight);
                }

            }
//          Wenn Kombination zu schwer ist, wird der Wert zu -1 gesetzt
            if (weight > Rucksackgroesse) {
                value = -1;
            }
//          Wert der Kombinationen wird in einem Array gespeichert
            values[i] = value;
        }

//      Beste Kombination wird zu einer Liste hinzugefuegt
        List<int[]> beste = new ArrayList<>();
        for (int p = 0; p < Gen.size(); p++)
            if (values[p] == largest(values)) {

                beste.add(0, Gen.get(p));

                break;
            }
//      Beste Kombination wird zurueckgeben
        return beste;
    }

    //  Methode gibt die groeßte Zahl in einem Array zurueck
    public static int largest(int[] arr) {
        int c;
        int max = arr[0];

        for (c = 1; c < arr.length; c++)
            if (arr[c] > max)
                max = arr[c];
        return max;
    }
}