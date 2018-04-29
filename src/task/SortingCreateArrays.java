package task;

import java.util.Random;

import factory.Task;

public class SortingCreateArrays extends Task {

    private static final int MAX = 100000;

    private static Random rand;
    public int[] array;

    public SortingCreateArrays() {
        rand = new Random();
        array = new int[MAX];
    }

    @Override
    public String executeTask() {
        for (int i = 0; i < MAX; i++) {
            array[i] = rand.nextInt(5000);
        }
        response = array;
        return "arrays " + MAX + " positions.";
    }
}
