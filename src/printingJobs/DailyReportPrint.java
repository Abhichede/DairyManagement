//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package printingJobs;

import main.Printsupport;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class DailyReportPrint implements Printable {
    JTable itemTable;
    JTable totalsTable;
    String dairyName;
    String dairyAddress;
    String customerName;
    Object[][] itemTableData;
    Object[][] totalsTableData;
    PaymentTablePrint paymentTablePrint = new PaymentTablePrint();
    int[] pageBreaks;
    static String[] title = new String[]{"PRD. Name", "Shift", "Type", "Litre", "Fat", "SNF", "Rate", "Amount"};

    public DailyReportPrint() {
    }

    public DailyReportPrint(JTable itemsTable, JTable totalsTable, String dairyName, String dairyAddress, String customerName) {
        this.itemTable = itemsTable;
        this.totalsTable = totalsTable;
        this.dairyAddress = dairyAddress;
        this.dairyName = dairyName;
        this.customerName = customerName;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        //int result = true;
        Graphics2D g2d = (Graphics2D)graphics;
        Font font = new Font("Monospaced", 1, 12);
        double width = pageFormat.getImageableWidth();
        double imageableHeight = pageFormat.getImageableHeight();
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics(font);
        int fontHeight = fontMetrics.getHeight();
        int currentHeight = 0;
        int y;
        int start;
        int end;
        if (this.pageBreaks == null) {
            y = (int)((imageableHeight - (double)(fontHeight * 5)) / (double)fontHeight);
            start = (this.itemTable.getRowCount() + 2) / y;
            this.pageBreaks = new int[start];

            for(end = 0; end < start; ++end) {
                this.pageBreaks[end] = (end + 1) * y;
            }
        }

        if (pageIndex > this.pageBreaks.length) {
            return 1;
        } else {
            y = fontHeight + 5;
            start = pageIndex == 0 ? 0 : this.pageBreaks[pageIndex - 1];
            end = pageIndex == this.pageBreaks.length ? this.itemTable.getRowCount() + 2 : this.pageBreaks[pageIndex];
            this.itemTableData = this.paymentTablePrint.getTableData(this.itemTable);
            this.totalsTableData = this.paymentTablePrint.getTableData(this.totalsTable);

            try {
                g2d.drawString(this.dairyName, (int)(width / 3.0D), y);
                y += fontHeight;
                g2d.drawString(this.dairyAddress, (int)(width / 3.0D), y);
                y += fontHeight;
                g2d.drawString("Report Date: " + this.customerName, 15, y);
                g2d.drawString(Printsupport.now(), (int)(width / 3.0D) * 2, y);
                y += fontHeight;
                g2d.drawString("DAILY REPORT", (int)(width / 3.0D), y);
                y += fontHeight;
                g2d.drawString(title[0], 10, y);
                int itemsX = 150;

                int cellX;
                for(cellX = 1; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX + 15, y);
                    itemsX += 60;
                }

                font = new Font("Monospaced", 0, 12);
                g2d.setFont(font);
                cellX = 8;
                int cellY = y + fontHeight;

                int tblCol;
                for(tblCol = start; tblCol < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); ++tblCol) {
                    g2d.drawString(this.itemTableData[tblCol][0].toString(), cellX, cellY);
                    cellY += fontHeight;
                    cellX = 8;
                }

                cellX = 165;
                cellY = y + fontHeight;

                for(int tblRow = start; tblRow < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); tblRow++) {
                    for(tblCol = 1; tblCol < this.itemTable.getColumnCount() - 1; ++tblCol) {
                        g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        cellX += 60;
                    }

                    cellY += fontHeight;
                    cellX = 165;
                }

                cellY += fontHeight;
                cellX = 10;
                font = new Font("Monospaced", 1, 12);
                g2d.setFont(font);

                for(tblCol = 0; tblCol < this.totalsTable.getColumnCount() - 1; ++tblCol) {
                    g2d.drawString(this.totalsTableData[0][tblCol].toString(), cellX, cellY);
                    cellX += 70;
                }
            } catch (Exception var22) {
                var22.printStackTrace();
            }

            int result = 0;
            return result;
        }
    }
}
