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

public class PaymentTablePrint implements Printable {
    JTable itemTable;
    JTable totalsTable;
    String dairyName;
    String dairyAddress;
    String customerName;
    Object[][] itemTableData;
    Object[][] totalsTableData;
    static String[] title = new String[]{"Date", "Shift", "Type", "Litre", "Fat", "SNF", "Rate", "Amount"};
    int[] pageBreaks;

    public PaymentTablePrint() {
    }

    public PaymentTablePrint(JTable itemsTable, JTable totalsTable, String dairyName, String dairyAddress, String customerName) {
        this.itemTable = itemsTable;
        this.totalsTable = totalsTable;
        this.dairyAddress = dairyAddress;
        this.dairyName = dairyName;
        this.customerName = customerName;
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
                    if (j >= 5) {
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
            y = (int)((imageableHeight - (double)(fontHeight * 7)) / (double)fontHeight);
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
            end = pageIndex == this.pageBreaks.length ? this.itemTable.getRowCount() + 2 : this.pageBreaks[pageIndex];
            this.itemTableData = this.getTableData(this.itemTable);
            if (this.totalsTable != null) {
                this.totalsTableData = this.getTableData(this.totalsTable);
            }

            try {
                g2d.drawString(this.dairyName, (int)(width / 3.0D), y);
                y += fontHeight;
                g2d.drawString(this.dairyAddress, (int)(width / 3.0D), y);
                y += fontHeight;
                g2d.drawString("Name: " + this.customerName, 15, y);
                g2d.drawString(Printsupport.now(), (int)(width / 3.0D) * 2, y);
                y += fontHeight;
                g2d.drawString("Customer Bill From: " + this.itemTableData[0][0] + " To: " + this.itemTableData[this.itemTableData.length - 1][0], 15, y);
                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                y += fontHeight;
                int itemsX = 10;

                int cellX;
                for(cellX = 0; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX + 15, y);
                    itemsX += 70;
                }

                y += fontHeight;
                g2d.drawLine(0, y, (int)width, y);
                font = new Font("Monospaced", Font.PLAIN, 12);
                g2d.setFont(font);
                cellX = 8;
                int cellY = y + fontHeight;

                int tblCol;
                for(int tblRow = start; tblRow < (end > this.itemTable.getRowCount() ? this.itemTable.getRowCount() : end); tblRow++) {
                    for(tblCol = 0; tblCol < this.itemTable.getColumnCount() - 1; ++tblCol) {
                        g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        cellX += 73;
                    }

                    cellY += fontHeight;
                    cellX = 8;
                }

                if (this.totalsTableData != null) {
                    cellX = 5;
                    font = new Font("Monospaced", Font.BOLD, 12);
                    g2d.setFont(font);

                    for(tblCol = 0; tblCol < this.totalsTable.getColumnCount() - 1; ++tblCol) {
                        g2d.drawString(this.totalsTableData[0][tblCol].toString(), cellX, cellY + fontHeight);
                        cellX += 72;
                    }
                }
            } catch (Exception var22) {
                var22.printStackTrace();
            }

            int result = 0;
            return result;
        }
    }
}
