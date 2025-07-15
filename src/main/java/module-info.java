module com.example.advancedfinalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example to javafx.fxml;
    exports com.example;
    exports advancedfinalproject.game.prototype;
    exports advancedfinalproject.game.PlantsVsZombies;
}