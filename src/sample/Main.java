package sample;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;


public class Main extends Application {
    static WorkoutsList workoutList = new WorkoutsList();
    static WorkoutsList sortedWorkoutList = new WorkoutsList();
    static Scene mainPageScene, maxesScene, newWorkoutScene, viewWorkoutsScene, helpScene, compareScene;
    QuickSort qs = new QuickSort();
    static Queue<Workout> queue = new WorkoutsList();

    static Label labelCounter = new Label();
    static Label label = new Label();
    static Label label2 = new Label();
    static Label label3 = new Label();
    static Label label4 = new Label();
    static Label label5 = new Label();

    static VBox viewWorkoutsLayout = new VBox();
    static Stage primaryStage;

    static Button returnButton3 = new Button("Return");
    static Button compareButton = new Button("Compare Workouts");
    static Button sortButton = new Button("Sort");
    static Button nextButton = new Button("Next");
    static Button findHighestMaxButton = new Button("Highest Max");
    Hashtable<Integer, Workout> workoutHashTable
            = new Hashtable<Integer, Workout>();

    static Alert alert = new Alert(Alert.AlertType.INFORMATION);
    static BinarySearchTree  bst = new BinarySearchTree();

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        // Call help class
        Help help = new Help();

        // Main Page scene buttons
        //image URLS
        ImageView maxImage = new ImageView("https://img.icons8.com/color/50/000000/weightlift.png");
        ImageView newWorkoutImage = new ImageView("https://img.icons8.com/ios/50/000000/flex-biceps.png");
        ImageView viewWorkoutImage = new ImageView("https://img.icons8.com/fluency/48/000000/physical-gallery.png");
        ImageView helpImage = new ImageView("https://img.icons8.com/emoji/48/000000/question-mark-emoji.png");

        Label label1 = new Label("Welcome to the Lifting App. What would you like to do?");
        label1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        Button maxButton = new Button("View your current Maxes");
        maxButton.setGraphic(maxImage);
        Button workoutButton = new Button("New workout");
        workoutButton.setGraphic(newWorkoutImage);
        Button viewWorkouts = new Button("Load workouts");
        viewWorkouts.setGraphic(viewWorkoutImage);
        Button helpButton = new Button("Help");
        helpButton.setGraphic(helpImage);


        //main page scene

        VBox mainPage = new VBox(20);
        mainPage.setStyle("-fx-background-color: #ffffff");
        mainPage.getChildren().addAll(label1, maxButton, workoutButton, viewWorkouts, helpButton);
        mainPageScene = new Scene(mainPage, 500, 500);


        //Maxbutton scene
        int benchCurrentMax = 225;
        int squatCurrentMax = 300;
        int deadliftCurrentMax = 360;
        int ohpCurrentMax = 135;

        Button returnButton1 = new Button("Return");
        returnButton1.setOnAction(e -> primaryStage.setScene(mainPageScene));
        VBox maxesLayout = new VBox();
        maxesLayout.setStyle("-fx-background-color: #ffffff");
        maxesScene = new Scene(maxesLayout, 500, 500);
        Text maxesText = new Text("Your maxes are: \n" +
                " Bench: " + benchCurrentMax + "\n" +
                " Squat: " + squatCurrentMax + "\n" +
                " DeadLift: " + deadliftCurrentMax + "\n" +
                " Overhead Press: " + ohpCurrentMax);
        maxesText.setWrappingWidth(500);
        maxesLayout.getChildren().add(maxesText);
        maxesLayout.getChildren().addAll(returnButton1);

        maxesText.setTextAlignment(TextAlignment.JUSTIFY);
        maxButton.setOnAction(e -> primaryStage.setScene(maxesScene));
        //********************************End Of Max Scene*******************************************************


        // New Workout Scene
        Button returnButton2 = new Button("Return");
        returnButton2.setOnAction(e -> primaryStage.setScene(mainPageScene));

        //add workout button
        Button addWorkoutButton = new Button("Add Workout");

        Button saveButton = new Button("Save Workouts");


        VBox newWorkoutLayout = new VBox();
        newWorkoutLayout.setStyle("-fx-background-color: #ffffff");
        newWorkoutScene = new Scene(newWorkoutLayout, 500, 500);
        Label newTitleLabel = new Label("What is your workout?");
        TextField newTitleInput = new TextField();

        Label newRepLabel = new Label("How many Reps completed?");
        TextField newRepInput = new TextField();

