module com.example.advancedfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example to javafx.fxml;
    exports com.example;
    exports advancedfinalproject.game.prototype;
    exports advancedfinalproject.game.PlantsVsZombies;
    opens advancedfinalproject.game.prototype to javafx.fxml;
    opens advancedfinalproject.game.PlantsVsZombies to javafx.fxml;
}