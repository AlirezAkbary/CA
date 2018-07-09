import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.util.concurrent.BrokenBarrierException;


/**
 * Created by ali on 7/7/2018 AD.
 */
public class Main extends Application
{
    public static void main(String[] args)
    {
//        Recorder recorder = new Recorder();
//        Processor processor = new Processor(recorder);
//        processor.start();
//        for (Record record : recorder.records)
//        {
//            record.print();
//        }
//        Assembler assembler = new Assembler();
//        String s = ".constant\n" +
//                "\tali\t1\n" +
//                "\tmamad\t2\n" +
//                "\thassan\t1324\n" +
//                ".end-constant\n" +
//                ".var\n" +
//                "\tand\n" +
//                "\tvfsf\n" +
//                "\tvgdbd\n" +
//                ".end-var\n" +
//                ".main\n" +
//                "First:\n" +
//                "\tbipush\t12\n" +
//                "\tGoTo\tFirst\n" +
//                "Second:\n" +
//                "\tiinc \tand\t21\n" +
//                "\tisub\n" +
//                "Third:\n" +
//                "\tiadd\n" +
//                "Forth:\n" +
//                "\tiload\t\tvfsf\n" +
//                "Fifth:\n" +
//                "\tnop\n" +
//                "\tif_icmpeq\t\tforth\n" +
//                "\tifeq\t\tsecond\n" +
//                ".end-main";
//        try
//        {
//            assembler.start(s);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        launch(args);
    }

    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("IJVM Emulator");

        TableView<RegRecord> regTable = new TableView<>();
        TableColumn <RegRecord, String> name = new TableColumn<>("Name");
        TableColumn <RegRecord, String> value = new TableColumn<>("Value");
        TableColumn <RegRecord, String> clear = new TableColumn<>("Clear");
        TableColumn <RegRecord, String> load = new TableColumn<>("Load");
        regTable.getColumns().addAll(name, value, clear, load);




        TableView flagALUTable = new TableView();
        TableColumn flagName =  new TableColumn("Name");
        TableColumn flagValue = new TableColumn("Value");
        flagALUTable.getColumns().addAll(flagName, flagValue);


        VBox tableGroup = new VBox();
        tableGroup.getChildren().addAll(regTable, flagALUTable);

        ScrollPane scrollPane = new ScrollPane(tableGroup);
        scrollPane.setPrefSize(300, 100);

        BorderPane bp = new BorderPane();
        bp.setRight(scrollPane);

        Scene scene = new Scene(bp, 500, 500);

        primaryStage.setScene(scene);

        primaryStage.show();


    }
}
