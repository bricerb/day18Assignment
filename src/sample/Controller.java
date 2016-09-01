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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Controller implements Initializable {

    Main runner = new Main();

    private String userName;

    private String DATA_FILE_NAME = (userName + "-todoList.txt");

    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserName();
//        todoItems.equals(readFromFile(getUserName()));

        todoList.setItems(todoItems);
    }

    public void setUserName() {
        Scanner inputScanner = new Scanner(System.in);
        Main runner = new Main();
        System.out.println("What's your name?");
        userName = inputScanner.next();
    }

    public void addItem() {
        todoItems.add(new ToDoItem(todoText.getText()));
        todoText.setText("");
        writeToFile();
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        todoItems.remove(todoItem);
        writeToFile();
    }

    public void toggleItem() {
        ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
            writeToFile();
        }
    }

    public void toggleAll() {
        for (ToDoItem currToDoItem : todoItems) {
            currToDoItem.isDone = !currToDoItem.isDone;
        }
        writeToFile();
        todoList.setItems(null);
        todoList.setItems(todoItems);
    }

    public void allDone() {
        for (ToDoItem currToDoItem : todoItems) {
            if (todoItems != null) {
                currToDoItem.isDone = true;
            }
        }
        writeToFile();
        todoList.setItems(null);
        todoList.setItems(todoItems);
    }

    public void allNotDone() {
        for (ToDoItem currToDoItem : todoItems) {
            currToDoItem.isDone = !true;
        }
        writeToFile();
        todoList.setItems(null);
        todoList.setItems(todoItems);
    }

    public String jsonSave() {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(prepareContainer());

        return jsonString;
    }

    public void saveList(ArrayList<ToDoItem> todoToSave) throws IOException {
        FileOutputStream fos = new FileOutputStream(DATA_FILE_NAME);
        ObjectOutput objectOut = new ObjectOutputStream(fos);
        objectOut.writeObject(todoToSave);
        objectOut.flush();
    }

    public ToDoItem jsonRestore(String jsonTD) {

        JsonParser toDoItemParser = new JsonParser();
        ToDoItem item = toDoItemParser.parse(jsonTD, ToDoItem.class);

        return item;
    }

    public ArrayList<ToDoItem> prepareContainer() {
        ArrayList<ToDoItem> array = new ArrayList<>();
        if (todoItems != null) {
            array.addAll(todoItems);
        }
        return array;
    }

    public void writeToFile() {
        try {
            saveList(prepareContainer());
            jsonSave();
        } catch (IOException ioEx) {

        }
    }

}