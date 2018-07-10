import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;


/**
 * Created by ali on 7/7/2018 AD.
 */
public class Main extends Application
{
    Recorder recorder;
    Processor processor;

    Label intro = new Label("Name  value     load    clear");
    Label MAR = new Label("MAR: 0x00000000  0  0");
    Label MDR = new Label("MDR: 0x00000000  0  0");
    Label PC = new Label("PC: 0x00000000  0  0");
    Label MBR = new Label("MBR: 0x00000000  0  0");
    Label SP = new Label("SP: 0x00000000  0  0");
    Label LV = new Label("LV: 0x00000000  0  0");
    Label CPP = new Label("CPP: 0x00000000  0  0");
    Label TOS = new Label("TOS: 0x00000000  0  0");
    Label OPC = new Label("OPC: 0x00000000  0  0");
    Label H = new Label("H: 0x00000000  0  0");
    Label IR = new Label("IR: 0x00000000  0  0");

    Label start = new Label("Start: 0");
    Label rwn = new Label("rwn: 1");
    Label ready = new Label("Ready = 1");
    Label ALU = new Label("ALU signal: 000000");
    Label nFlag = new Label("Neg Flag: 0");
    Label zFlag = new Label("Z Flag:");
    Label nff = new Label("Neg Flip Flop: 0");
    Label zff = new Label("Zero Flip Flop: 0");
    TextArea codeSubmit = new TextArea("Write your code here!");
    Label throughput = new Label("Throughput: 0 inst/clk");
    Assembler assembler = new Assembler();
    Label util = new Label("Utilization: 0");
    int[] instNum = new int[1];
    Label hitRate = new Label("Hits: 0");
    Label missRate = new Label("Misses: 0");


    {
        recorder = new Recorder();
        processor = new Processor(recorder);
        recorder.setUp(processor);
    }
    public static void main(String[] args)
    {

//        String string = ".constant\n" +
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
//            byte[] mem = (new Assembler()).start(string);
//        } catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            System.out.println("fuck");
//        }

        launch(args);
    }

    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("IJVM Emulator");

//        TableView<RegRecord> regTable = new TableView<>();
//        TableColumn <RegRecord, String> name = new TableColumn<>("Name");
//        TableColumn <RegRecord, String> value = new TableColumn<>("Value");
//        TableColumn <RegRecord, String> clear = new TableColumn<>("Clear");
//        TableColumn <RegRecord, String> load = new TableColumn<>("Load");
//        regTable.getColumns().addAll(name, value, clear, load);
//
//
//
//
//        TableView flagALUTable = new TableView();
//        TableColumn flagName =  new TableColumn("Name");
//        TableColumn flagValue = new TableColumn("Value");
//        flagALUTable.getColumns().addAll(flagName, flagValue);
//
//
//        VBox tableGroup = new VBox();
//        tableGroup.getChildren().addAll(regTable, flagALUTable);
//
//        ScrollPane scrollPane = new ScrollPane(tableGroup);
//        scrollPane.setPrefSize(300, 100);

        VBox scrollGroup = new VBox();


        scrollGroup.getChildren().addAll(intro, MAR, MDR, PC, MBR, SP, LV, CPP,
                TOS, OPC, H, IR, start, rwn, ready, ALU, nFlag, zFlag, zff, nff);

        scrollGroup.setSpacing(20);
        scrollGroup.setAlignment(Pos.BASELINE_CENTER);

        ScrollPane scrollPane = new ScrollPane(scrollGroup);

        scrollGroup.setPrefSize(250, 300);

        BorderPane bp = new BorderPane();
        bp.setRight(scrollPane);

        Button newFile = new Button("LoadFile");

        Button next = new Button("Next");
        Button last = new Button("Last");
        Button prev = new Button("Prev");
        Button reset = new Button("reset");
        ToggleButton cache = new ToggleButton("Cache Off");

        cache.setOnAction(event -> {
            if (processor.memory.hasCache)
            {
                processor.memory.hasCache = false;
                cache.setText("Cache Off");
            } else {
                processor.memory.hasCache = true;
                cache.setText("Cache On");
            }
        });


        HBox hBox = new HBox();
        hBox.getChildren().addAll(newFile, next, prev, last, reset, cache);
        hBox.setSpacing(30);
        hBox.setPrefSize(500, 60);
        hBox.setAlignment(Pos.CENTER);

        bp.setTop(hBox);



        Button submit = new Button("Submit");

        VBox codeVbox = new VBox();
        codeVbox.getChildren().addAll(codeSubmit, submit);

        codeSubmit.setPrefSize(250, 300);

        codeVbox.setSpacing(10);

        bp.setLeft(codeVbox);



        Label alert = new Label();
        Group bottomBox = new Group();
        bottomBox.getChildren().addAll(alert, throughput, util, missRate, hitRate);
        alert.setPrefSize(400, 100);
        alert.setLayoutX(0);
        alert.setLayoutY(0);

        throughput.setLayoutX(350);
        throughput.setLayoutY(10);

        util.setLayoutX(350);
        util.setLayoutY(50);

        hitRate.setLayoutX(350);
        hitRate.setLayoutY(90);

        missRate.setLayoutX(350);
        missRate.setLayoutY(130);

        bp.setBottom(bottomBox);



