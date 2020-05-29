package MGTUball;


    import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
    public class PDFTextExtractor {
        public static void main(String[] args) throws IOException {
            File file = new File("data\\2019-08-03.pdf");
//            File file = new File("data\\МИЭМ_0308-21.pdf");
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
//            pdfTextStripper.setStartPage(1);
//            pdfTextStripper.setEndPage(5);
            String text  = pdfTextStripper.getText(document);
            System.out.println(text);
            document.close();
        }
    }


