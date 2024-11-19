import pointdata.EquationData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

public class XML {
    private EquationData data = new EquationData();

    @SuppressWarnings("serial")
    public static class FileException extends Exception {
        private String fileName;

        public FileException(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    @SuppressWarnings("serial")
    public static class FileReadException extends FileException {
        public FileReadException(String fileName) {
            super(fileName);
        }
    }

    @SuppressWarnings("serial")
    public static class FileWriteException extends FileException {
        public FileWriteException(String fileName) {
            super(fileName);
        }
    }

    public XML() throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance("pointdata");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        data = (EquationData) unmarshaller.unmarshal(new FileInputStream("D:\\JavaProjects\\CourseWork_Java\\CourseWorkGUI_KiraHovorukha\\src\\pointdata\\infinite_roots.xml"));

    }


    public int getSizeFx() {
        return data.getFunctionFx().getPoint().size();
    }

    public int getSizeGx() {
        return data.getFunctionGx().getPoint().size();
    }

    public List<EquationData.FunctionFx.Point> getFuncFx() {
        return data.getFunctionFx().getPoint();
    }

    public List<EquationData.FunctionGx.Point> getFuncGx() {
        return data.getFunctionGx().getPoint();
    }

    public void delByIndexFx(int index) {
        data.getFunctionFx().getPoint().remove(index);
    }

    public void delByIndexGx(int index) {
        data.getFunctionGx().getPoint().remove(index);
    }

    public EquationData.FunctionFx.Point getPointFx_index(int index) {
        return data.getFunctionFx().getPoint().get(index);
    }

    public EquationData.FunctionGx.Point getPointGx_index(int index) {
        return data.getFunctionGx().getPoint().get(index);
    }

    public void Clear() {
        if (data.getFunctionFx().getPoint() != null)
            data.getFunctionFx().getPoint().clear();
        if (data.getFunctionGx().getPoint() != null)
            data.getFunctionGx().getPoint().clear();
    }

    public double getLowerBound() {
        return data.getLowerBound();
    }

    public double getUpperBound() {
        return data.getUpperBound();
    }

    public double getTolerance() {
        return data.getTolerance();
    }

    public void setLowerBound(double lowerBound) {
        data.setLowerBound(lowerBound);
    }

    public void setUpperBound(double upperBound) {
        data.setUpperBound(upperBound);
    }

    public void setTolerance(double tolerance) {
        data.setTolerance(tolerance);
    }

