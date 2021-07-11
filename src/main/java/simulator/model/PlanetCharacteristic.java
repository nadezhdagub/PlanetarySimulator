package simulator.model;

import javafx.scene.paint.Color;

public class PlanetCharacteristic {
    public String name;
    public String color;
    public double radius;
    public double positionX;
    public double positionY;
    public double speedX;
    public double speedY;


    public void setName (String nameU){
        name = nameU;
    }

    public void setColor (Color colorU){
        color = colorU.toString();
    }

    public void setPositionX (String positionXU) {
        positionX = Double.parseDouble(formatter(positionXU));
    }

    public void setPositionY (String positionYU) {
        positionY = Double.parseDouble(formatter(positionYU));
    }

    public void setRadius (String radiusU) {
        radius = Double.parseDouble(formatter(radiusU));
    }

    public void setSpeed (String speedU, String deg) {
        var logic = new LogicManager();
        speedX = logic.speedX(Double.parseDouble(formatter(speedU)), Double.parseDouble(formatter(deg)));
        speedY = logic.speedY(Double.parseDouble(formatter(speedU)), Double.parseDouble(formatter(deg)));
    }

    public String formatter(String in) {
        return in.replace(',', '.').trim();
    }

    public String toShortString() {
        return  "Planet " + name +
                "\nColor " + color +
                "\nRadius " + radius;
    }

    public String toString() {
        return "Model.PlanetCharacteristic{" +
                "name=" + name +
                ", color=" + color +
                ", radius=" + radius +
                ", speedX=" + speedX +
                ", speedY=" + speedY +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }

}
