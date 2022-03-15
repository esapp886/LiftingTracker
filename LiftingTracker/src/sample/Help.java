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
                "Make sure to title your workout with a capital letter. Ex: Bench, Squat, Deadlift, Overhead Press";

    }

}
