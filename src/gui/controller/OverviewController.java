package gui.controller;

import gui.GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 */
public class OverviewController extends Controller {

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void openCaseAction(ActionEvent event) {

    GUI.getInstance().showStage("CreateCase");
    GUI.getInstance().getDomain().createCase();
    getStage().close();
  }
}
