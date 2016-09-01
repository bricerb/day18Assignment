package sample;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Brice on 8/31/16.
 */
public class ToSaveContainer {

    public ToSaveContainer() {
    }

    private ArrayList<ToDoItem> containerList = new ArrayList<>();

    public ArrayList<ToDoItem> getContainerList() {
        return containerList;
    }

    public void addToContainerList(ToDoItem addingToList) {
        containerList.add(addingToList);
    }
}
