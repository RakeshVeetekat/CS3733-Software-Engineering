import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class editNodeController {
    private Node oldNode;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField nodeIDTextfield;

    @FXML
    private TextField xcoordTextfield;

    @FXML
    private TextField ycoordTextfield;

    @FXML
    private TextField floorTextfield;

    @FXML
    private TextField buildingTextfield;

    @FXML
    private TextField nodeTypeTextfield;

    @FXML
    private TextField longNameTextfield;

    @FXML
    private TextField shortNameTextfield;

    public void setNodeID(String nodeID) {
        System.out.println(nodeID);
        //Get Node Data
        //setFields(oldNode);
    }

    @FXML
    public void setFields(Node node)  {
        nodeIDTextfield.setText(node.getNodeID());
        xcoordTextfield.setText("" + node.getXcoord());
        ycoordTextfield.setText("" + node.getYcoord());
        floorTextfield.setText("" + node.getFloor());
        buildingTextfield.setText(node.getBuidling());
        nodeTypeTextfield.setText(node.getNodeType());
        longNameTextfield.setText(node.getLongName());
        shortNameTextfield.setText(node.getShortName());
    }

    @FXML
    private void validateEdits() {
        if(!oldNode.getNodeID().equals(nodeIDTextfield.getText()) ||
           oldNode.getXcoord() != Integer.parseInt(xcoordTextfield.getText()) ||
           oldNode.getYcoord() != Integer.parseInt(ycoordTextfield.getText()) ||
           oldNode.getFloor() != Integer.parseInt(floorTextfield.getText()) ||
           !oldNode.getBuidling().equals(buildingTextfield.getText()) ||
           !oldNode.getNodeType().equals(nodeTypeTextfield.getText()) ||
           !oldNode.getLongName().equals(longNameTextfield.getText()) ||
           !oldNode.getShortName().equals(shortNameTextfield.getText())) {
            saveButton.setDisable(false);
        } else {
            saveButton.setDisable(true);
        }
    }


    @FXML
    private void setCancelButton(ActionEvent e) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("readIn.fxml"));
        stage.setTitle("Database Viewer");
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    @FXML
    private void setSaveButton() {

    }

}