        newFile.setOnAction(event ->
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(primaryStage);
            try
            {
                Scanner scanner = new Scanner(file);
                scanner.useDelimiter("\\z");
                codeSubmit.setText(scanner.next());
            } catch (Exception e) {
                alert.setText("File can not be read.");
            }


        });

        int[] num = new int[1];

        submit.setOnAction(event ->
        {

            try
            {
                runCode(codeSubmit.getText());
            } catch (Exception e)
            {
                alert.setText("Invalid Code Input\n");

                e.printStackTrace();
                return;
            }

            setLabels(recorder.records.get(0));
            num[0] = 0;
            System.out.println(instNum[0]);
            throughput.setText(String.format("Throughput: %.3f inst/clk", (double)instNum[0] / recorder.records.size()));
            System.out.println(instNum[0]);
            util.setText(String.format("Utilization: %.3f" , (double)(recorder.records.size() - processor.memory.haltclk) / recorder.records.size()));
            System.out.println(recorder.records.size());
            hitRate.setText(String.format("Hits: %d", processor.memory.hitNum));
            missRate.setText(String.format("Misses: %d", processor.memory.missNum));
        });

        last.setOnAction(event -> {
            setLabels(recorder.records.get(recorder.records.size() - 1));
        });

        prev.setOnAction(event -> {
            if (num[0] > 0)
            {
                setLabels(recorder.records.get(num[0] - 1));
                num[0] -= 1;
            }
        });

        next.setOnAction(event -> {
            if (num[0] >= recorder.records.size() - 1)
                return;
            setLabels(recorder.records.get(num[0]));
            num[0] += 1;
        });

        reset.setOnAction(event -> this.reSet());


        Scene scene = new Scene(bp, 600, 600);

        primaryStage.setScene(scene);

        primaryStage.show();


    }

    private void runCode(String input) throws Exception
    {
        processor.memory.array = assembler.start(input);
        processor.start();
        this.instNum[0] = assembler.instNum;

    }

    private void setLabels(Record record)
    {
        String mdrString = "MDR: " + String.format("0x%08X", record.MDR.value) +
                "  " + record.MDR.load + "  " + record.MDR.clear;

        String marString = "MAR: " + String.format("0x%08X", record.MAR.value) +
                "  " + record.MAR.load + "  " + record.MAR.clear;

        String pcString = "PC: " + String.format("0x%08X", record.PC.value) +
                "  " + record.PC.load + "  " + record.PC.clear;

        String mbrString = "MBR: " + String.format("0x%08X", record.MBR.value) +
                "  " + record.MBR.load + "  " + record.MBR.clear;

        String spString = "SP: " + String.format("0x%08X", record.SP.value) +
                "  " + record.SP.load + "  " + record.SP.clear;

        String lvString = "LV: " + String.format("0x%08X", record.LV.value) +
                "  " + record.LV.load + "  " + record.LV.clear;

        String cppString = "CPP: " + String.format("0x%08X", record.CPP.value) +
                "  " + record.CPP.load + "  " + record.CPP.clear;

        String tosString = "TOS: " + String.format("0x%08X", record.TOS.value) +
                "  " + record.TOS.load + "  " + record.TOS.clear;

        String opcString = "OPC: " + String.format("0x%08X", record.OPC.value) +
                "  " + record.OPC.load + "  " + record.OPC.clear;

        String hString = "H: " + String.format("0x%08X", record.H.value) +
                "  " + record.H.load + "  " + record.H.clear;

        String irString = "IR: " + String.format("0x%08X", record.IR.value) +
                "  " + record.IR.load + "  " + record.IR.clear;

        String startString = "Start: " + record.memory.start;

        String nffString = "Neg Flip Flop: " + record.Negative.set;

        String zffString = "Zero Flip Flop:" + record.Zero.set;

        String rwnString = "rwn: " + record.memory.rwn;
        String readyString = "Ready: " + record.memory.ready;
        String aluString = "ALU Signal: " + record.ALU.Function;
        String nFlagString = "Neg Flag: " + record.ALU.nFlag;
        String zFlagString = "Zero Flag: " + record.ALU.zFlag;

        MAR.setText(marString);
        MDR.setText(mdrString);
        PC.setText(pcString);
        MBR.setText(mbrString);
        SP.setText(spString);
        LV.setText(lvString);
        CPP.setText(cppString);
        TOS.setText(tosString);
        OPC.setText(opcString);
        H.setText(hString);
        IR.setText(irString);

        start.setText(startString);
        rwn.setText(rwnString);
        ready.setText(readyString);
        ALU.setText(aluString);
        nFlag.setText(nFlagString);
        zFlag.setText(zFlagString);

        nff.setText(nffString);
        zff.setText(zffString);
    }

    private void reSet()
    {
        recorder = new Recorder();
        processor = new Processor(recorder);
        recorder.setUp(processor);
        assembler = new Assembler();

        MAR.setText("MAR: 0x00000000  0  0");
        MDR.setText("MDR: 0x00000000  0  0");
        PC.setText("PC: 0x00000000  0  0");
        MBR.setText("MBR: 0x00000000  0  0");
        SP.setText("SP: 0x00000000  0  0");
        LV.setText("LV: 0x00000000  0  0");
        CPP.setText("CPP: 0x00000000  0  0");
        TOS.setText("TOS: 0x00000000  0  0");
        OPC.setText("OPC: 0x00000000  0  0");
        H.setText("H: 0x00000000  0  0");
        IR.setText("IR: 0x00000000  0  0");

        start.setText("Start: 0");
        rwn.setText("rwn: 1");
        ready.setText("Ready = 1");
        ALU.setText("ALU Signal: 000000");
        nFlag.setText("Neg Flag: 0");
        zFlag.setText("Zero  Flag:");
        nff.setText("Neg Flip Flop: 0");
        zff.setText("Zero Flip Flop: 0");

        codeSubmit.setText("Write your code here!");

        throughput.setText("Throughput: 0 inst/clk");
        util.setText("Utilization: 0");
        instNum[0] = 0;

        missRate.setText("Misses: 0");
        hitRate.setText("Hits: 0");

    }

}
