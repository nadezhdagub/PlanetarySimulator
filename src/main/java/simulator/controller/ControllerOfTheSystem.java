package simulator.controller;

import simulator.model.FileManager;
import simulator.model.SystemCharacteristic;
import simulator.view.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ControllerOfTheSystem {

    Logger log = LogManager.getLogger(ControllerOfTheSystem.class.getName());

    @FXML
    private Button help;
    @FXML
    private Button apply;
    @FXML
    private TextField mass;
    @FXML
    private TextField radius;
    @FXML
    public TextField G;
    @FXML
    private Slider number;
    @FXML
    public Button load;

    boolean[] completion = new boolean[3];

    private boolean check = false;

    private void btnEnabler(boolean test, int num) {
        completion[num] = test;
        for (var b : completion)
            if (!b) {
                check = false;
                break;
            } else check = true;
        apply.setDisable(!check);
    }

    @FXML
    public void initialize() {
        help.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Help");
            alert.setContentText("For example, mass = 900, radius = 80, Gravitational constant = 7");
            alert.showAndWait();
        });

        load.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            var file = new FileManager();
            file.open((Stage) apply.getScene().getWindow());
        });

        var regex = "[0-9]+([.,][0-9]+)?";
        mass.setOnKeyTyped(event -> btnEnabler(mass.getText().matches(regex), 0));
        radius.setOnKeyTyped(event -> btnEnabler(radius.getText().matches(regex), 1));
        G.setOnKeyTyped(event -> btnEnabler(G.getText().matches(regex), 2));

        apply.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            ((Stage) apply.getScene().getWindow()).close();
            var system = new SystemCharacteristic();
            try {
                system.setMassOfStar(mass.getText());
                system.setRadiusOfStar(radius.getText());
                system.setGC(G.getText());
            } catch (Exception e) {
                log.error("Exception " + e);
                e.printStackTrace();
            }
            system.setNumberOfPlanets(number.getValue());

            log.info(system.toString());
                var planet = new App();
                try {
                    planet.planetSetup(system);
                } catch (Exception e) {
                    log.error("Exception " + e);
                    e.printStackTrace();
                }
        });
    }
}