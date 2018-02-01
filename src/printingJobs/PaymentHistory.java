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

public class PaymentHistory implements Printable {
    JTable itemTable;
    String dairyName;
    String dairyAddress;
    String customerName;
    Object[][] itemTableData;
    Object[][] totalsTableData;
    static String[] title = new String[]{"Date", "Litres", "FAT", "SNF", "LACTO", "Rate", "Amount"};

    public PaymentHistory() {
    }

    public PaymentHistory(JTable itemsTable, String dairyName, String dairyAddress, String customerName) {
        this.itemTable = itemsTable;
        this.dairyAddress = dairyAddress;
        this.dairyName = dairyName;
        this.customerName = customerName;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = 1;
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D)graphics;
            double width = pageFormat.getImageableWidth();
            double height = pageFormat.getImageableHeight();
            g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
            Font font = new Font("Monospaced", 1, 12);
            g2d.setFont(font);
            this.itemTableData = (new RateChart()).getTableData(this.itemTable);

            try {
                int y = 10;
                g2d.drawString(this.dairyName, (int)(width / 3.0D), y);
                g2d.drawString(this.dairyAddress, (int)(width / 3.0D), y + 15);
                g2d.drawString("Name: " + this.customerName, 15, y + 30);
                g2d.drawString(Printsupport.now(), (int)(width / 3.0D) * 2, y + 30);
                g2d.drawLine(0, y + 70, (int)width, y + 70);
                y = y + 80;
                int itemsX = 10;

                int cellX;
                for(cellX = 0; cellX < title.length; ++cellX) {
                    g2d.drawString(title[cellX], itemsX + 15, y + 5);
                    if (cellX == 0) {
                        itemsX += 85;
                    } else {
                        itemsX += 80;
                    }
                }

                font = new Font("Monospaced", 0, 12);
                g2d.setFont(font);
                cellX = 8;
                int cellY = y + 30;

                for(int tblRow = 0; tblRow < this.itemTable.getRowCount(); ++tblRow) {
                    for(int tblCol = 0; tblCol < this.itemTable.getColumnCount(); ++tblCol) {
                        g2d.drawString(this.itemTableData[tblRow][tblCol].toString(), cellX, cellY);
                        if (tblCol == 0) {
                            cellX += 100;
                        } else {
                            cellX += 80;
                        }
                    }

                    cellY += 25;
                    cellX = 8;
                }
            } catch (Exception var17) {
                var17.printStackTrace();
            }

            result = 0;
        }

        return result;
    }
}
