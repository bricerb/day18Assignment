package sample;

import com.sun.javafx.css.converters.StringConverter;
import com.sun.tools.javac.comp.Todo;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Controller implements Initializable {

    private String userName;

    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserName();
        todoItems.equals(readFromFile(getUserName()));

        todoList.setItems(todoItems);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("What's your name?");
        this.userName = inputScanner.next();
    }

    public void addItem() {
        todoItems.add(new ToDoItem(todoText.getText()));;
        todoText.setText("");
        writeUserFile();
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        todoItems.remove(todoItem);
        writeUserFile();
    }

    public void toggleItem() {
        ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
        }
        writeUserFile();
    }

    public void toggleAll() {
        for (ToDoItem currToDoItem : todoItems) {
            currToDoItem.isDone = !currToDoItem.isDone;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
        writeUserFile();
    }

    public void allDone() {
        for (ToDoItem currToDoItem : todoItems) {
            if (todoItems != null) {
                currToDoItem.isDone = true;
            }
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
        writeUserFile();
    }

    public void allNotDone() {
        for (ToDoItem currToDoItem : todoItems) {
            currToDoItem.isDone = !true;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
        writeUserFile();
    }

    public void writeUserFile() {
        FileWriter writeToFile = null;
        try {
            File userFile = new File(getUserName() + "-todoList.txt");
            writeToFile = new FileWriter(userFile);

            ToSaveContainer containerClass = new ToSaveContainer();
            for (ToDoItem currentToDoItem : todoItems) {
                containerClass.addToContainerList(currentToDoItem);
            }
            writeToFile.write(jsonStringGenerator(containerClass));
            writeToFile.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (writeToFile != null) {
                try {
                    writeToFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public ListView readFromFile (String userName) {
        try {
            File userFile = new File(userName + "-todoList.txt");
            Scanner fileScanner = new Scanner(userFile);

            String scanString = null;
            scanString = fileScanner.nextLine();

            ToSaveContainer container = jsonRestore(scanString);
            for (ToDoItem currentItem : container.getContainerList()) {
                todoItems.add(currentItem);
            }
            todoList.setItems(null);
            todoList.setItems(todoItems);
        } catch (Exception exception) {
        }
        return todoList;
    }

    public String jsonStringGenerator(ToSaveContainer todoToSave) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(todoToSave);

        return jsonString;
    }

    public ToSaveContainer jsonRestore(String jsonTD) {
        JsonParser toDoItemParser = new JsonParser();
        ToSaveContainer item = toDoItemParser.parse(jsonTD, ToSaveContainer.class);

        return item;
    }

}