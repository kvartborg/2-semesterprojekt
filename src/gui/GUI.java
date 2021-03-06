package gui;

import acq.IGUI;
import acq.IDomain;
import gui.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.util.HashMap;
import java.util.Map;

public class GUI extends Application implements IGUI {

  /**
   * Instance of the class itself, used for singleton setup. Is set to null
   * initially, instantiated if needed though getInstance()
   */
  private static GUI gui;

  /**
   * Reference to domain instance. This variable need to be static in order to
   * be set in initialize
   */
  private static IDomain domain;

  /**
   * A map with all active controllers
   */
  private Map<String, Controller> controllers = new HashMap<>();

  /**
   * Singleton setup for GUI, returns instance of the class if none has been
   * made. If one has been made, returns that created instance
   *
   * @return GUI
   */
  public static GUI getInstance() {
    if (gui == null) {
      gui = new GUI();
    }

    return gui;
  }

  /**
   * Inject domain instance
   *
   * @param domain
   */
  @Override
  public void inject(IDomain domain) {
    GUI.domain = domain;
  }

  /**
   * Initialize the javafx thread
   *
   * @param args
   * @param d
   */
  public static void initialize(String[] args, IDomain d) {
    domain = d;
    launch(args);
  }

  /**
   * start
   *
   * @param stage
   */
  @Override
  public void start(Stage stage) {
    //This to setup the GUI correctly
    gui = this;
    //this.stage = stage;
    controllers.put("Login", load("Login"));
    controllers.get("Login").getStage().show();
    controllers.put("CreateCase", load("create_case/CreateCase"));
    controllers.put("Overview", load("Overview"));

  }

  /**
   * Load view and controller
   *
   * @param name the name of the controller to load
   *
   * @return Controller the loaded controller
   */
  public Controller load(String name) {
    Controller controller = null;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/" + name + ".fxml"));
    try {
      Parent view = (Parent) loader.load();

      controller = loader.getController();

      Stage stage = new Stage();

      controller.setStage(stage);

      this.createScene(stage, view);

    } catch (Exception err) {
      err.printStackTrace();
    }

    return controller;
  }

  /**
   * Create scen
   *
   * @param stage
   * @param view
   */
  private void createScene(Stage stage, Parent view) {
    Scene scene = new Scene(view);
    stage.setScene(scene);
  }

  /**
   * Shortcut to show stage
   *
   * @param name
   */
  public void showStage(String name) {
    controllers.get(name).getStage().show();
  }

  /**
   * Get domain
   *
   * @return IDomain
   */
  public IDomain getDomain() {
    return domain;
  }

  /**
   * Get a map with all active controller
   *
   * @return Map<String, Controller>
   */
  public Map<String, Controller> getControllers() {
    return controllers;
  }

  /**
   * Get controller
   *
   * @param name
   * @return name
   */
  public Controller getController(String name) {
    return controllers.get(name);
  }

}
