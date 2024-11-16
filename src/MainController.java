import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;
import data.PointData;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private XML xml = new XML();
    private ObservableList<PointData.ArrayFX.Point> observableListFX;
    private ObservableList<PointData.ArrayGX.Point> observableListGX;

    @FXML
    private TableView<PointData.ArrayFX.Point> tableFX;

    @FXML
    private TableColumn<PointData.ArrayFX.Point, Double> FXColumnX;

    @FXML
    private TableColumn<PointData.ArrayFX.Point, Double> FXColumnY;

    @FXML
    private TableView<PointData.ArrayGX.Point> tableGX;

    @FXML
    private TableColumn<PointData.ArrayGX.Point, Double> GXColumnX;

    @FXML
    private TableColumn<PointData.ArrayGX.Point, Double> GXColumnY;

    @FXML
    private TextField eps;

    @FXML
    private TextField a;

    @FXML
    private TextField b;

    @FXML
    private LineChart<Number, Number> Chart;

    @FXML
    private TextArea ROOT;

    public static List<String> ll = new ArrayList<>();

    public MainController() throws JAXBException, FileNotFoundException {
    }

    private boolean checker() {
        List<PointData.ArrayFX.Point> listFX = new ArrayList<>();
        ObservableList<PointData.ArrayFX.Point> tempFX = FXCollections.observableList(listFX);
        for (PointData.ArrayFX.Point p : observableListFX) {
            for (int i = 0; i < tempFX.size(); i++) {
                if (tempFX.get(i).getX() == p.getX() && tempFX.get(i).getY() == p.getY()) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error!");
                    a.setHeaderText("Error! The point already exists in the table!");
                    a.showAndWait();
                    observableListFX.remove(i);
                    xml.delByIndexFX(i);
                    return false;
                }
            }
            tempFX.add(p);
        }
        List<PointData.ArrayGX.Point> listGX = new ArrayList<>();
        ObservableList<PointData.ArrayGX.Point> tempGX = FXCollections.observableList(listGX);
        for (PointData.ArrayGX.Point p : observableListGX) {
            for (int i = 0; i < tempGX.size(); i++) {
                if (tempGX.get(i).getX() == p.getX() && tempGX.get(i).getY() == p.getY()) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error!");
                    a.setHeaderText("Error! The point already exists in the table!");
                    a.showAndWait();
                    observableListGX.remove(i);
                    xml.delByIndexGX(i);
                    return false;
                }
            }
            tempGX.add(p);
        }
        return true;
    }

    @FXML
    void doAddFX(ActionEvent actionEvent) {
        xml.addPFX(0, 0);
        XMLupdateTableFX();
    }

    @FXML
    void doAddGX(ActionEvent actionEvent) {
        xml.addPGX(0, 0);
        XMLupdateTableGX();
    }

    @FXML
    void doBuild() {
        try {
            int count = 0;
            ll.clear();
            ROOT.clear();
            Chart.getData().clear();
            Chart.setCreateSymbols(false);
            Chart.setLegendVisible(false);
            XYChart.Series<Number, Number> seriesF = new XYChart.Series<>();
            XYChart.Series<Number, Number> seriesG = new XYChart.Series<>();
            double A = Double.parseDouble(a.getText());
            double B = Double.parseDouble(b.getText());
            double E = Double.parseDouble(eps.getText());
            xml.setA(A);
            xml.setB(B);
            xml.setEps(E);
            try {
                if (xml.getSizeFX() < 2 || xml.getSizeGX() < 2)
                    throw new SecurityException();
                if (checker() == false)
                    doBuild();
                double h = (B - A) / 100;
                if (A >= B)
                    throw new Error();
                double temp;
                for (double x = A; x < B; x += h) {
                    for (int i = 0; i < xml.getSizeFX(); i++) {
                        temp = Polinom.LPfx(x, xml.getArrFX());
                        if (Double.isNaN(temp))
                            throw new ArithmeticException();
                        seriesF.getData().add(new XYChart.Data<>(x, temp));
                    }
                    for (int i = 0; i < xml.getSizeGX(); i++) {
                        temp = Polinom.LPgx(x, xml.getArrGX());
                        if (Double.isNaN(temp))
                            throw new ArithmeticException();
                        seriesG.getData().add(new XYChart.Data<>(x, temp));
                    }
                }
            } catch (Error e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error!");
                a.setHeaderText("Error!Incorrect interval");
                seriesF.getData().clear();
                seriesG.getData().clear();
                Chart.getData().clear();
                ROOT.clear();
                a.showAndWait();
            } catch (SecurityException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error!");
                a.setHeaderText("Error! Not enough points (need >= 2)");
                seriesF.getData().clear();
                seriesG.getData().clear();
                Chart.getData().clear();
                ROOT.clear();
                a.showAndWait();
            }
            ROOT.clear();
            Chart.getData().addAll(seriesF, seriesG);
            for (double x = A; x < B; x += 0.1) {
                String temp = Dotichiy.dotichiy(x, x + 0.1, Double.parseDouble(eps.getText()), xml.getArrFX(), xml.getArrGX());
                if (!temp.isEmpty())
                    count++;
            }
            if (count == Math.abs((B - A) * 10) || count - 1 == Math.abs((B - A) * 10)) {
                ROOT.setText("Infinite number of roots!");
                ll.add("Infinite number of roots!");
            } else {
                for (double x = A; x < B; x += 0.1) {
                    String temp = Dotichiy.dotichiy(x, x + 0.1, Double.parseDouble(eps.getText()), xml.getArrFX(), xml.getArrGX());
                    if (!temp.isEmpty()) {
                        ROOT.appendText(temp + "\n");
                        ll.add(temp);
                        count++;
                    }
                }
                if (ROOT.getText().isEmpty()) {
                    ROOT.setText("There aren't roots in this interval!");
                    ll.add("There aren't roots in this interval!");
                }
            }
        } catch (ArithmeticException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setHeaderText("Error! The graph with such points doesn't exist! Check points!");
            Chart.getData().clear();
            ROOT.clear();
            a.showAndWait();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setHeaderText("Error!Incorrect data");
            Chart.getData().clear();
            ROOT.clear();
            a.showAndWait();
        }
    }

    @FXML
    void doRemoveFX(ActionEvent event) {
        int t = tableFX.getSelectionModel().getFocusedIndex();
        if (observableListFX == null)
            return;
        if (observableListFX.size() > 0) {
            observableListFX.remove(t);
            xml.delByIndexFX(t);
        }
        if (observableListFX.size() <= 0)
            observableListFX = null;
    }

    @FXML
    void doRemoveGX(ActionEvent event) {
        int t = tableGX.getSelectionModel().getFocusedIndex();
        if (observableListGX == null)
            return;
        if (observableListGX.size() > 0) {
            observableListGX.remove(t);
            xml.delByIndexGX(t);
        }
        if (observableListGX.size() <= 0)
            observableListGX = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Записуємо порожній рядок замість "No content in table":
        tableFX.setPlaceholder(new Label(""));
        tableGX.setPlaceholder(new Label(""));
    }

    private void FXupdateX(TableColumn.CellEditEvent<PointData.ArrayFX.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setX(t.getNewValue());
    }

    private void FXupdateY(TableColumn.CellEditEvent<PointData.ArrayFX.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setY(t.getNewValue());
    }

    private void GXupdateX(TableColumn.CellEditEvent<PointData.ArrayGX.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setX(t.getNewValue());
    }

    private void GXupdateY(TableColumn.CellEditEvent<PointData.ArrayGX.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setY(t.getNewValue());
    }

    private void XMLupdateTableFX() {
        List<PointData.ArrayFX.Point> list = new ArrayList<>();
        observableListFX = FXCollections.observableList(list);
        for (int i = 0; i < xml.getSizeFX(); i++) {
            list.add(xml.getPointFX_index(i));
        }
        tableFX.setItems(observableListFX);

        FXColumnX.setCellValueFactory(new PropertyValueFactory<>("X"));
        FXColumnX.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        FXColumnX.setOnEditCommit(t -> FXupdateX(t));

        FXColumnY.setCellValueFactory(new PropertyValueFactory<>("Y"));
        FXColumnY.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        FXColumnY.setOnEditCommit(t -> FXupdateY(t));
    }

    private void XMLupdateTableGX() {
        List<PointData.ArrayGX.Point> list = new ArrayList<>();
        observableListGX = FXCollections.observableList(list);
        for (int i = 0; i < xml.getSizeGX(); i++) {
            list.add(xml.getPointGX_index(i));
        }
        tableGX.setItems(observableListGX);

        GXColumnX.setCellValueFactory(new PropertyValueFactory<>("X"));
        GXColumnX.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        GXColumnX.setOnEditCommit(t -> GXupdateX(t));

        GXColumnY.setCellValueFactory(new PropertyValueFactory<>("Y"));
        GXColumnY.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        GXColumnY.setOnEditCommit(t -> GXupdateY(t));
    }

    @FXML
    void doAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About program...");
        alert.setHeaderText("Lagrange polynomial interpolation and search roots by tangential\n");
        alert.setContentText("Course work\nAuthor: Kira Hovorukha");
        alert.showAndWait();
    }

    @FXML
    void doClear() {
        Chart.getData().clear();
        a.clear();
        b.clear();
        eps.clear();
        xml.Clear();
        ROOT.clear();
        if (observableListFX == null)
            return;
        else
            observableListFX.clear();
        if (observableListGX == null)
            return;
        else
            observableListGX.clear();
    }

    @FXML
    void doClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void doNew() throws JAXBException, FileNotFoundException {
        xml = new XML();
        doClear();
        observableListFX = null;
        observableListGX = null;
    }

    public static FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        // Починаємо шукати з поточної теки:
        fileChooser.setInitialDirectory(new File("."));
        // Встановлюємо фільтри для пошуку файлів:
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML-files (*.xml)", "*.xml"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
        // Вказуємо заголовк вікна:
        fileChooser.setTitle(title);
        return fileChooser;
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static FileChooser FileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        // Починаємо шукати з поточної теки:
        fileChooser.setInitialDirectory(new File("."));
        // Встановлюємо фільтри для пошуку файлів:
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html-files (*.html)", "*.html"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
        // Вказуємо заголовк вікна:
        fileChooser.setTitle(title);
        return fileChooser;
    }

    @FXML
    void doOpen(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = getFileChooser("Open XML-file");
        File file;
        if ((file = fileChooser.showOpenDialog(null)) != null) {
            try {
                doNew();
                xml.readFromFile(file.getCanonicalPath());
                // Заповнюємо текстові поля прочитаними даними:
                a.setText(xml.getA() + "");
                b.setText(xml.getB() + "");
                eps.setText(xml.getEps() + "");
                ROOT.setText("");
                // Очищаємо та оновлюємо таблицю:
                tableFX.setItems(null);
                tableGX.setItems(null);
                XMLupdateTableFX();
                XMLupdateTableGX();
                doBuild();
            } catch (IOException e) {
                showError("File not found");
            } catch (XML.FileReadException e) {
                showError("The file format is incorrect");
            }
        }
    }

    @FXML
    void doReport(ActionEvent event) throws IOException {
        try {
            if (observableListGX.size() < 2 || observableListFX.size() < 2 || a.getText().isEmpty() || b.getText().isEmpty() || eps.getText().isEmpty())
                throw new Exception();
            WritableImage im = Chart.snapshot(new SnapshotParameters(), null);
            File fileIm = new File("D:\\Курсова\\CourseWork_Java\\CourseWork\\out\\production\\CourseWork\\chart.png");
            ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", fileIm);
            FileChooser fileChooser = FileChooser("Save html-file");
            File file;
            if ((file = fileChooser.showSaveDialog(null)) != null) {
                //updateSourceData(); // оновлюємо дані в моделі
                xml.saveReport(file.getCanonicalPath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Report is generated!");
                alert.showAndWait();
                Desktop.getDesktop().open(file.getCanonicalFile());
            }
        } catch (Exception e) {
            showError("Incorrect data for report generation");
        }
    }

    private void updateSourceData() {
        // Переписуємо дані в модель з observableList
        xml.setA(Double.parseDouble(a.getText()));
        xml.setB(Double.parseDouble(b.getText()));
        xml.setEps(Double.parseDouble(eps.getText()));
        xml.Clear();
        for (PointData.ArrayFX.Point c : observableListFX)
            xml.addPFX(c.getX(), c.getY());
        for (PointData.ArrayGX.Point c : observableListGX)
            xml.addPGX(c.getX(), c.getY());
    }

    @FXML
    void doSave(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Save XML-file");
        File file;
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                updateSourceData(); // оновлюємо дані в моделі
                xml.writeToFile(file.getCanonicalPath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Results saved successfully");
                alert.showAndWait();
            } catch (Exception e) {
                showError("Error writing to file");
            }
        }
    }
}
