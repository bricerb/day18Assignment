package sample;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Brice on 8/31/16.
 */
public class ToSaveContainer implements Serializable {

    private ArrayList<ToDoItem> containerList;

    public ArrayList<ToDoItem> getContainerList() {
        return containerList;
    }

    public void setContainerList(ArrayList<ToDoItem> containerList) {
        this.containerList = containerList;
    }
}
