package sample;

public class BubbleSort {
        static int MAX = 100;

        public WorkoutsList sortStrings(WorkoutsList arr, int n) {
            Workout temp = null;

            // Sorting strings using bubble sort
            for (int j = 0; j < n - 1; j++) {
                for (int i = j + 1; i < n; i++) {
                    if (arr.get(j).getTitle().compareTo(arr.get(i).getTitle()) > 0) {
                        temp = arr.get(j);
                        arr.set(j, arr.get(i));
                        arr.set(i, temp);
                    }
                }
            }
            return arr;
        }

}
