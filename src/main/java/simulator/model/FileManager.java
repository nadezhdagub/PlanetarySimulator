package simulator.model;

import javafx.stage.Stage;
import simulator.view.App;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    static Logger log = LogManager.getLogger(FileManager.class.getName());

    public void save(SystemCharacteristic system) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select directory for save");
        fileChooser.setInitialFileName("configuration");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Configuration file .pss", "*.pss"));
        var file = fileChooser.showSaveDialog(App.stageFile);
        if (file == null) {
            log.info("The user closed the saving window");
            return;
        }
        try (var writer = new FileWriter(file)) {
            writer.write(system.toStringFile());
            log.info("Save to " + file.getAbsolutePath());
        } catch (IOException e) {
            error(false);
            log.error(e);
        }
    }

    public void open(Stage stage) {
        try {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Open config file");
        var filter = new FileChooser.ExtensionFilter("Configuration file .pss", "*.pss");
        fileChooser.getExtensionFilters().add(filter);
        var file = fileChooser.showOpenDialog(App.stageFile);
        if (file == null) {
            log.info("The user closed the opening window");
            return;
        }
        file = file.getAbsoluteFile();
        var system = new SystemCharacteristic();
        var size = 0;
            var fr = new FileReader(file);
            var reader = new BufferedReader(fr);
            var line = reader.readLine();
            while (line != null) {
                if (line.matches("([0-9]+([.,][0-9]+)? ){4}")) {
                    var list = line.split(" ");
                    system.massOfStar = Double.parseDouble(list[0]);
                    system.radiusOfStar = Double.parseDouble(list[1]);
                    system.GC = Double.parseDouble(list[2]);
                    system.numberOfPlanets = Integer.parseInt(list[3]);
                }
                else if (line.matches("'\\S+' 0x([a-f0-9]){8} (-?[0-9]+([.,][0-9]+)? ){5}")) {
                    var list = line.split(" ");
                    var planet = new PlanetCharacteristic();
                    planet.setName(list[0].substring(1, list[0].length() - 1));
                    planet.color = list[1];
                    if (list[2].contains("-")) {
                        log.error("The file " + file.getAbsolutePath() + "has '-'");
                        error(true);
                        return;
                    }
                    planet.setRadius(list[2]);
                    planet.setPositionX(list[3]);
                    planet.setPositionY(list[4]);
                    planet.speedX = Double.parseDouble(list[5]);
                    planet.speedY = Double.parseDouble(list[6]);
                    system.planet.add(planet);
                    size++;
                }
                else {
                    log.error("The file " + file.getAbsolutePath() + " is invalid");
                    error(true);
                    return;
                }
                line = reader.readLine();
            }
            if (size != system.numberOfPlanets) {
                log.error("In this file " + file.getAbsolutePath() + " the number of available planets does not match the specified number.");
                error(true);
                return;
            }
            stage.close();
            var app = new App();
            app.space(system);
        } catch (IOException e) {
            error(true);
            log.error(e);
        }
    }

    public static void error(boolean type) {
        String headerText;
        String contentText;

        if (type) {
            headerText = "File invalid";
            contentText = "This file cannot be used by the program.";
        }
        else {
            headerText = "Saving failed";
            contentText = "Try again. Try specifying a different directory.";
        }

        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
