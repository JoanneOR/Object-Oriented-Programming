package com.example.demo1;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.layout.GridPane;
import java.sql.Connection;

public class HelloApplication extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_records";
    private static final String USER = "root";
    private static final String PASS = "Orange@123";

    private Connection connection;


    public void start(Stage stage) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to MySQL database
        connection = DriverManager.getConnection(DB_URL, USER, PASS);

        ArrayList<HelloController> students = new ArrayList<>();
        ArrayList<Module> modulee = new ArrayList<>();

        // Create TabPane and tabs
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Add Student");
        Tab tab4 = new Tab("Add Modules");
        Tab tab2 = new Tab("Add Grades");
        Tab tab3 = new Tab("Memory Leak");


        // Tab 1
        VBox root = new VBox();
        HBox nameBox = new HBox();
        Label nameLabels = new Label("Enter Name ");
        TextField nameField = new TextField();
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.getChildren().addAll(nameLabels, nameField);
        nameBox.setSpacing(10);

        HBox studentIdBox = new HBox();
        Label studentIdLabel = new Label("Enter Student ID ");
        TextField studentIdField = new TextField();
        studentIdBox.setAlignment(Pos.CENTER_LEFT);
        studentIdBox.getChildren().addAll(studentIdLabel, studentIdField);
        studentIdBox.setSpacing(10);

        HBox datebirthBox = new HBox();
        Label datebirthLabel = new Label("Enter Date of Birth ");
        TextField datebirthField = new TextField();
        datebirthBox.setAlignment(Pos.CENTER_LEFT);
        datebirthBox.getChildren().addAll(datebirthLabel, datebirthField);
        datebirthBox.setSpacing(10);

        HBox semesterBox = new HBox();
        Label semesterLabel = new Label("Enter the semester you are in (1 or 2): ");
        TextField semesterField = new TextField();
        semesterBox.setAlignment(Pos.CENTER_LEFT);
        semesterBox.getChildren().addAll(semesterLabel, semesterField);
        semesterBox.setSpacing(10);

        HBox controlsBox = new HBox();
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button listButton = new Button("List");
        controlsBox.setAlignment(Pos.CENTER_LEFT);
        controlsBox.getChildren().addAll(addButton, removeButton, listButton);
        controlsBox.setSpacing(10);


        TextArea blankArea = new TextArea("List of Students details within this application");

        HBox moreControls = new HBox();
        Button exitButton = new Button("Exit");
        moreControls.setAlignment(Pos.CENTER_RIGHT);
        moreControls.getChildren().addAll(exitButton);
        moreControls.setSpacing(10);

        root.getChildren().addAll(nameBox, studentIdBox, datebirthBox, semesterBox, controlsBox, blankArea, moreControls);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(15);

        //Tab 4
        //Adding Module

        VBox root4 = new VBox();
        HBox modBox = new HBox();
        Label modLabels = new Label("Enter Module Name ");
        TextField modField = new TextField();
        modBox.setAlignment(Pos.CENTER_LEFT);
        modBox.getChildren().addAll(modLabels, modField);
        modBox.setSpacing(10);


        HBox codeBox = new HBox();
        Label codeLabel = new Label("Enter Module Code:");
        TextField codeField = new TextField();
        codeBox.setAlignment(Pos.CENTER_LEFT);
        codeBox.getChildren().addAll(codeLabel, codeField);
        codeBox.setSpacing(10);

        HBox semBox = new HBox();
        Label semLabel = new Label("Enter semester the module is in (1 or 2): ");
        TextField semField = new TextField();
        semBox.setAlignment(Pos.CENTER_LEFT);
        semBox.getChildren().addAll(semLabel, semField);
        semBox.setSpacing(10);


        HBox controlBox = new HBox();
        Button addingmodules = new Button("Add Module Information");
        Button removesButton = new Button("Remove");
        controlBox.setAlignment(Pos.CENTER_LEFT);
        controlBox.getChildren().addAll(addingmodules, removesButton);
        controlBox.setSpacing(10);


        root4.getChildren().addAll(modBox, codeBox, semBox, controlBox);
        root4.setPadding(new Insets(20, 20, 20, 20));
        root4.setSpacing(15);


        //  Set the event handler for the "Remove" button
        removesButton.setOnAction(e -> {
            String modname = modField.getText();
            String modcode = codeField.getText();
            String modsem = semField.getText();

            if (modname.isBlank() || modcode.isBlank() || modsem.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Incorrect!");
                alert.setContentText("Please provide information to all three fields");
                alert.showAndWait();
                return;
            }

            // Search for the module to remove
            Module removemodule = null;
            for (Module module : modulee) {
                if (module.getName().equals(modname) && module.getCode().equals(modcode) && module.getSem().equals(modsem)) {
                    removemodule = module;
                    break;
                }
            }

            if (removemodule != null) {
                // Remove the module from the list
                modulee.remove(removemodule);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Module Information successfully removed!");
                alert.showAndWait();

                try {
                    // Remove the module from the database
                    String sql = "DELETE FROM modules WHERE Module_code = ? AND Module_name = ? AND semester = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, removemodule.getCode());
                    statement.setString(2, removemodule.getName());
                    statement.setString(3, removemodule.getSem());
                    statement.executeUpdate();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail!");
                alert.setContentText("Module Information not found in this System");
                alert.showAndWait();
            }
        });


        //Tab2
        GridPane root2 = new GridPane();
        root2.setAlignment(Pos.TOP_LEFT);
        root2.setHgap(10);
        root2.setVgap(10);
        root2.setPadding(new Insets(20, 20, 20, 20));

        Label studentLabel = new Label("Select a student:");
        ComboBox<String> studentCombobox = new ComboBox<>();
        for (HelloController student : students) {
            studentCombobox.getItems().add(student.getName());
        }
        studentCombobox.getSelectionModel().selectFirst();

        Label moduleLabel = new Label("Select a module:");
        ComboBox<String> moduleCombobox = new ComboBox<>();
        for (Module modulees : modulee) {
            moduleCombobox.getItems().add(modulees.getName());
        }
        moduleCombobox.getSelectionModel().selectFirst();

        Label gradeLabels = new Label("Enter the grade for the module:");
        TextField gradeField = new TextField();

        Button addGradeButton = new Button("Add Grade");

        addGradeButton.setOnAction(e -> {
            String studentSelectedName = studentCombobox.getSelectionModel().getSelectedItem();
            HelloController selectedStudent = null;
            for (HelloController student : students) {
                if (student.getName().equals(studentSelectedName)) {
                    selectedStudent = student;
                    break;
                }
            }
            String moduleSelectedName = moduleCombobox.getSelectionModel().getSelectedItem();
            Module selectedModule = null;
            for (Module modulees : modulee) {
                if (modulees.getName().equals(moduleSelectedName)) {
                    selectedModule = modulees;
                    break;
                }
            }
            if (selectedStudent != null && selectedModule != null) {
                String grade = gradeField.getText();
                selectedStudent.addGrade(selectedModule, grade);
                // Add the grade to the SQL database
                try {

                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO grades (studentid, modulecode,modulename, grade) VALUES (?, ?, ?,?)");
                    stmt.setString(1, selectedStudent.getStudentID());
                    stmt.setString(2, selectedModule.getCode());
                    stmt.setString(3, selectedModule.getName());
                    stmt.setString(4, grade);
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Grade Information Added!");
                alert.showAndWait();

            }
        });

        Label modifyGradeLabel = new Label("Modify the grade for a module:");
        ComboBox<String> modifyStudentCombobox = new ComboBox<>();
        for (HelloController student : students) {
            modifyStudentCombobox.getItems().add(student.getName());
        }
        modifyStudentCombobox.getSelectionModel().selectFirst();

        ComboBox<String> modifyModuleCombobox = new ComboBox<>();
        for (Module modulees : modulee) {
            modifyModuleCombobox.getItems().add(modulees.getName());
        }
        modifyModuleCombobox.getSelectionModel().selectFirst();

        Label newGradeLabel = new Label("Enter the new grade for the module:");
        TextField newGradeField = new TextField();

        Button modifyGradeButton = new Button("Modify Grade");

        modifyGradeButton.setOnAction(e -> {
            String studentSelectedName = modifyStudentCombobox.getSelectionModel().getSelectedItem();
            HelloController selectedStudent = null;
            for (HelloController student : students) {
                if (student.getName().equals(studentSelectedName)) {
                    selectedStudent = student;
                    break;
                }
            }
            String moduleSelectedName = modifyModuleCombobox.getSelectionModel().getSelectedItem();
            Module selectedModule = null;
            for (Module modulees : modulee) {
                if (modulees.getName().equals(moduleSelectedName)) {
                    selectedModule = modulees;
                    break;
                }
            }
            if (selectedStudent != null && selectedModule != null) {
                String newGrade = newGradeField.getText();
                String oldGrade = selectedModule.getGrade();
                selectedStudent.modifyGrade(selectedModule, newGrade);

// Update the grade in the SQL database
                try {

                    PreparedStatement stmt = connection.prepareStatement("UPDATE grades SET grade = ? WHERE studentid = ? AND modulecode = ? AND modulename = ?");
                    stmt.setString(1, newGrade);
                    stmt.setString(2, selectedStudent.getStudentID());
                    stmt.setString(3, selectedModule.getCode());
                    stmt.setString(4, selectedModule.getName());
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Grade Information Updated! New Grade: " + newGrade);
                alert.showAndWait();
                newGradeField.clear();
            }
        });

//Add components to the grid
        root2.add(studentLabel, 0, 0);
        root2.add(studentCombobox, 1, 0);
        root2.add(moduleLabel, 0, 1);
        root2.add(moduleCombobox, 1, 1);
        root2.add(gradeLabels, 0, 2);
        root2.add(gradeField, 1, 2);
        root2.add(addGradeButton, 1, 3);
        root2.add(modifyGradeLabel, 0, 4);
        root2.add(modifyStudentCombobox, 1, 4);
        root2.add(modifyModuleCombobox, 1, 5);
        root2.add(newGradeLabel, 0, 6);
        root2.add(newGradeField, 1, 6);
        root2.add(modifyGradeButton, 1, 7);



        //Tab3 Memory Leak
        GridPane root3 = new GridPane();
        root3.setAlignment(Pos.TOP_LEFT);
        root3.setHgap(10);
        root3.setVgap(10);
        root3.setPadding(new Insets(20, 20, 20, 20));


        Button memoryLeakButton = new Button("Simulate Memory Leak");
        memoryLeakButton.setOnAction(e -> {
            LocalDate localDate = LocalDate.now();
            int count = 0;
            ArrayList<HelloController> studentss = new ArrayList<>();
            try {
                while (true) {
                    HelloController student = new HelloController("Name " + count, "ID " + count, localDate.toString(), "Semester " + count);
                    studentss.add(student);
                    count++;
                }
            } catch (OutOfMemoryError ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Memory Leak!");
                alert.setContentText("Memory ran out after creating " + count + " objects.");
                alert.showAndWait();
            }
        });

        root3.add(memoryLeakButton, 1, 3);


        // Add tabs to the tabPane
            tab1.setContent(root);
            tab4.setContent(root4);
           tab2.setContent(root2);
            tab3.setContent(root3);
            tabPane.getTabs().addAll(tab1, tab4, tab2,tab3);

            // Create the Scene
            Scene scene = new Scene(tabPane, 600, 400);

        addButton.setOnAction(ef -> {
            String name = nameField.getText();
            String studentID = studentIdField.getText();
            String DOB = datebirthField.getText();
            String semester = semesterField.getText();

            if (name.isBlank() || studentID.isBlank() || DOB.isBlank() || semester.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Incorrect!");
                alert.setContentText("Please provide information to all three fields name, Student ID " +
                        "and Date of Birth.");
                alert.showAndWait();
                return;
            }
            HelloController newStudent = new HelloController(name, studentID, DOB, semester);
            students.add(newStudent);
            studentCombobox.getItems().add(newStudent.getName());
            modifyStudentCombobox.getItems().add(newStudent.getName());


            try {
                String sql = "INSERT INTO students (student_id, Student_name, date_of_birth, semester) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, newStudent.getStudentID());
                stmt.setString(2, newStudent.getName());
                stmt.setString(3, newStudent.getDateBirth());
                stmt.setString(4, newStudent.getSemester());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }


// Display success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success!");
            successAlert.setContentText("Student information added to database.");
            successAlert.showAndWait();

        });

        removeButton.setOnAction(e -> {
            String name = nameField.getText();
            String studentID = studentIdField.getText();
            String DOB = datebirthField.getText();
            String semester = semesterField.getText();

            if (name.isBlank() || studentID.isBlank() || DOB.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Incorrect!");
                alert.setContentText("Please provide information to all three fields name, Student ID " +
                        "and Date of Birth.");
                alert.showAndWait();
                return;
            }

            HelloController removedStudent = new HelloController(name, studentID, DOB, semester);

            if (students.remove(removedStudent)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setContentText("Student Information successfully removed!");
                alert.showAndWait();

                try {
                    String sql = "DELETE FROM students WHERE student_id = ? AND Student_name = ? AND date_of_birth = ? AND semester = ?";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.setString(1, removedStudent.getStudentID());
                    stmt.setString(2, removedStudent.getName());
                    stmt.setString(3, removedStudent.getDateBirth());
                    stmt.setString(4, removedStudent.getSemester());
                    stmt.executeUpdate();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail!");
                alert.setContentText("Student Information not found in this System");
                alert.showAndWait();
            }
        });

        addingmodules.setOnAction(ej -> {
            String modname = modField.getText();
            String modcode = codeField.getText();
            String modsem = semField.getText();

            // Validate input
            if (modname.isBlank() || modcode.isBlank() || modsem.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Incorrect!");
                alert.setContentText("Please provide information to all three fields.");
                alert.showAndWait();
                return;
            }

            // Create a new module object and add it to the modulee collection
            Module newmodule = new Module(modname, modcode, modsem);
            modulee.add(newmodule);
            moduleCombobox.getItems().add(newmodule.getName());
            modifyModuleCombobox.getItems().add(newmodule.getName());

            // Insert the new module into the database
            try {
                String sql = "INSERT INTO modules (Module_code, Module_name, semester) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, newmodule.getCode());
                stmt.setString(2, newmodule.getName());
                stmt.setString(3, newmodule.getSem());
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Display success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success!");
            successAlert.setContentText("Module information added to database.");
            successAlert.showAndWait();
        });


        listButton.setOnAction(e -> {
                StringBuilder studentList = new StringBuilder();
                for (HelloController student : students) {
                    studentList.append(student).append("\n");
                }
                blankArea.setText(studentList.toString());
            });

            exitButton.setOnAction(e ->
                    stage.close());


            stage.setScene(scene);
            stage.setTitle("Student Management System");
            stage.show();

    }
        public static void main (String[]args) {
            launch();

        }
    }


