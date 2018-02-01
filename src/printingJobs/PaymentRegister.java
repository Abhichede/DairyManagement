//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package printingJobs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PaymentRegister implements Printable {
    JTable itemTable;
    JTable totalsTable;
    String dairyName;
    String dairyAddress;
    String strFromDate;
    String strToDate;
    Object[][] itemTableData;
    Object[][] totalsTableData;
    int[] pageBreaks;
    int pages = 1;
    PaymentTablePrint paymentTablePrint = new PaymentTablePrint();
    PrinterJob printerJob = null;
    static String[] title = new String[]{"Code", "PRD. Name", "Litre", "Fat", "SNF", "Rate", "Amount", "Signature"};

    public PaymentRegister() {
    }

    public PaymentRegister(JTable itemsTable, JTable totalsTable, String dairyName, String dairyAddress, String strToDate, String strFromDate) {
        this.itemTable = itemsTable;
        this.totalsTable = totalsTable;
        this.dairyAddress = dairyAddress;
        this.dairyName = dairyName;
        this.strFromDate = strFromDate;
        this.strToDate = strToDate;
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
            y = (int)(imageableHeight / (double)fontHeight) / 2 - 4;
            start = (this.itemTable.getRowCount() + 7) / y;
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
            end = pageIndex == this.pageBreaks.length ? this.itemTable.getRowCount() : this.pageBreaks[pageIndex];
            System.out.println("Start: " + start + " End: " + end);
            System.out.println("pageBreaks: " + this.pageBreaks.length);
            g2d.drawString(this.dairyName, 10, y);
            y += fontHeight;
            g2d.drawString(this.dairyAddress, 10, y);
            y += fontHeight;
            g2d.drawString("From: " + this.strToDate + " To: " + this.strFromDate, (int)(width / 2.0D), y);
            y += fontHeight;
            g2d.drawString("PAYMENT REGISTER", (int)(width / 3.0D), y);
            y += fontHeight;
            this.printerJob = PrinterJob.getPrinterJob();
            g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
            this.itemTableData = this.getTableData(this.itemTable);
            this.totalsTableData = this.getTableData(this.totalsTable);

            try {
                g2d.drawLine(0, y, (int)width, y);
                y += fontHeight;
                g2d.drawString(title[0], 8, y);
                g2d.drawString(title[1], 45, y);
                int itemsX = 180;

                int cellX;
                for(cellX = 2; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX + 15, y);
                    itemsX += 65;
                }

                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                y += fontHeight;
                font = new Font("Monospaced", 0, 12);
                g2d.setFont(font);
                cellX = 8;
                int cellY = y;
                currentHeight = y;

                int tblCol;
                for(tblCol = start; tblCol < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); ++tblCol) {
                    g2d.drawString(this.itemTableData[tblCol][0].toString(), cellX, cellY);
                    cellY += fontHeight;
                    cellX = 8;
                    currentHeight += fontHeight;
                    cellY += fontHeight;
                }

                cellX = 45;
                cellY = y;
                currentHeight = y;

                for(tblCol = start; tblCol < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); ++tblCol) {
                    g2d.drawString(this.itemTableData[tblCol][1].toString(), cellX, cellY);
                    cellY += fontHeight;
                    cellX = 38;
                    currentHeight += fontHeight;
                    cellY += fontHeight;
                }

                cellX = 195;
                cellY = y;

                for(int tblRow = start; tblRow < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); tblRow++) {
                    for(tblCol = 2; tblCol < this.itemTable.getColumnCount(); ++tblCol) {
                        g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        cellX += 65;
                    }

                    cellY += fontHeight;
                    cellX = 195;
                    g2d.drawLine(0, cellY, (int)width, cellY);
                    cellY += fontHeight;
                }

                cellX = 130;
                font = new Font("Monospaced", 1, 12);
                g2d.setFont(font);

                for(tblCol = 0; tblCol < this.totalsTable.getColumnCount(); ++tblCol) {
                    g2d.drawString(this.totalsTableData[0][tblCol].toString(), cellX, cellY);
                    cellX += 65;
                }
            } catch (Exception var22) {
                var22.printStackTrace();
            }

            int result = 0;
            return result;
        }
    }

    public Object[][] getTableData(JTable table) {
        int itemcount = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel)table.getModel();
        int nRow = dtm.getRowCount();
        int nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        if (itemcount == nRow) {
            for(int i = 0; i < nRow; ++i) {
                for(int j = 0; j < nCol; ++j) {
                    tableData[i][j] = dtm.getValueAt(i, j);
                }
            }

            if (tableData.length != itemcount) {
                this.getTableData(table);
            }
        } else {
            this.getTableData(table);
        }

        return tableData;
    }
}
