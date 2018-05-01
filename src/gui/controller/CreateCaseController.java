package gui.controller;

import acq.ICase;
import gui.GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

public class CreateCaseController extends Controller {

  /**
   * Tabpane holding the different stages in case-creation
   */
  @FXML
  private TabPane tabPane;
  /**
   * Refreence Case
   */
  private ICase c;

  /**
   * Go to next tab in the process
   *
   * @param event
   */
  @FXML
  private void nextButtonClicked(ActionEvent event) {
    tabPane.getSelectionModel().selectNext();
  }

  /**
   * Go to previous tab in the process
   *
   * @param event
   */
  @FXML
  private void previousButtonClicked(ActionEvent event) {
    tabPane.getSelectionModel().selectPrevious();
  }

  /**
   * Method for open window
   */
  public void open() {
    super.open();
    if (c == null) {
      c = GUI.getInstance().getDomain().createCase();
    }
  }

}
