package hu.unideb.inf.warehouse.frontend;

import hu.unideb.inf.warehouse.WarehouseApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext ctx;

    @Override
    public void start(Stage stage) throws Exception {

        // Spring indítása
        ctx = SpringApplication.run(WarehouseApplication.class);

        // FXMLLoader Spring controller factory-val
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
        loader.setControllerFactory(ctx::getBean);

        Parent root = loader.load(); // ✅ Parent
        stage.setScene(new Scene(root)); // ✅ Scene-be csomagoljuk
        stage.setTitle("Warehouse");
        stage.show();
    }

    @Override
    public void stop() {
        ctx.close();
    }
}
