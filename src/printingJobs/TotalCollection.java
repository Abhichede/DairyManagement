//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package printingJobs;

import main.Printsupport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class TotalCollection implements Printable {
    JTable itemTable;
    JTable totalsTable;
    String dairyName;
    String dairyAddress;
    Object[][] itemTableData;
    Object[][] totalsTableData;
    int[] pageBreaks;
    int pages = 1;
    PaymentTablePrint paymentTablePrint = new PaymentTablePrint();
    PrinterJob printerJob = null;
    static String[] title = new String[]{"PRD. Name", "Date", "Shift", "Type", "Litre", "Fat", "SNF", "Rate", "Amount"};

    public TotalCollection() {
    }

    public TotalCollection(JTable itemsTable, JTable totalsTable, String dairyName, String dairyAddress) {
        this.itemTable = itemsTable;
        this.totalsTable = totalsTable;
        this.dairyAddress = dairyAddress;
        this.dairyName = dairyName;
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
            y = (int)((imageableHeight - (double)(fontHeight + 5)) / (double)fontHeight);
            start = (this.itemTable.getRowCount() + 5) / y;
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
            g2d.drawString(this.dairyName, (int)(width / 3.0D), y);
            y += fontHeight;
            g2d.drawString(this.dairyAddress, (int)(width / 3.0D), y);
            y += fontHeight;
            g2d.drawString(Printsupport.now(), (int)(width / 3.0D) * 2, y);
            y += fontHeight;
            g2d.drawString("TOTAL COLLECTION", (int)(width / 3.0D), y);
            y += fontHeight;
            this.printerJob = PrinterJob.getPrinterJob();
            g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
            this.itemTableData = this.getTableData(this.itemTable);
            this.totalsTableData = this.getTableData(this.totalsTable);

            try {
                g2d.drawString(title[0], 10, y);
                int itemsX = 150;

                int cellX;
                for(cellX = 1; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX + 15, y);
                    if (cellX > 3) {
                        itemsX += 45;
                    } else {
                        itemsX += 60;
                    }
                }

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
                }

                cellX = 165;
                cellY = y;

                for(tblCol = start; tblCol < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); ++tblCol) {
                    for(tblCol = 1; tblCol < this.itemTable.getColumnCount() - 1; ++tblCol) {
                        g2d.drawString(this.itemTableData[tblCol][tblCol].toString(), cellX, cellY);
                        if (tblCol > 3) {
                            cellX += 45;
                        } else {
                            cellX += 60;
                        }
                    }

                    cellY += fontHeight;
                    cellX = 165;
                }

                cellX = 30;
                font = new Font("Monospaced", 1, 12);
                g2d.setFont(font);

                for(tblCol = 0; tblCol < this.totalsTable.getColumnCount() - 1; ++tblCol) {
                    g2d.drawString(this.totalsTableData[0][tblCol].toString(), cellX, cellY);
                    cellX += 60;
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
                for(int j = 0; j < nCol - 1; ++j) {
                    if (j >= 6) {
                        tableData[i][j] = dtm.getValueAt(i, j + 1);
                    } else {
                        tableData[i][j] = dtm.getValueAt(i, j);
                    }
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
