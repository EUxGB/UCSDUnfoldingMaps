package MGTUball;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Paragraph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.print.PrintService;
import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class PdfClass {

    public void convertDocPdf (String inputNameFile, String outputNameFile) {
        POIFSFileSystem fs = null;
        Document document = new Document();

        try {
            System.out.println("Starting the test");
            fs = new POIFSFileSystem(new FileInputStream(inputNameFile));

            //This class acts as the bucket that we throw all of the Word data structures into.
            HWPFDocument doc = new HWPFDocument(fs);

            WordExtractor we = new WordExtractor(doc);

            OutputStream file = new FileOutputStream(new File(outputNameFile));

            PdfWriter writer = PdfWriter.getInstance(document, file);

            Range range = doc.getRange();
            document.open();
            writer.setPageEmpty(true);
            document.newPage();
            writer.setPageEmpty(true);

            String[] paragraphs = we.getParagraphText();
            for (int i = 0; i < paragraphs.length; i++) {

                org.apache.poi.hwpf.usermodel.Paragraph pr = range.getParagraph(i);
                // CharacterRun run = pr.getCharacterRun(i);
                // run.setBold(true);
                // run.setCapitalized(true);
                // run.setItalic(true);
                paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
                System.out.println("Length:" + paragraphs[i].length());
                System.out.println("Paragraph" + i + ": " + paragraphs[i].toString());

                // add the paragraph to the document
                document.add(new Paragraph(paragraphs[i]));
            }

            System.out.println("Document testing completed");
        } catch (Exception e) {
            System.out.println("Exception during test");
            e.printStackTrace();
        } finally {
            // close the document
            document.close();
        }
    }
}
