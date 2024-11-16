import data.PointData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

public class XML {
    private PointData data = new PointData();

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
        JAXBContext jaxbContext = JAXBContext.newInstance("data");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        data = (PointData) unmarshaller.unmarshal(new FileInputStream("D:\\JavaProjects\\CourseWork_Java\\CourseWork\\src\\data\\one.xml"));

    }


    public int getSizeFX() {
        return data.getArrayFX().getPoint().size();
    }

    public int getSizeGX() {
        return data.getArrayGX().getPoint().size();
    }

    public List<PointData.ArrayFX.Point> getArrFX() {
        return data.getArrayFX().getPoint();
    }

    public List<PointData.ArrayGX.Point> getArrGX() {
        return data.getArrayGX().getPoint();
    }

    public void delByIndexFX(int index) {
        data.getArrayFX().getPoint().remove(index);
    }

    public void delByIndexGX(int index) {
        data.getArrayGX().getPoint().remove(index);
    }

    public PointData.ArrayFX.Point getPointFX_index(int index) {
        return data.getArrayFX().getPoint().get(index);
    }

    public PointData.ArrayGX.Point getPointGX_index(int index) {
        return data.getArrayGX().getPoint().get(index);
    }

    public void Clear() {
        if (data.getArrayFX().getPoint() != null)
            data.getArrayFX().getPoint().clear();
        if (data.getArrayGX().getPoint() != null)
            data.getArrayGX().getPoint().clear();
    }

    public double getA() {
        return data.getA();
    }

    public double getB() {
        return data.getB();
    }

    public double getEps() {
        return data.getEps();
    }

    public void setA(double a) {
        data.setA(a);
    }

    public void setB(double b) {
        data.setB(b);
    }

    public void setEps(double e) {
        data.setEps(e);
    }

    public XML readFromFile(String fileName) throws FileReadException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("data");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            data = (PointData) unmarshaller.unmarshal(new FileInputStream(fileName));
            return this;
        } catch (FileNotFoundException | JAXBException e) {
            throw new FileReadException(fileName);
        }
    }

    public boolean addPFX(double x, double y) {
        PointData.ArrayFX.Point arr;
        arr = new PointData.ArrayFX.Point();
        boolean result = data.getArrayFX().getPoint().add(arr);
        data.getArrayFX().getPoint().get(data.getArrayFX().getPoint().size() - 1).setX(x);
        data.getArrayFX().getPoint().get(data.getArrayFX().getPoint().size() - 1).setY(y);
        return result;
    }

    public boolean addPGX(double x, double y) {
        PointData.ArrayGX.Point arr;
        arr = new PointData.ArrayGX.Point();
        boolean result = data.getArrayGX().getPoint().add(arr);
        data.getArrayGX().getPoint().get(data.getArrayGX().getPoint().size() - 1).setX(x);
        data.getArrayGX().getPoint().get(data.getArrayGX().getPoint().size() - 1).setY(y);
        return result;
    }

    public XML writeToFile(String fileName) throws FileWriteException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("data");
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(data, new FileWriter(fileName));
            return this;
        } catch (IOException | JAXBException e) {
            throw new FileWriteException(fileName);
        }
    }

    public XML saveReport(String fileName) throws FileWriteException {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")))
        {
            out.printf("<h2 style=\"text-align: center;\">Report</h2>%n");
            out.printf("<p style=\"text-align: center;\">Interpolation of the Langrange polynomial with root search by the dichotomy method</p>%n");
            out.printf("<p style=\"text-align: center;\"></p>%n");
            out.printf("<p>f(x)</p>%n");
            out.printf("<table border=\"1\" style=\"height: 55px; width: 5.31628%%; border-collapse: collapse;\" height=\"72\"><caption>X&nbsp; Y</caption>%n");
            out.printf("<tbody>%n");
            for (int i = 0; i < getSizeFX(); i++) {
                out.printf("<tr style=\"height: 15px;\">%n");
                out.printf("<td style=\"width: 10.0852%%; height: 15px;\">%8.1f</td>%n", getPointFX_index(i).getX());
                out.printf("<td style=\"width: 22.7273%%; height: 15px;\">%8.1f</td>%n", getPointFX_index(i).getY());
                out.printf("</tr>%n");
            }
            out.printf("</tbody>%n");
            out.printf("</table>%n");
            out.printf("<p>g(x)</p>%n");
            out.printf("<table border=\"1\" height=\"72\" style=\"height: 60px; width: 5.31628%%; border-collapse: collapse;\"><caption>X&nbsp; Y</caption>%n");
            out.printf("<tbody>%n");
            for (int i = 0; i < getSizeGX(); i++) {
                out.printf("<tr style=\"height: 15px;\">%n");
                out.printf("<td style=\"width: 10.0852%%; height: 15px;\">%8.1f</td>%n", getPointGX_index(i).getX());
                out.printf("<td style=\"width: 22.7273%%; height: 15px;\">%8.1f</td>%n", getPointGX_index(i).getY());
                out.printf("</tr>%n");
            }
            out.printf("</tbody>%n");
            out.printf("</table>%n");
            out.printf("<p></p>%n");
            out.printf("<table border=\"1\" style=\"height: 20px; width: 0%%; border-collapse: collapse; margin-left: auto; margin-right: auto;\" height=\"20\"><caption>  </caption>%n");
            out.printf("<p style=\"text-align: left;\"></p>%n");
            out.printf("<tbody>%n");
            out.printf("<tr>%n");
            out.printf("<td style=\"width: 100%%;\">%n");
            out.printf("<p> <img src=\"D:\\Курсова\\CourseWork_Java\\CourseWork\\out\\production\\CourseWork\\chart.png\"></p>%n");
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

