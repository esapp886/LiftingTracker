package sample;
public class Help {

    private String helper;

    public Help(String helper) {
        this.helper = helper;
    }
    public Help(){

    }

    public String about(){

        return "\nClick on new workouts button. Then create your workout after working out and then add the " +
                " workout by clicking the add workout button which will then save your workout info. " +
                "Make sure to title your workout with a capital letter. Ex: Bench, Squat, Deadlift, Overhead Press" +
                ". To see your workout sorted, click the sort button once your workout is uploaded and then press the" +
                " return button and go back to the view past workouts. Now your workout will be sorted based on the title.";

    }

}
