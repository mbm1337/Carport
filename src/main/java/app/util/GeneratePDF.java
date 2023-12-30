package app.util;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

//https://www.javatpoint.com/java-create-pdf

public class GeneratePDF {

    private Document doc;
    private PdfWriter writer;


    public GeneratePDF(int orderNumber) {
        this.doc = new Document();
        try {
            this.writer = PdfWriter.getInstance(doc, new FileOutputStream("src\\main\\resources\\pdf\\"+orderNumber+".pdf"));
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        doc.open();
    }

    public void close() {
        doc.close();
        writer.close();
    }


    public void addParagraph(String text) {
        try {
            doc.add(new Paragraph(text));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void addSVG(String path) {
        try {
            doc.add(new Paragraph(path));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}
