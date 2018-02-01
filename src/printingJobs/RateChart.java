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

public class RateChart implements Printable {
    JTable itemTable;
    Object[][] itemTableData;
    PaymentTablePrint paymentTablePrint = new PaymentTablePrint();
    String[] titles;

    public RateChart() {
    }

    public RateChart(JTable itemsTable, String[] titles) {
        this.titles = titles;
        this.itemTable = itemsTable;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = 1;
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D)graphics;
            double width = pageFormat.getImageableWidth();
            double height = pageFormat.getImageableHeight();
            g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
            Font font = new Font("Monospaced", 1, 10);
            g2d.setFont(font);
            this.itemTableData = this.getTableData(this.itemTable);

            try {
                int y = 15;
                int itemsX = 5;
                g2d.drawLine(2, 1, 36 * this.itemTable.getColumnCount(), 1);

                int cellX;
                for(cellX = 0; cellX < this.titles.length; ++cellX) {
                    g2d.drawString(this.titles[cellX], itemsX, y);
                    itemsX += 36;
                }

                g2d.drawLine(2, 20, 36 * this.itemTable.getColumnCount(), 20);
                g2d.drawLine(2, 5, 2, 13 * this.itemTable.getRowCount());
                g2d.drawLine(36 * this.itemTable.getColumnCount(), 5, 36 * this.itemTable.getColumnCount(), 13 * this.itemTable.getRowCount());
                g2d.drawLine(2, 13 * this.itemTable.getRowCount(), 36 * this.itemTable.getColumnCount(), 13 * this.itemTable.getRowCount());
                font = new Font("Monospaced", 0, 10);
                g2d.setFont(font);
                cellX = 5;
                int cellY = y + 15;

                for(int tblRow = 0; tblRow < this.itemTable.getRowCount(); ++tblRow) {
                    for(int tblCol = 0; tblCol < this.itemTable.getColumnCount(); ++tblCol) {
                        g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        cellX += 36;
                    }

                    cellY += 12;
                    cellX = 5;
                    if (tblRow != this.itemTable.getRowCount() - 1) {
                        g2d.drawLine(2, cellY - 10, 36 * this.itemTable.getColumnCount() - 1, cellY - 10);
                    }
                }
            } catch (Exception var17) {
                var17.printStackTrace();
            }

            result = 0;
        }

        return result;
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
