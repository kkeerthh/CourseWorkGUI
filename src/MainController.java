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
import pointdata.EquationData;

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
    private ObservableList<EquationData.FunctionFx.Point> observableListFx;
    private ObservableList<EquationData.FunctionGx.Point> observableListGx;

    @FXML
    private TableView<EquationData.FunctionFx.Point> tableFx;

    @FXML
    private TableColumn<EquationData.FunctionFx.Point, Double> FxColumnX;

    @FXML
    private TableColumn<EquationData.FunctionFx.Point, Double> FxColumnY;

    @FXML
    private TableView<EquationData.FunctionGx.Point> tableGx;

    @FXML
    private TableColumn<EquationData.FunctionGx.Point, Double> GxColumnX;

    @FXML
    private TableColumn<EquationData.FunctionGx.Point, Double> GxColumnY;

    @FXML
    private TextField tolerance;

    @FXML
    private TextField lowerBound;

    @FXML
    private TextField upperBound;

    @FXML
    private LineChart<Number, Number> Chart;

    @FXML
    private TextArea ROOT;

    public static List<String> ll = new ArrayList<>();

    public MainController() throws JAXBException, FileNotFoundException {
    }

    private boolean checker() {
        List<EquationData.FunctionFx.Point> listFx = new ArrayList<>();
        ObservableList<EquationData.FunctionFx.Point> tempFx = FXCollections.observableList(listFx);
        for (EquationData.FunctionFx.Point p : observableListFx) {
            for (int i = 0; i < tempFx.size(); i++) {
                if (tempFx.get(i).getX() == p.getX() && tempFx.get(i).getY() == p.getY()) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Помилка!");
                    a.setHeaderText("Помилка! Точка вже існує в таблиці!");
                    a.showAndWait();
                    observableListFx.remove(i);
                    xml.delByIndexFx(i);
                    return false;
                }
            }
            tempFx.add(p);
        }
        List<EquationData.FunctionGx.Point> listGx = new ArrayList<>();
        ObservableList<EquationData.FunctionGx.Point> tempGx = FXCollections.observableList(listGx);
        for (EquationData.FunctionGx.Point p : observableListGx) {
            for (int i = 0; i < tempGx.size(); i++) {
                if (tempGx.get(i).getX() == p.getX() && tempGx.get(i).getY() == p.getY()) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Помилка!");
                    a.setHeaderText("Помилка! Точка вже існує в таблиці!");
                    a.showAndWait();
                    observableListGx.remove(i);
                    xml.delByIndexGx(i);
                    return false;
                }
            }
            tempGx.add(p);
        }
        return true;
    }

    @FXML
    void doAddFx(ActionEvent actionEvent) {
        xml.addPFx(0, 0);
        XMLupdateTableFx();
    }

    @FXML
    void doAddGx(ActionEvent actionEvent) {
        xml.addPGx(0, 0);
        XMLupdateTableGx();
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
            double LowerBound = Double.parseDouble(lowerBound.getText());
            double UpperBound = Double.parseDouble(upperBound.getText());
            double Tolerance = Double.parseDouble(tolerance.getText());
            xml.setLowerBound(LowerBound);
            xml.setUpperBound(UpperBound);
            xml.setTolerance(Tolerance);
            try {
                if (xml.getSizeFx() < 2 || xml.getSizeGx() < 2)
                    throw new SecurityException();
                if (checker() == false)
                    doBuild();
                double h = (UpperBound - LowerBound) / 100;
                if (LowerBound >= UpperBound)
                    throw new Error();
                double temp;
                for (double x = LowerBound; x < UpperBound; x += h) {
                    for (int i = 0; i < xml.getSizeFx(); i++) {
                        temp = Interpolation.LagrangePolynomialFx(x, xml.getFuncFx());
                        if (Double.isNaN(temp))
                            throw new ArithmeticException();
                        seriesF.getData().add(new XYChart.Data<>(x, temp));
                    }
                    for (int i = 0; i < xml.getSizeGx(); i++) {
                        temp = Interpolation.LagrangePolynomialGx(x, xml.getFuncGx());
                        if (Double.isNaN(temp))
                            throw new ArithmeticException();
                        seriesG.getData().add(new XYChart.Data<>(x, temp));
                    }
                }
            } catch (Error e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Помилка!");
                a.setHeaderText("Помилка! Неправильний інтервал");
                seriesF.getData().clear();
                seriesG.getData().clear();
                Chart.getData().clear();
                ROOT.clear();
                a.showAndWait();
            } catch (SecurityException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Помилка!");
                a.setHeaderText("Помилка! Не вистачає точок (потрібно більше(або рівно) дві точки)");
                seriesF.getData().clear();
                seriesG.getData().clear();
                Chart.getData().clear();
                ROOT.clear();
                a.showAndWait();
            }
            ROOT.clear();
            Chart.getData().addAll(seriesF, seriesG);
            for (double x = LowerBound; x < UpperBound; x += 0.1) {
                String temp = Dichotomy.dichotomyMethod(x, x + 0.1, Double.parseDouble(tolerance.getText()), xml.getFuncFx(), xml.getFuncGx());
                if (!temp.isEmpty())
                    count++;
            }
            if (count == Math.abs((UpperBound - LowerBound) * 10) || count - 1 == Math.abs((UpperBound - LowerBound) * 10)) {
                ROOT.setText("Нескінченна кількість коренів!");
                ll.add("Нескінченна кількість коренів!");
            } else {
                for (double x = LowerBound; x < UpperBound; x += 0.1) {
                    String temp = Dichotomy.dichotomyMethod(x, x + 0.1, Double.parseDouble(tolerance.getText()), xml.getFuncFx(), xml.getFuncGx());
                    if (!temp.isEmpty()) {
                        ROOT.appendText(temp + "\n");
                        ll.add(temp);
                        count++;
                    }
                }
                if (ROOT.getText().isEmpty()) {
                    ROOT.setText("На цьому інтервалі немає коренів!");
                    ll.add("На цьому інтервалі немає коренів!");
                }
            }
        } catch (ArithmeticException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Помилка!");
            a.setHeaderText("Помилка! Графіка з такими точками не існує! Потрібно перевірити точки!");
            Chart.getData().clear();
            ROOT.clear();
            a.showAndWait();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Помилка!");
            a.setHeaderText("Помилка! Неправильні дані");
            Chart.getData().clear();
            ROOT.clear();
            a.showAndWait();
        }
    }

    @FXML
    void doRemoveFx(ActionEvent event) {
        int t = tableFx.getSelectionModel().getFocusedIndex();
        if (observableListFx == null)
            return;
        if (observableListFx.size() > 0) {
            observableListFx.remove(t);
            xml.delByIndexFx(t);
        }
        if (observableListFx.size() <= 0)
            observableListFx = null;
    }

    @FXML
    void doRemoveGx(ActionEvent event) {
        int t = tableGx.getSelectionModel().getFocusedIndex();
        if (observableListGx == null)
            return;
        if (observableListGx.size() > 0) {
            observableListGx.remove(t);
            xml.delByIndexGx(t);
        }
        if (observableListGx.size() <= 0)
            observableListGx = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Записуємо порожній рядок замість "No content in table":
        tableFx.setPlaceholder(new Label(""));
        tableGx.setPlaceholder(new Label(""));
    }

    private void FxUpdateX(TableColumn.CellEditEvent<EquationData.FunctionFx.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setX(t.getNewValue());
    }

    private void FxUpdateY(TableColumn.CellEditEvent<EquationData.FunctionFx.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setY(t.getNewValue());
    }

    private void GxUpdateX(TableColumn.CellEditEvent<EquationData.FunctionGx.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setX(t.getNewValue());
    }

    private void GxUpdateY(TableColumn.CellEditEvent<EquationData.FunctionGx.Point, Double> t) {
        t.getTableView().getItems().get(t.getTablePosition().getRow()).setY(t.getNewValue());
    }

    private void XMLupdateTableFx() {
        List<EquationData.FunctionFx.Point> list = new ArrayList<>();
        observableListFx = FXCollections.observableList(list);
        for (int i = 0; i < xml.getSizeFx(); i++) {
            list.add(xml.getPointFx_index(i));
        }
        tableFx.setItems(observableListFx);

        FxColumnX.setCellValueFactory(new PropertyValueFactory<>("X"));
        FxColumnX.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        FxColumnX.setOnEditCommit(t -> FxUpdateX(t));

        FxColumnY.setCellValueFactory(new PropertyValueFactory<>("Y"));
        FxColumnY.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        FxColumnY.setOnEditCommit(t -> FxUpdateY(t));
    }

    private void XMLupdateTableGx() {
        List<EquationData.FunctionGx.Point> list = new ArrayList<>();
        observableListGx = FXCollections.observableList(list);
        for (int i = 0; i < xml.getSizeGx(); i++) {
            list.add(xml.getPointGx_index(i));
        }
        tableGx.setItems(observableListGx);

        GxColumnX.setCellValueFactory(new PropertyValueFactory<>("X"));
        GxColumnX.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        GxColumnX.setOnEditCommit(t -> GxUpdateX(t));

        GxColumnY.setCellValueFactory(new PropertyValueFactory<>("Y"));
        GxColumnY.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        GxColumnY.setOnEditCommit(t -> GxUpdateY(t));
    }

    @FXML
    void doClear() {
        Chart.getData().clear();
        lowerBound.clear();
        upperBound.clear();
        tolerance.clear();
        xml.Clear();
        ROOT.clear();
        if (observableListFx == null)
            return;
        else
            observableListFx.clear();
        if (observableListGx == null)
            return;
        else
            observableListGx.clear();
    }

    @FXML
    void doClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void doNew() throws JAXBException, FileNotFoundException {
        xml = new XML();
        doClear();
        observableListFx = null;
        observableListGx = null;
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
        alert.setTitle("Помилка");
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
                lowerBound.setText(xml.getLowerBound() + "");
                upperBound.setText(xml.getUpperBound() + "");
                tolerance.setText(xml.getTolerance() + "");
                ROOT.setText("");
                // Очищаємо та оновлюємо таблицю:
                tableFx.setItems(null);
                tableGx.setItems(null);
                XMLupdateTableFx();
                XMLupdateTableGx();
                doBuild();
            } catch (IOException e) {
                showError("Файл не знайдено");
            } catch (XML.FileReadException e) {
                showError("Некоректний формат файла");
            }
        }
    }

    @FXML
    void doReport(ActionEvent event) throws IOException {
        try {
            if (observableListGx.size() < 2 || observableListFx.size() < 2 || lowerBound.getText().isEmpty() || upperBound.getText().isEmpty() || tolerance.getText().isEmpty())
                throw new Exception();

            double lower = Double.parseDouble(lowerBound.getText());
            double upper = Double.parseDouble(upperBound.getText());
            double tol = Double.parseDouble(tolerance.getText());

            WritableImage im = Chart.snapshot(new SnapshotParameters(), null);
            File fileIm = new File("D:\\JavaProjects\\CourseWork_Java\\CourseWorkGUI_KiraHovorukha\\out\\production\\CourseWork\\chart.png");
            ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", fileIm);
            FileChooser fileChooser = FileChooser("Save html-file");
            File file;
            if ((file = fileChooser.showSaveDialog(null)) != null) {
                //updateSourceData(); // оновлюємо дані в моделі
                xml.saveReport(file.getCanonicalPath(),lower, upper, tol);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Звіт створено!");
                alert.showAndWait();
                Desktop.getDesktop().open(file.getCanonicalFile());
            }
        } catch (Exception e) {
            showError("Неправильні дані для створення звіту");
        }
    }

    private void updateSourceData() {
        // Переписуємо дані в модель з observableList
        xml.setLowerBound(Double.parseDouble(lowerBound.getText()));
        xml.setUpperBound(Double.parseDouble(upperBound.getText()));
        xml.setTolerance(Double.parseDouble(tolerance.getText()));
        xml.Clear();
        for (EquationData.FunctionFx.Point c : observableListFx)
            xml.addPFx(c.getX(), c.getY());
        for (EquationData.FunctionGx.Point c : observableListGx)
            xml.addPGx(c.getX(), c.getY());
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
                alert.setHeaderText("Результати успішно збережені!");
                alert.showAndWait();
            } catch (Exception e) {
                showError("Помилка під час запису до файла");
            }
        }
    }
}