    public XML readFromFile(String fileName) throws FileReadException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("pointdata");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            data = (EquationData) unmarshaller.unmarshal(new FileInputStream(fileName));
            return this;
        } catch (FileNotFoundException | JAXBException e) {
            throw new FileReadException(fileName);
        }
    }

    public boolean addPFx(double x, double y) {
        EquationData.FunctionFx.Point arr;
        arr = new EquationData.FunctionFx.Point();
        boolean result = data.getFunctionFx().getPoint().add(arr);
        data.getFunctionFx().getPoint().get(data.getFunctionFx().getPoint().size() - 1).setX(x);
        data.getFunctionFx().getPoint().get(data.getFunctionFx().getPoint().size() - 1).setY(y);
        return result;
    }

    public boolean addPGx(double x, double y) {
        EquationData.FunctionGx.Point arr;
        arr = new EquationData.FunctionGx.Point();
        boolean result = data.getFunctionGx().getPoint().add(arr);
        data.getFunctionGx().getPoint().get(data.getFunctionGx().getPoint().size() - 1).setX(x);
        data.getFunctionGx().getPoint().get(data.getFunctionGx().getPoint().size() - 1).setY(y);
        return result;
    }

    public XML writeToFile(String fileName) throws FileWriteException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("pointdata");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(data, new FileWriter(fileName));
            return this;
        } catch (IOException | JAXBException e) {
            throw new FileWriteException(fileName);
        }
    }

    public XML saveReport(String fileName, double LowerBound, double UpperBound, double Tolerance) throws FileWriteException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")))
        {
            out.printf("<h1 style=\"text-align: center;\">Звіт</h1>%n");
            out.printf("<p style=\"text-align: center;\">Чисельне знаходження коренів рівняння методом дихотомії</p>%n");

            // Виведення параметрів LowerBound, UpperBound, Tolerance
            out.printf("<h2>Параметри</h2>%n");
            out.printf("<p>Нижня межа: %8.2f</p>%n", LowerBound);
            out.printf("<p>Верхня межа: %8.2f</p>%n", UpperBound);
            out.printf("<p>Точність: %8.9f</p>%n", Tolerance);

            out.printf("<p style=\"text-align: center;\"></p>%n");
            out.printf("<p>f(x)</p>%n");
            out.printf("<table border=\"1\" style=\"height: 55px; width: 5.31628%%; border-collapse: collapse;\" height=\"72\"><caption>X&nbsp; Y</caption>%n");
            out.printf("<tbody>%n");
            for (int i = 0; i < getSizeFx(); i++) {
                out.printf("<tr style=\"height: 15px;\">%n");
                out.printf("<td style=\"width: 10.0852%%; height: 15px;\">%8.1f</td>%n", getPointFx_index(i).getX());
                out.printf("<td style=\"width: 22.7273%%; height: 15px;\">%8.1f</td>%n", getPointFx_index(i).getY());
                out.printf("</tr>%n");
            }
            out.printf("</tbody>%n");
            out.printf("</table>%n");
            out.printf("<p>g(x)</p>%n");
            out.printf("<table border=\"1\" height=\"72\" style=\"height: 60px; width: 5.31628%%; border-collapse: collapse;\"><caption>X&nbsp; Y</caption>%n");
            out.printf("<tbody>%n");
            for (int i = 0; i < getSizeGx(); i++) {
                out.printf("<tr style=\"height: 15px;\">%n");
                out.printf("<td style=\"width: 10.0852%%; height: 15px;\">%8.1f</td>%n", getPointGx_index(i).getX());
                out.printf("<td style=\"width: 22.7273%%; height: 15px;\">%8.1f</td>%n", getPointGx_index(i).getY());
                out.printf("</tr>%n");
            }
            out.printf("</tbody>%n");
            out.printf("</table>%n");
            out.printf("<p></p>%n");
            out.printf("<table border=\"1\" style=\"height: 20px; width: 0%%; border-collapse: collapse; margin-left: auto; margin-right: auto;\" height=\"20\"><caption>  </caption>%n");
            out.printf("<p style=\"text-align: left;\"></p>%n");
            out.printf("<tbody>%n");
            out.printf("<h2>Результат</h2>%n");
            out.printf("<tr>%n");
            out.printf("<td style=\"width: 100%%;\">%n");
            //out.printf("<p> <img src=\"D:\\Курсова\\CourseWork_Java\\CourseWork\\out\\production\\CourseWork\\chart.png\"></p>%n");
            out.printf("<p> <img src=\"D:\\JavaProjects\\CourseWork_Java\\CourseWorkGUI_KiraHovorukha\\out\\production\\CourseWork\\chart.png\"></p>%n");

            for (int i = 0; i < MainController.ll.size(); i++)
                out.printf("<p style=\"text-align: center;\">" + getTextArea(MainController.ll).get(i) + "</p>%n");
            out.printf("<table border=\"1\" style=\"border-collapse: collapse; width: 100%%;\" height=\"20\">%n");
            out.printf("<tbody></tbody>%n");
            out.printf("</table>%n");
            out.printf("</td>%n");
            out.printf("</tr>%n");
            out.printf("</tbody>%n");
            out.printf("</table>%n");
            out.printf("<td style=\"width: 100%%;\">%n");
            return this;
        } catch (IOException e) {
            throw new FileWriteException(fileName);
        }
    }

    private List<String> getTextArea(List<String> s) {
        return s;
    }

}

