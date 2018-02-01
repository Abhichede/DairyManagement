package printingJobs;

import main.Printsupport;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class EmployeeSalarySlip implements Printable {
    String[] values = new String[10];
    String dairyName = "";
    String dairyAddress = "";

    public EmployeeSalarySlip(String[] values, String dairyName, String dairyAddress) {
        this.values = values;
        this.dairyName = dairyName;
        this.dairyAddress = dairyAddress;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = 1;
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D)graphics;
            double width = pageFormat.getImageableWidth();
            double height = pageFormat.getImageableHeight();
            g2d.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
            Font font = new Font("Monospaced", 0, 10);
            g2d.setFont(font);

            try {
                int y = 0;
                g2d.drawString(this.dairyName, 60, y + 15);
                g2d.drawString(this.dairyAddress, 60, y + 30);
                int cH = 0;
                g2d.drawString("DATE: ", 10, y + 50);
                g2d.drawString(Printsupport.now(), 85, y + 50);
                cH = y + 70;
                String[] strLabels = new String[]{"EMP. NAME:", "AMOUNT:", "PAY. TYPE:", "PAY. DESC.", "METHOD:", "DESC:", "BONUS:", "BALANCE:"};

                for(int i = 0; i < this.values.length; ++i) {
                    g2d.drawString(strLabels[i], 10, cH + 10);
                    g2d.drawString(this.values[i], 100, cH + 10);
                    cH += 15;
                }

                g2d.drawString("----------------------", 40, cH + 20);
                g2d.drawString("Thank You", 60, cH + 30);
                g2d.drawString("----------------------", 40, cH + 40);
                font = new Font("Monospaced", 0, 7);
                g2d.setFont(font);
                g2d.drawString("Powered by: Linker IT Solutions PVT. LTD.", 20, cH + 55);
            } catch (Exception var15) {
                var15.printStackTrace();
            }

            result = 0;
        }

        return result;
    }
}
