package org.example.algorithms2assiggnment;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import org.example.algorithms2assiggnment.Core.*;

import java.io.*;
import java.util.*;

public class MainApp extends Application {
    private final List<Student> students = new ArrayList<>();
    private final TableView<Student> table = new TableView<>();
    private final TextArea logArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CSV Sorting App");

        // Buttons and Controls
        Button selectFileBtn = new Button("Select CSV File");
        Button sortBtn = new Button("Sort Files");
        Button saveBtn = new Button("Save as CSV");

        ComboBox<String> sortCriteria = new ComboBox<>(FXCollections.observableArrayList("Name", "ID", "Average"));
        ComboBox<String> sortAlgorithm = new ComboBox<>(FXCollections.observableArrayList("Merge Sort", "Quick Sort"));
        sortAlgorithm.setValue("Quick Sort");
        sortCriteria.setValue("ID");
        // Event Handlers
        selectFileBtn.setOnAction(e -> openCSV(primaryStage));
        sortBtn.setOnAction(e -> sortData(sortCriteria.getValue(),sortAlgorithm.getValue()));
        saveBtn.setOnAction(e -> saveCSV(primaryStage));
        // TableView Configuration
        configureTable();

        // Layout
        HBox controls = new HBox(10, selectFileBtn, sortCriteria, sortAlgorithm, sortBtn, saveBtn);
        VBox root = new VBox(10, controls,table,logArea);

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    private void configureTable() {
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, Double> avgColumn = new TableColumn<>("Average");
        avgColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        table.getColumns().addAll(idColumn, nameColumn, avgColumn);
    }

    private void openCSV(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            students.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isHeader = true; // Skip header

                while ((line = reader.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        try {
                            int id = Integer.parseInt(parts[0].trim());
                            String name = parts[1].trim();
                            double average = Double.parseDouble(parts[2].trim());
                            students.add(new Student(name, id, average));
                        } catch (NumberFormatException e) {
                            logArea.appendText("Invalid data in line: " + line + "\n");
                        }
                    }
                    else {
                        logArea.appendText("Skipping invalid line: " + line + "\n");
                    }
                }

                logArea.appendText("File loaded successfully!\n");
                updateTable(); // Ensure the table is updated immediately
            } catch (IOException ex) {
                logArea.appendText("Error reading file: " + ex.getMessage() + "\n");
            }
        } else {
            logArea.appendText("File selection was canceled.\n");
        }
    }

    private void sortData(String criteria, String algorithm) {
        List<Student> sortedStudents = new ArrayList<>(students);
        Comparator<Student> comparator = switch (criteria) {
            case "Name" -> new CompareByName();
            case "ID" -> new CompareById();
            default -> new CompareByAvg();
        };
        if ("Merge Sort".equals(algorithm)) {
            SortUtil.mergeSort(sortedStudents, comparator);
        } else {
            SortUtil.quickSort(sortedStudents, comparator);
        }
        students.clear();
        students.addAll(sortedStudents);

        logArea.appendText("Data sorted successfully using " + algorithm + " by " + criteria + "!\n");
        updateTable();
    }


    private void saveCSV(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("sorted_data.csv");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("ID,Name,Average\n");
                for (Student s : students) {
                    writer.write(s.getId() + "," + s.getName() + "," + s.getAverage() + "\n");
                }
                logArea.appendText("Data saved successfully!\n");
            } catch (IOException ex) {
                logArea.appendText("Error saving file: " + ex.getMessage() + "\n");
            }
        }
    }

    private void updateTable() {
        table.getItems().clear();
        table.getItems().addAll(students);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
