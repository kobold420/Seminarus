package Seminararbeit;

import java.util.ArrayList;
import java.util.List;

public class KnapsackExample {
    public static void main(String[] args) {
        // Erstelle eine Liste von Gegenständen
        List<Item> items = new ArrayList<>();
        items.add(new Item(10, 5));  // Gegenstand mit Wert 10 und Gewicht 5
        items.add(new Item(20, 4));  // Gegenstand mit Wert 20 und Gewicht 4
        items.add(new Item(30, 6));  // Gegenstand mit Wert 30 und Gewicht 6

        int maxWeight = 10;  // Maximal zulässiges Gewicht

        // Löse das Knapsackproblem und hole den optimalen Nutzen
        int optimalValue = knapsack(items, maxWeight);
        System.out.println("Optimaler Nutzen: " + optimalValue);

    }

    public static int knapsack(List<Item> items, int maxWeight) {
        // Erstelle eine Tabelle zur Speicherung der Lösungen für jedes Unterproblem
        // Die Zeilen der Tabelle entsprechen den Gegenständen und die Spalten entsprechen dem Gewicht
        // Die Tabelle wird mit 0 initialisiert
        int[][] table = new int[items.size() + 1][maxWeight + 1];

        // Iteriere über jeden Gegenstand
        for (int i = 1; i <= items.size(); i++) {
            // Hole den Wert und das Gewicht des aktuellen Gegenstands
            Item item = items.get(i - 1);
            double value = item.getWert();
            double weight = item.getGewicht();
            //NUR ZUM AUSGEBEN DER TABELLE
            for (int o = 0; o < table.length; o++) {
                for (int j = 0; j < table[o].length; j++) {
                    System.out.print(table[o][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

            // Iteriere über jedes mögliche Gewicht von 0 bis max_weight
            for (int w = 0; w <= maxWeight; w++) {
                // Wenn das Gewicht des aktuellen Gegenstands größer als das aktuelle Gewicht ist,
                // kann der Gegenstand nicht ausgewählt werden, daher bleibt die beste bisher gefundene
                // Lösung für dieses Unterproblem unverändert
                if (weight > w) {
                    table[i][w] = table[i - 1][w];
                } else {
                    table[i][w] = (int) Math.max(table[i - 1][w], table[i - 1][(int) (w - weight)] + value);
                }
            }
        }

        return table[items.size()][maxWeight];
    }
}

