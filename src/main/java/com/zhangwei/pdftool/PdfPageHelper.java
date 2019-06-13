package com.zhangwei.pdftool;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

public class PdfPageHelper extends PdfPageEventHelper {
    // 模板
    public PdfTemplate total = null;

    private BaseFont baseFont;

    // 文档打开时创建模板
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(50, 50);
        BaseFont baseFontChinese = null;
        try {
            baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            this.baseFont = baseFontChinese;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 关闭每页的时候，写入页眉，写入'第几页共'这几个字。
    public void onEndPage(PdfWriter writer, Document document) {
        Font textFont = new Font(this.baseFont, 10);

        int pageS = writer.getPageNumber();
        String foot1 = "第 " + pageS + " 页 共  ";
        Phrase footer = new Phrase(foot1, textFont);

        // 4.拿到当前的PdfContentByte
        PdfContentByte content = writer.getDirectContent();

        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, footer,
                (document.rightMargin() + document.right() + document.leftMargin() - document.left() - baseFont.getWidthPoint(foot1,10)) / 2.0F+ 20F,
                document.bottom() - 10, 0);

        content.addTemplate(total,
                (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20F,
                document.bottom() - 10);

    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        total.beginText();
        total.setFontAndSize(baseFont,10);
        String foot2 = Integer.toString(writer.getPageNumber()) + " 页";
        total.showText(foot2);
        total.endText();
        total.closePath();
    }
}
