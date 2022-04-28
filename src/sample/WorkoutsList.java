package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;


class WorkoutsList extends LinkedList<Workout> {
    LocalDateTime dateTime = LocalDateTime.now();


    public WorkoutsList() {
        super();
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = dateTime.format(formatter);
        return formatDateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    //compares titles
    public boolean equals(WorkoutsList workoutsList) {
        if (this == workoutsList) {
            return true;
        }
        for (int i = 0; i <= workoutsList.size(); i++) {
            if (this.get(i).getTitle().equals(workoutsList.get(i).getTitle())) {

                System.out.println("equals");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }






}
