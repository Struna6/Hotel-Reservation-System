package JavaFX.Pracownicy;

import Core.PokojeHotelowe;
import Core.Pracownicy;
import Core.Rezerwacje;
import JavaFX.Komunikaty.Blad;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class Faktura
{
    static public void drukujFakture(Rezerwacje rezerwacje, Pracownicy pracownicy)
    {
        String fileName = "Faktura VAT " + String.valueOf(rezerwacje.getId_rezerwacji());
        Document document = new Document();
        File file = new File(fileName+".pdf");
        try
        {
            file.createNewFile();
            PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));

            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font=new Font(helvetica,11);

            BaseFont helveticaB = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font2=new Font(helveticaB,16);

            document.open();
            document.addTitle("Faktura_VAT_" + String.valueOf(rezerwacje.getId_rezerwacji()));
            document.setMargins(20, 20, 20, 20);
            Date date = new Date();
            Paragraph kiedy = new Paragraph(date.toString(),font);
            kiedy.setAlignment(Element.ALIGN_RIGHT);
            document.add(kiedy);
            Paragraph tytul = new Paragraph("Faktura VAT za rezerwacje", font2);
            tytul.setAlignment(Element.ALIGN_CENTER);
            document.add(tytul);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph(rezerwacje.getKlienci().toString(),font));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("Rezerwacja numer: " + String.valueOf(rezerwacje.getId_rezerwacji()),font));
            document.add(new Paragraph("Rezerwacja obejmowala: ", font2));
            List list = new List();
            list.setListSymbol("\u2022");
            for(PokojeHotelowe p:
                rezerwacje.getPokoje())
            {
                list.add(p.toString());
            }
            document.add(list);
            document.add(new Paragraph("Rezerwacja od: " + rezerwacje.getPobyt_od().toString(),font));
            document.add(new Paragraph("Rezerwacja do: " + rezerwacje.getPobyt_do().toString(),font));
            document.add(new Paragraph("Suma: " + String.valueOf(rezerwacje.getPlatnosci().getKwota()) +"zł",font2));
            document.add(new Paragraph("\n\n\n\n"));
            document.add(new Paragraph("Zapłacono:" + rezerwacje.getPlatnosci().doFaktury(),font));

            document.close();
        }
        catch (IOException e)
        {
            Blad.show("Błąd tworzenia faktury!");
        }
        catch (DocumentException e)
        {
            Blad.show("Błąd tworzenia faktury!");
        }
    }
}
