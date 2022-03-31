package sample;


import java.io.Serializable;

public class Workout implements Serializable {
    private String title;
    private int reps;
    private int sets;
    private int weight; // could be a double
    private int max;
    private static final long serialVersionUID = 1L;


    public String toString() {
        return "Workout: " +
                "\n \t title: " + title  +
                "\n \t reps: " + reps +
                "\n \t sets: " + sets +
                "\n \t weight: " + weight +
                "\n \t max: " + max;
    }

    // constructors
    public Workout(String title, int reps, int sets, int weight) {
        this.title = title;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;

    }

    //getter and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public int getMax() {

        return max;
    }

    // this is predetermined max
    public void setMax(int max) {
        this.max = max;
    }

    // this will be the one rep max for the weight of the workout i did today
    // **** this could be different 1 rep max from my actual max ****
    public int OneRepMax() {
        int oneRepMax = (int)((100 * this.weight) / (101.3 - 2.67123 * this.reps));

        return  oneRepMax;
    }



    public static void main(String[] args) {

    }// end of main class
}


