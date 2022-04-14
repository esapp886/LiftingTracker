package sample;

class QuickSort{

    //Method for swapping the elements
    static void swap(WorkoutsList arr, int i, int j) {

       Workout temp = arr.get(j);
        arr.set(j, arr.get(i));
        arr.set(i, temp);
    }

    /* This function takes last element as pivot, places
       the pivot element at its correct position in sorted
       array, and places all smaller (smaller than pivot)
       to left of pivot and all greater elements to right
       of pivot */
    static int partition(WorkoutsList arr, int low, int high) {

        // pivot
        Workout pivot = arr.get(high);

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for(int j = low; j <= high - 1; j++) {

            // If current element is smaller
            // than the pivot
            if (arr.get(j).getTitle().compareTo(pivot.getTitle()) < 0) {

                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }


    static WorkoutsList quickSort(WorkoutsList arr, int low, int high) {
        if (low < high) {

            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
        return arr;
    }


}
