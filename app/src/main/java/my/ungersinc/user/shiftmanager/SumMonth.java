package my.ungersinc.user.shiftmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ungersinc.shiftmanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class SumMonth extends Fragment {
    Spinner spinnerMonth;
    TextView sumTxt, sumCashTxt, tipTxt, drivesTxt, distanceTxt, parkingTxt, avgTxt, numEventTxt,hoursTxt,avgHourSalaryTxt, btnWorks,fromDate,tillDate,tillC,fromC;
    Button btn;
    final File file = new File(Environment.getExternalStorageDirectory() + "/Folder/File.xls");
    ArrayList<Integer> listChecked;

    public List<Integer>  listIdSelectedWorks =new LinkedList<Integer>();


    MySQLiteHelper db;
    private List<Event> events;


    private double sum = 0, sumCash = 0, drives = 0,distance = 0, parking = 0, tipWaiters = 0, tipPrivate = 0;
    private int  travelers = 0, chosenMonth = 0, numEvent = 0,hours=0,avgHours=0,month,fromG=0,tillG=0;
    boolean flag=true, flagDate=false;;
    String[] date;
    String dateS;
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.activity_sum_month, container, false);

        db = new MySQLiteHelper(getActivity());
        listChecked = new ArrayList<>();
        for(int i = 0 ; i<db.getAllWorks().size();i++)
            listIdSelectedWorks.add(db.getAllWorks().get(i).getId());


        events = db.getAllEvents();
        btn = (Button) v.findViewById(R.id.buttonExport);
        btnWorks = (TextView)v.findViewById(R.id.btnWorks);

        sumTxt = (TextView) v.findViewById(R.id.sumMonthTV);
        sumCashTxt = (TextView) v.findViewById(R.id.sumCashMonthTV);
        drivesTxt = (TextView) v.findViewById(R.id.drivesTV);
        tipTxt = (TextView) v.findViewById(R.id.tipCashTV);
        drivesTxt = (TextView) v.findViewById(R.id.drivesTV);
        tipTxt = (TextView) v.findViewById(R.id.tipCashTV);
        distanceTxt = (TextView) v.findViewById(R.id.distanceTV);
        parkingTxt = (TextView) v.findViewById(R.id.parkingTV);
        avgTxt = (TextView) v.findViewById(R.id.avgEventTV);
        numEventTxt = (TextView) v.findViewById(R.id.NumberEventsTV);
        hoursTxt = (TextView) v.findViewById(R.id.hoursTV);
        avgHourSalaryTxt = (TextView) v.findViewById(R.id.avgHoursTV);

        month = Calendar.getInstance().getTime().getMonth();

        spinnerMonth = (Spinner) v.findViewById(R.id.spinnerMonth);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.month_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setSelection(month + 1);




        spinnerMonth.setOnItemSelectedListener(SpinnerListener);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExcel();
            }
        });

        btnWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWork();

            }
        });
        boolean flag=false;
        tillC = (TextView) v.findViewById(R.id.tillC);
        fromC = (TextView) v.findViewById(R.id.fromC);
        fromDate = (TextView) v.findViewById(R.id.fromTv);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        int from,till,help;
                        dateS = day + "/" + (month + 1) + "/" + year + "";
                        fromDate.setText(dateS);
                        if (flagDate==true)
                        {
                            from=MySQLiteHelper.dateToInt(fromDate.getText().toString());
                            till=MySQLiteHelper.dateToInt(tillDate.getText().toString());
                            if(from>till) {
                                help = from;
                                from=till;
                                till= help;

                            }
                            fromG=from;
                            tillG=till;
                            setValues(chosenMonth,from,till);
                        }
                        else
                            flagDate=true;
                    }

                };
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        tillDate = (TextView) v.findViewById(R.id.tillTv);
        tillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        int from,till,help;
                        dateS = day + "/" + (month + 1) + "/" + year + "";
                        tillDate.setText(dateS);
                        if (flagDate==true)
                        {
                            from=MySQLiteHelper.dateToInt(fromDate.getText().toString());
                            till=MySQLiteHelper.dateToInt(tillDate.getText().toString());
                            if(from>till) {
                                help = from;
                                from=till;
                                till= help;
                            }
                            fromG=from;
                            tillG=till;
                            setValues(chosenMonth,from,till);
                        }
                        else
                            flagDate=true;
                    }

                };
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        fromDate.setVisibility(View.GONE);
        tillDate.setVisibility(View.GONE);
        fromC.setVisibility(View.GONE);
        tillC.setVisibility(View.GONE);
        return v;
    }

    public String[] getWorksName()
    {
        List<Work> works ;
        works= db.getAllWorks();
        String[] worksName = new String[works.size()];
        for(int i=0;i<works.size();i++)
        {
            worksName[i]="";
            if(works.get(i).getWorkName()!=null)
                worksName[i]=works.get(i).getWorkName();

        }
        return worksName;
    }
    public void setValues(int chosenMonth,int start,int end){

        sum = 0;
        sumCash = 0;

        tipPrivate = 0;
        tipWaiters = 0;

        drives = 0;
        distance = 0;
        parking = 0;
        numEvent = 0;
        hours=0;
        int daysEvent=0;


        spinnerMonth.setSelection(chosenMonth);

        for (int index = 0; index < events.size(); index++) {
            if(chosenMonth==0)
                daysEvent=MySQLiteHelper.dateToInt(events.get(index));

            date = events.get(index).getDate().split("/");
            if ((Integer.parseInt(date[1]) == chosenMonth&&listIdSelectedWorks.contains(events.get(index).getTypeEvent()))||(chosenMonth==0&&daysEvent>=start&&daysEvent<=end))
            {
                sum += events.get(index).getSum();
                sumCash += events.get(index).getSumCash();

                tipWaiters += events.get(index).getWaitersTip();
                tipPrivate += events.get(index).getPrivateTip();
                Log.d("check",String.valueOf(db.getWork(events.get(index).getTypeEvent()).getStartSabbaticalHour())+"."+String.valueOf(db.getWork(events.get(index).getTypeEvent()).getEndSabbaticalHour())+".");
                if(db.getWork(events.get(index).getTypeEvent()).getTypeOfDrive()==0) {
                    if (events.get(index).getDistance() % 100 == 99) {
                        distance += events.get(index).getDistance() / 1000;
                    }

                }
                else if(db.getWork(events.get(index).getTypeEvent()).getTypeOfDrive()==2)
                    distance += events.get(index).getDistance();
                parking += events.get(index).getParking();
                numEvent++;
                hours+=events.get(index).getHours();
                drives += MainActivity.sumDrive(db.getWork(events.get(index).getTypeEvent()),events.get(index).getDistance(), events.get(index).getParking());


            }
        }
        sumTxt.setText(String.valueOf(MainActivity.Round(sum, 2)));
        sumCashTxt.setText(String.valueOf(MainActivity.Round(sumCash, 2)));
        drivesTxt.setText(String.valueOf(MainActivity.Round(drives, 2)));
        tipTxt.setText(String.valueOf(MainActivity.Round((sumCash-sum),2)));
        distanceTxt.setText(String.valueOf(MainActivity.Round(distance,1)));
        parkingTxt.setText(String.valueOf(parking));
        hoursTxt.setText(String.valueOf(hours));

        if (hours != 0)
            avgHourSalaryTxt.setText(String.valueOf(MainActivity.Round(sum / hours, 2)));
        else
            avgHourSalaryTxt.setText("0");

        if (numEvent != 0)
            avgTxt.setText(String.valueOf( MainActivity.Round(sum / numEvent, 2)));
        else
            avgTxt.setText("0");


        numEventTxt.setText(String.valueOf(numEvent));


    }



    private AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView adapter, View v, int month, long lng) {
            chosenMonth=month;
            if(chosenMonth>0) {
                setValues(chosenMonth, 9999999, 0);
                fromDate.setVisibility(View.GONE);
                tillDate.setVisibility(View.GONE);
                fromC.setVisibility(View.GONE);
                tillC.setVisibility(View.GONE);
            }
            else {
                fromDate.setVisibility(View.VISIBLE);
                tillDate.setVisibility(View.VISIBLE);
                fromC.setVisibility(View.VISIBLE);
                tillC.setVisibility(View.VISIBLE);
            }


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        };


    };

    public void onClickWork() {
        Dialog dialog;
        final String[] items = getWorksName();

        final ArrayList itemsSelected = new ArrayList();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.SumSelectWorks));
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);
                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }

                    }
                })
                .setPositiveButton(getString(R.string.SumDone), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listIdSelectedWorks.clear();
                        for(int i = 0;i<items.length;i++)
                            if(itemsSelected.contains(i))
                                listIdSelectedWorks.add(db.getAllWorks().get(i).getId());
                      setValues(spinnerMonth.getSelectedItemPosition(),9999999,0);
                    }
                })
                .setNegativeButton(getString(R.string.MainCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        dialog = builder.create();
        dialog.show();

    }







    public void showExcel()  {
        try {
        ExcelLib xl = new ExcelLib();
            xl.write("month-" + chosenMonth, events, db.getAllWorks(), chosenMonth, listIdSelectedWorks,fromG,tillG);
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            File file = new File(xl.getWbfile().getAbsolutePath());
            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            myIntent.setDataAndType(Uri.fromFile(file), mimetype);
            startActivity(myIntent);
        }
        catch (Exception e) {}

    }
    public static SumMonth newInstance(String text) {

        SumMonth f = new SumMonth();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}

