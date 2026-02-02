package main;

import calculator.logic.BinaryCalculator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main extends Application {

    private Label display;
    private Label hexLabel, decLabel, octLabel, binLabel;

    private String firstOperand = "";
    private String operator = "";
    private boolean startNew = true;

    private final BinaryCalculator calculator = new BinaryCalculator();

    @Override
    public void start(Stage stage) {



        Label title = new Label("≡ Programmer");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        display = new Label("0");
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setMinHeight(80);
        display.setMaxWidth(Double.MAX_VALUE);
        display.setStyle(
                "-fx-font-size: 36px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10;"
        );

        hexLabel = createRadixLabel("HEX");
        decLabel = createRadixLabel("DEC");
        octLabel = createRadixLabel("OCT");
        binLabel = createRadixLabel("BIN");
        binLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

        VBox displayBox = new VBox(8, title, display, hexLabel, decLabel, octLabel, binLabel);
        displayBox.setPadding(new Insets(15));



        GridPane grid = new GridPane();
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setPadding(new Insets(10));

        String[][] layout = {
                {"A", "<<", ">>", "C", "⌫"},
                {"B", "(", ")", "%", "÷"},
                {"C", "7", "8", "9", "×"},
                {"D", "4", "5", "6", "-"},
                {"E", "1", "2", "3", "+"},
                {"F", "+/-", "0", ".", "="}
        };

        for (int r = 0; r < layout.length; r++) {
            for (int c = 0; c < layout[r].length; c++) {
                String text = layout[r][c];
                Button btn = createButton(text);

                if (text.equals("0") || text.equals("1")) {
                    btn.setOnAction(e -> appendDigit(text));
                } else if ("+-×÷".contains(text)) {
                    btn.setOnAction(e -> setOperator(text));
                } else if (text.equals("=")) {
                    btn.setOnAction(e -> calculate());
                } else if (text.equals("C")) {
                    btn.setOnAction(e -> clear());
                } else {
                    btn.setDisable(true);
                }

                grid.add(btn, c, r);
            }
        }

        VBox root = new VBox(displayBox, grid);
        root.setStyle("-fx-background-color: #F3F3F3;");

        Scene scene = new Scene(root, 360, 550);
        stage.setTitle("Binary Calculator");
        stage.setScene(scene);
        stage.show();
    }



    private void appendDigit(String d) {
        if (startNew) {
            display.setText(d);
            startNew = false;
        } else {
            display.setText(display.getText() + d);
        }
        updateRadix(display.getText());
    }

    private void setOperator(String op) {
        firstOperand = display.getText();
        operator = op;
        startNew = true;
    }

    private void calculate() {
        try {
            String second = display.getText();
            String result = "0";

            switch (operator) {
                case "+": result = calculator.addBinary(firstOperand, second); break;
                case "-": result = calculator.subtractBinary(firstOperand, second); break;
                case "×": result = calculator.multiplyBinary(firstOperand, second); break;
                case "÷": result = calculator.divideBinary(firstOperand, second); break;
            }

            display.setText(result);
            updateRadix(result);
            startNew = true;

        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void clear() {
        display.setText("0");
        updateRadix("0");
        firstOperand = "";
        operator = "";
        startNew = true;
    }

    private void updateRadix(String bin) {
        try {
            int value = Integer.parseInt(bin, 2);
            hexLabel.setText("HEX  " + Integer.toHexString(value).toUpperCase());
            decLabel.setText("DEC  " + value);
            octLabel.setText("OCT  " + Integer.toOctalString(value));
            binLabel.setText("BIN  " + bin);
        } catch (Exception ignored) {}
    }



    private Label createRadixLabel(String name) {
        Label l = new Label(name + "  0");
        l.setStyle("-fx-font-family: Consolas; -fx-text-fill: gray;");
        return l;
    }

    private Button createButton(String text) {
        Button btn = new Button(text);
        btn.setPrefSize(60, 55);
        btn.setFocusTraversable(false);

        if (text.equals("=")) {
            btn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-font-size: 16px;");
        } else if (text.equals("C")) {
            btn.setStyle("-fx-background-color: #E81123; -fx-text-fill: white;");
        } else if ("01+-×÷".contains(text)) {
            btn.setStyle("-fx-background-color: white; -fx-font-size: 14px;");
        } else {
            btn.setStyle("-fx-background-color: #EDEDED; -fx-text-fill: gray;");
        }

        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}