        Label newSetLabel = new Label("How many Sets completed?");
        TextField newSetInput = new TextField();

        Label newWeightLabel = new Label("How much weight did you lift?");
        TextField newWeightInput = new TextField();

        newWorkoutLayout.getChildren().addAll(newTitleLabel, newTitleInput, newRepLabel, newRepInput, newSetLabel, newSetInput, newWeightLabel, newWeightInput, addWorkoutButton, returnButton2, saveButton);
        workoutButton.setOnAction(e -> primaryStage.setScene(newWorkoutScene));

        addWorkoutButton.setOnAction(e -> {
            Workout newWorkout = new Workout(newTitleInput.getText(), Integer.parseInt(newRepInput.getText()), Integer.parseInt(newSetInput.getText()),
                    Integer.parseInt(newWeightInput.getText()));


            System.out.println("New Workout added: " + newWorkout.getTitle());
            Text actionStatus = new Text();


            // clears list
            newTitleInput.clear();
            newRepInput.clear();
            newSetInput.clear();
            newWeightInput.clear();
            workoutList.add(newWorkout);
            System.out.println(workoutList.size());

        });

        saveButton.setOnAction(e -> {
            try {
                this.writeObjectToFile();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        //********************************End of New workout button**************************************************

        viewWorkoutsLayout.setStyle("-fx-background-color: #ffffff ");
        // add a label for all attributes
        viewWorkoutsScene = new Scene(viewWorkoutsLayout, 500, 500);


        viewWorkouts.setOnAction(e -> {
            this.generateLoadWorkoutView();
        });

        //next button action to queue
        nextButton.setOnAction(e ->{
            this.queue.poll();
            viewWorkoutsLayout.getChildren().clear();
            if(this.queue.size() == 0){
                this.generateGenericAlert("Workout Complete","You have completed your workout","thank you");
                primaryStage.setScene(mainPageScene);
                }else{
                this.setWorkoutLabels();
                viewWorkoutsLayout.getChildren().addAll(new Label(labelCounter.getText()), new Label(label.getText()), new Label(label2.getText()), new Label(label3.getText()),
                        new Label(label4.getText()), new Label(label5.getText()), returnButton3, compareButton, sortButton, nextButton);
            }
        });


        //exit scene
        returnButton3.setOnAction(e -> {
            primaryStage.setScene(mainPageScene);
            viewWorkoutsLayout.getChildren().clear();


        });

        compareButton.setOnAction(e -> {
            primaryStage.setScene(compareScene);

        });

        //Sort button that utilizes the QuickSort class
        sortButton.setOnAction(e -> {
            sortedWorkoutList = qs.quickSort(workoutList, 0, workoutList.size() - 1);
            this.generateGenericAlert("Quick Sort", "Sorted Worked out based on title","");
            viewWorkoutsLayout.getChildren().clear();
            this.clearLabels();
            this.generateLoadWorkoutView();
        });
        //button action that loads the workoutlist into a hashtable
        findHighestMaxButton.setOnAction(e ->{
            System.out.println("BST implementation");
            for(Workout w: workoutList) {
                bst.insert(w.OneRepMax());
                workoutHashTable.put(w.OneRepMax(), w);
            }

            String max = "Max rep of all workouts in the file: "+bst.maxValue(bst.root);
            this.generateGenericAlert("High Rep Max", max,"");

        });



        //************************End of view workouts**********************************
        //help button
        Button returnButton4 = new Button("Return");
        returnButton4.setOnAction(e -> primaryStage.setScene(mainPageScene));
        VBox helpLayout = new VBox();
        helpLayout.setStyle("-fx-background-color: #ffffff ");
        helpScene = new Scene(helpLayout, 500, 500);
        Text helpText = new Text(help.about()); // call method
        helpText.setWrappingWidth(500);
        helpLayout.getChildren().add(helpText);
        helpLayout.getChildren().addAll(returnButton4);

        helpText.setTextAlignment(TextAlignment.JUSTIFY);
        helpButton.setOnAction(e -> primaryStage.setScene(helpScene));

        //*****************************End Of help **************************************************************

        Button returnButton5 = new Button("Return");
        Button compareWorkoutButton = new Button("equals");

        returnButton5.setOnAction(e -> primaryStage.setScene(viewWorkoutsScene));

        VBox compareLayout = new VBox();
        returnButton5.setAlignment(Pos.BOTTOM_LEFT);
        compareWorkoutButton.setAlignment(Pos.BOTTOM_RIGHT);

        compareLayout.setStyle("-fx-background-color: #ffffff");

        compareScene = new Scene(compareLayout, 500, 500);

        Label compareLabel = new Label("What past workouts would you like to compare?: ");
        TextField compareText = new TextField();


        compareLayout.getChildren().addAll(compareLabel, compareText, returnButton5, compareWorkoutButton);

        compareWorkoutButton.setOnAction(e -> {
            // compare text field input with workoutlist title spot
            String foundWorkouts = "";
            for (int i = 0; i < workoutList.size(); i++) {

                try {
                    if (compareText.getText().equals(workoutList.get(i).getTitle())) {
                        System.out.println("found " + workoutList.get(i).toString());
                        //add workoutlist that is equal to compare text to found workouts
                        foundWorkouts += workoutList.get(i).toString() + " \n";

                    }

                } catch (Exception exe) {
                    System.out.println("could not find");

                }
            }

            String title = "Compared Workouts " + compareText.getText();
            String header = "Here are the results of " + compareText.getText();
            String content = foundWorkouts;

            this.generateGenericAlert(title, header, content);


        });

        primaryStage.setScene(mainPageScene);
        primaryStage.setTitle("Lifting Tracker");
        primaryStage.show();
    }

    public static void main(String[] args) {

        count(5);
        Application.launch(args);

    }


    // Method for save workouts
    public void writeObjectToFile() throws IOException {
        FileChooser file = new FileChooser();
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("WRK files (*.wrk)", "*.wrk"));
        File selectedFile = file.showSaveDialog(null);

        try {

            FileOutputStream fos = new FileOutputStream(selectedFile.getPath());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(workoutList);
            oos.flush();
            oos.close();
            System.out.println("success");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    // Deserialization
    // Get object from a file.
    public static void readObjectFromFile() throws IOException, ClassNotFoundException {
        FileChooser file = new FileChooser();
        file.getExtensionFilters().add(new FileChooser.ExtensionFilter("WRK files (*.wrk)", "*.wrk"));
        File selectedFile = file.showOpenDialog(null);

        try {
            FileInputStream fis = new FileInputStream(selectedFile.getPath());
            ObjectInputStream obj = new ObjectInputStream(fis);
            workoutList = (WorkoutsList) obj.readObject();
            System.out.println("Uploaded");


        } catch (Exception exception) {
            System.out.println(exception);
        }


    }

    // recursive method
    public static void count(int n) {
        //base case
        if (n == 0) {
            System.out.println("Let's get it on!");
        } else {
            System.out.println(n);
            n--;
            count(n); // recursion
        }
    }

    //method to set the workout labels for queue
    public static void setWorkoutLabels(){
        //reusable set methods
        labelCounter.setText("\n\tWorkouts Left: " + queue.size());
        label.setText("\n\tWorkout: " + queue.peek().getTitle());
        label2.setText("\tReps: " + String.valueOf(queue.peek().getReps()));
        label3.setText("\tSets: " + String.valueOf(queue.peek().getSets()));
        label4.setText("\tWeight: " + String.valueOf(queue.peek().getWeight()) + " lbs");
        label5.setText("\tOne Rep Max: " + String.valueOf(queue.peek().OneRepMax()) + " lbs");
    }

    // method to clear the queue data
    public static void clearLabels(){
        labelCounter.setText("");
        label.setText("");
        label2.setText("");
        label3.setText("");
        label4.setText("");
        label5.setText("");
    }

    //generates workout playlist
    public static void generateLoadWorkoutView(){
        //check if the workoutlist is zero. prompt user to upload a workout file. Otherwise load workout into the queue
        if (workoutList.size() == 0) {
            try {
                readObjectFromFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        } else {
            queue = workoutList;
            setWorkoutLabels();
            viewWorkoutsLayout.getChildren().addAll(new Label(labelCounter.getText()), new Label(label.getText()), new Label(label2.getText()), new Label(label3.getText()),
                    new Label(label4.getText()), new Label(label5.getText()));

            viewWorkoutsLayout.getChildren().addAll(returnButton3, compareButton, sortButton, nextButton, findHighestMaxButton);
            primaryStage.setScene(viewWorkoutsScene);
        }


    }

    //method to create an alert
    public static void generateGenericAlert(String title, String header, String content){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
