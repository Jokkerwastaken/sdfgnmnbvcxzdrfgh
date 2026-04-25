package objects;

import classes.Vector2;

public class EntitySpeed {
    public EntitySpeed() {

    }
    public void checkSpeed() {
        // Kui vektorite hüpotenuus on võrdne või suurem kui maxSpeed,
        // võtame vektorite x ja y suurused, arvutame välja nende magnituudi
        // ja korrutame need kiiruse piiriga.

            // Nt. x = 54 ja y = 34
        Vector2 Velocity = new Vector2(54, 34);
            // Nende Hüpotenuus: u 64
            // Nende magnituud on hüpotenuus
        double Magnitude = Velocity.magnitude();
            // Ning maxSpeed on 50
        double maxSpeed = 50;
            // Kui Magnituud on suurem kui maxSpeed siis on vaja andmeid kärpida
        if (Magnitude > maxSpeed) {
            // Kärpimisel võtame iga atribuudi,jagame selle magnituudiga
            // ning siis korrutame saaduse maxSpeed väärtusega
            // sel juhul on uus x suurus: (54/64)*maxSpeed (50) = (54/64)*50 = 42.1875
            Velocity.x = (Velocity.x / Magnitude) * maxSpeed;
            // ning y uus väärtus: (34/64)*50 = 26.5625 
            Velocity.y = (Velocity.y / Magnitude) * maxSpeed;
            // ning nende hüpotenuus: u 50
        }
    }
}
