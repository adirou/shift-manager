package my.ungersinc.user.shiftmanager;

/**
 * Created by USER on 05/07/2015.
 */

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelLib {
    List<Event> events;
    List<Work> works;
    private String inputFile;
    String fileName;
    String[] date;
    WritableWorkbook wb;
    WritableSheet ws;
    File wbfile;
    double a;

    public ExcelLib(){}

    public void write(String fileNameOut, List<Event> events, List<Work> works, int chosenMonth, List<Integer> selected,int start, int end) throws RowsExceededException, WriteException, IOException {
        double drives = 0, sum = 0, sumWithoutDrives = 0, allDrives = 0;
        int daysEvent;
        wb = createWorkbook(fileNameOut);
        ws = createSheet(wb, "page", 0);

        writeCell(0, 0, "Date", true, ws);
        writeCell(1, 0, "Location", true, ws);
        writeCell(2, 0, "Type of shift", true, ws);
        writeCell(3, 0, "Hours", true, ws);
        writeCell(4, 0, "Sum", true, ws);
        writeCell(5, 0, "Distance / Manual  ", true, ws);
        writeCell(6, 0, "Parking", true, ws);
        writeCell(7, 0, "Drives", true, ws);
        writeCell(8, 0, "Bonus", true, ws);
        writeCell(9, 0, "Sum With Drives", true, ws);
        int j = 1;

        for (int index = 0; index < events.size(); index++) {
            daysEvent=MySQLiteHelper.dateToInt(events.get(index));
            date = events.get(index).getDate().split("/");
            Log.d("ch",String.valueOf(chosenMonth));
            Log.d("date",String.valueOf(Integer.parseInt(date[1])));
            if ((Integer.parseInt(date[1]) == chosenMonth&&selected.contains(events.get(index).getTypeEvent()))||(chosenMonth==0&&daysEvent>=start&&daysEvent<=end)){
                writeCell(0, j, events.get(index).getDate(), false, ws);
                writeCell(1, j, events.get(index).getLocation(), false, ws);

                boolean flag = true;
                int i = 0;
                for (; i < works.size() && flag; i++)
                    if (works.get(i).getId() == events.get(index).getTypeEvent())
                        flag = false;


                writeCell(2, j, String.valueOf(works.get(i - 1).getWorkName()), false, ws);
                writeCell(3, j, String.valueOf(events.get(index).getHours()), false, ws);

                drives = MainActivity.sumDrive(works.get(i - 1), events.get(index).getDistance(), events.get(index).getParking());
                writeCell(4, j, String.valueOf(events.get(index).getSum() - drives), false, ws);
                sumWithoutDrives += events.get(index).getSum() - drives;
                allDrives += drives;
                if (works.get(i - 1).getTypeOfDrive() == 0) {
                    if (events.get(index).getDistance() % 100 == 99) {
                        a = events.get(index).getDistance() / 100;
                        a = (int) a % 10;
                        writeCell(5, j, String.valueOf((int) (events.get(index).getDistance()) / 1000) + "/" + a, false, ws);
                    }
                } else if (events.get(index).getDistance() == 2)
                    writeCell(5, j, String.valueOf(events.get(index).getDistance()), false, ws);

                if (events.get(index).getParking() != 0)
                    writeCell(6, j, String.valueOf(events.get(index).getParking()), false, ws);


                if (drives != 0)
                    writeCell(7, j, String.valueOf(drives), false, ws);
                if (events.get(index).getTipForSal() != 0)
                    writeCell(8, j, String.valueOf(events.get(index).getTipForSal()), false, ws);
                sum += events.get(index).getSum();
                writeCell(9, j, String.valueOf(events.get(index).getSum()), false, ws);
                j++;
            }
        }
        writeCell(9, j, String.valueOf(MainActivity.Round(sum, 2)), true, ws);
        writeCell(7, j, String.valueOf(MainActivity.Round(allDrives, 2)), true, ws);
        writeCell(4, j, String.valueOf(MainActivity.Round(sumWithoutDrives, 2)), true, ws);


        wb.write();
        wb.close();

    }
    public void writeAll(String fileNameOut, List<Event> events, List<Work> works) throws RowsExceededException, WriteException, IOException {
        wb = createWorkbook(fileNameOut);
        ws = createSheet(wb, "page", 0);

        writeCell(0, 0, "Date", true, ws);
        writeCell(1, 0, "Location", true, ws);
        writeCell(2, 0, "Type of shift", true, ws);
        writeCell(3, 0, "Hours", true, ws);
        writeCell(4, 0, "Sum", true, ws);
        writeCell(5, 0, "Distance / Manual  ", true, ws);
        writeCell(6, 0, "Parking", true, ws);
        writeCell(7, 0, "Drives", true, ws);
        writeCell(8, 0, "Tip for salary", true, ws);
        writeCell(9, 0, "Sum With Drives", true, ws);
        int j = 1;

        for (int index = 0; index < events.size(); index++) {
                writeCell(0, j, events.get(index).getDate(), false, ws);
                writeCell(1, j, events.get(index).getLocation(), false, ws);
                writeCell(2, j, events.get(index).getComments(), false, ws);
                writeCell(3, j, String.valueOf(events.get(index).getHours()), false, ws);
                writeCell(4, j, String.valueOf(events.get(index).getHourStart()), false, ws);
                writeCell(5, j, String.valueOf(events.get(index).getMinuteStart()), false, ws);
                writeCell(6, j, String.valueOf(events.get(index).getHourEnd()), false, ws);
                writeCell(7, j, String.valueOf(events.get(index).getMinuteEnd()), false, ws);
                writeCell(8, j, String.valueOf(events.get(index).getParking()), false, ws);
                writeCell(9, j, String.valueOf(events.get(index).getDistance()), false, ws);
                writeCell(10, j, String.valueOf(events.get(index).getTipForSal()), false, ws);
                writeCell(11, j, String.valueOf(events.get(index).getWaitersTip()), false, ws);
                writeCell(12, j, String.valueOf(events.get(index).getPrivateTip()), false, ws);
                writeCell(13, j, String.valueOf(events.get(index).getManualSalary()), false, ws);
                writeCell(14, j, String.valueOf(events.get(index).getSum()), false, ws);
                writeCell(15, j, String.valueOf(events.get(index).getSumCash()), false, ws);
                writeCell(16, j, String.valueOf(events.get(index).getManager()), false, ws);
                writeCell(17, j, String.valueOf(events.get(index).getIsSmsSent()), false, ws);



                boolean flag = true;
                int i = 0;
                for (; i < works.size() && flag; i++)
                    if (works.get(i).getId() == events.get(index).getTypeEvent())
                        flag = false;


                writeCell(18, j, String.valueOf(works.get(i - 1).getId()), false, ws);
            j++;
            }
        writeCell(0, j, "WorkName", true, ws);
        j++;
        for (int index = 0; index < works.size(); index++) {
            writeCell(0, j, works.get(index).getWorkName(), false, ws);
            writeCell(1, j, works.get(index).getColor(), false, ws);
            writeCell(2, j,String.valueOf(works.get(index).getGlobal()), false, ws);
            writeCell(3, j,String.valueOf(works.get(index).getStart()), false, ws);
            writeCell(4, j,String.valueOf(works.get(index).getStepI()), false, ws);
            writeCell(5, j,String.valueOf( works.get(index).getStepII()), false, ws);
            writeCell(6, j,String.valueOf( works.get(index).getStepIII()), false, ws);
            writeCell(7, j,String.valueOf( works.get(index).getHourI()), false, ws);
            writeCell(8, j,String.valueOf( works.get(index).getHourII()), false, ws);
            writeCell(9, j,String.valueOf( works.get(index).getHourIII()), false, ws);
            writeCell(10, j,String.valueOf( works.get(index).getAddPerHour()), false, ws);
            writeCell(11, j,String.valueOf( works.get(index).getPerHour()), false, ws);
            writeCell(12, j,String.valueOf( works.get(index).getPerKilometer()), false, ws);
            writeCell(13, j,String.valueOf( works.get(index).getBonusDrive()), false, ws);
            writeCell(14, j,String.valueOf( works.get(index).getTypeOfDrive()), false, ws);
            writeCell(15, j,String.valueOf( works.get(index).getTypeOfWork()), false, ws);




        }


        wb.write();
        wb.close();

    }

    public File getWbfile() {
        return wbfile;
    }

    public void setWbfile(File wbfile) {
        this.wbfile = wbfile;
    }

    public WritableWorkbook createWorkbook(String fileNameOut) {
        //exports must use a temp file while writing to avoid memory hogging
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setUseTemporaryFileDuringWrite(true);

        //get the sdcard's directory
        File sdCard = Environment.getExternalStorageDirectory();
        //add on the your app's path
        File dir = new File(sdCard.getAbsolutePath() + "/ShiftManager");
        //make them in case they're not there
        dir.mkdirs();
        //create a standard java.io.File object for the Workbook to use
        wbfile = new File(dir, fileNameOut + ".xls");
        this.fileName = fileNameOut + ".xls";
        int i = 0;
        while (wbfile.exists()) {
            this.fileName = fileNameOut + "V" + (i++) + ".xls";
            wbfile = new File(dir, fileName);
        }

        WritableWorkbook wb = null;

        try {
            //create a new WritableWorkbook using the java.io.File and
            //WorkbookSettings from above

            wb = Workbook.createWorkbook(wbfile, wbSettings);


        } catch (IOException ex) {
            Log.e("1", ex.getStackTrace().toString());
            Log.e("1", ex.getMessage());
        }


        return wb;
    }

    public WritableSheet createSheet(WritableWorkbook wb,
                                     String sheetName, int sheetIndex) throws IOException, WriteException {
        //create a new WritableSheet and return it
        return wb.createSheet(sheetName, sheetIndex);

    }

    public void writeCell(int columnPosition, int rowPosition, String contents, boolean headerCell,
                          WritableSheet sheet) throws RowsExceededException, WriteException, IOException {
        //create a new cell with contents at position
        Label newCell = new Label(columnPosition, rowPosition, contents);


        if (headerCell) {
            //give header cells size 10 Arial bolded
            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            //center align the cells' contents
            headerFormat.setAlignment(Alignment.CENTRE);
            newCell.setCellFormat(headerFormat);
        }

        sheet.addCell(newCell);

    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<Event> read(List<Work> workList) throws IOException, BiffException {
        List<Event> listEvent= new LinkedList<Event>();

        File inputWorkbook = new File(inputFile);
        Event event;
        Work work;
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines

                int j=1;
                for (; sheet.getCell(0, j).getContents()!="WorkName"; j++) {
                    event = new Event();
                    event.setDate(sheet.getCell(0, j).getContents());
                    event.setLocation(sheet.getCell(1, j).getContents());
                    event.setComments(sheet.getCell(2, j).getContents());
                    event.setHours(Double.parseDouble(sheet.getCell(3, j).getContents()));
                    event.setHourStart(Integer.parseInt(sheet.getCell(4, j).getContents()));
                    event.setMinuteStart(Integer.parseInt(sheet.getCell(5, j).getContents()));
                    event.setHourEnd(Integer.parseInt(sheet.getCell(6, j).getContents()));
                    event.setMinuteEnd(Integer.parseInt(sheet.getCell(7, j).getContents()));
                    event.setParking(Integer.parseInt(sheet.getCell(8, j).getContents()));
                    event.setDistance(Double.parseDouble(sheet.getCell(9, j).getContents()));
                    event.setTipForSal(Integer.parseInt(sheet.getCell(10, j).getContents()));
                    event.setWaitersTip(Integer.parseInt(sheet.getCell(11,j).getContents()));
                    event.setPrivateTip(Integer.parseInt(sheet.getCell(12,j).getContents()));
                    event.setManualSalary(Integer.parseInt(sheet.getCell(13, j).getContents()));
                    event.setSum(Double.parseDouble(sheet.getCell(14, j).getContents()));
                    event.setSumCash(Double.parseDouble(sheet.getCell(15, j).getContents()));
                    event.setManager(sheet.getCell(16, j).getContents());
                    event.setIsSmsSent(Integer.parseInt(sheet.getCell(17, j).getContents()));


                    listEvent.add(event);

                }
            j++;
            for (; j < sheet.getRows(); j++) {
                work = new Work();
                work.setWorkName(sheet.getCell(0, j).getContents());
                work.setColor(sheet.getCell(1, j).getContents());
                work.setGlobal(Double.parseDouble(sheet.getCell(2, j).getContents()));
                work.setStart(Double.parseDouble(sheet.getCell(3, j).getContents()));
                work.setStepI(Double.parseDouble(sheet.getCell(4, j).getContents()));
                work.setStepII(Double.parseDouble(sheet.getCell(5, j).getContents()));
                work.setStepIII(Double.parseDouble(sheet.getCell(6, j).getContents()));
                work.setHourI(Double.parseDouble(sheet.getCell(7, j).getContents()));
                work.setHourII(Double.parseDouble(sheet.getCell(8, j).getContents()));
                work.setHourIII(Double.parseDouble(sheet.getCell(9, j).getContents()));
                work.setAddPerHour(Double.parseDouble(sheet.getCell(10, j).getContents()));
                work.setPerHour(Double.parseDouble(sheet.getCell(11, j).getContents()));
                work.setPerKilometer(Double.parseDouble(sheet.getCell(12, j).getContents()));
                work.setBonusDrive(Double.parseDouble(sheet.getCell(13, j).getContents()));
                work.setTypeOfDrive(Integer.parseInt(sheet.getCell(14, j).getContents()));
                work.setTypeOfWork(Integer.parseInt(sheet.getCell(15, j).getContents()));

                workList.add(work);

            }



        } catch (BiffException e) {
            e.printStackTrace();
        }
        return listEvent;

    }

    public List<Work> getWorks() {
        return works;
    }
}
