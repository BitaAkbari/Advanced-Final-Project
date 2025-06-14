module com.example.advancedfinalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.advancedfinalproject to javafx.fxml;
    exports com.example.advancedfinalproject;
    exports game.prototype.AdvancedFinalProject;
    exports game.PlantsVsZombies.AdvancedFinalProject;
}