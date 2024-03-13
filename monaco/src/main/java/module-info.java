module monaco.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires jdk.jlink;

    opens monaco.application to javafx.fxml;
    exports monaco.application;
}