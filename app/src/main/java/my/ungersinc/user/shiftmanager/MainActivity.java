package my.ungersinc.user.shiftmanager;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ungersinc.shiftmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public EditText hours,tipBonus,travelers,distance,parking,privateTip,waitersTip,locationET,manualSalaryET,commentsET;
    public String date="0/0/0",location="",manager="", comments="",dateCalender;
    public Spinner spinner;
    public TextView sumTv,dateTV,timeStartTV,timeEndTV,sumCashTV,drivesP,tipP,manualSalaryTV,idForRes;
    public Button sendButton;
    public Work work;

    public int typeEventI=0,hourStart=99,minuteStart=99,hourEnd=99,minuteEnd=99,isSmsSent=0,workIdPosition=0;
    public double sum=0, hoursD=0,sumWithCash=0,manualSalary=0,distanceI=0,tipI=0,travelersI=0,parkingI=0,privateTipI=0,waitersTipI=0;
    public boolean flagIsUpdate=false,isDrives=false, flagDrive=false, flagTip=false;
    public int dayOfWeek;


    public MySQLiteHelper db = new MySQLiteHelper(this);

    public Event event;
    public int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

       /* for(int i= 0 ; i<db.getAllWorks().size();i++)
            db.deleteWork(db.getAllWorks().get(i).getId());*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        hours = (EditText) findViewById(R.id.HoursET);
        tipBonus = (EditText) findViewById(R.id.TipForsSalET);
        travelers = (EditText) findViewById(R.id.HowManyTravelersET);
        distance = (EditText) findViewById(R.id.DistanceET);
        parking = (EditText) findViewById(R.id.ParkingET);
        privateTip = (EditText) findViewById(R.id.PrivateTipET);
        waitersTip = (EditText) findViewById(R.id.WaitersTipET);
        locationET = (EditText) findViewById(R.id.locationET);
        manualSalaryTV = (TextView) findViewById(R.id.ManuallySalC);

        dateTV = (TextView) findViewById(R.id.DateTV);
        timeStartTV = (TextView) findViewById(R.id.timeStartTV);
        timeEndTV = (TextView) findViewById(R.id.timeEndTV);
        sumTv = (TextView) findViewById(R.id.sumTV);
        sumCashTV = (TextView) findViewById(R.id.sumCash);
        drivesP = (TextView) findViewById(R.id.drivesTV);
        tipP = (TextView) findViewById(R.id.TipTv);
        manualSalaryET = (EditText) findViewById(R.id.ManualSalET);
        idForRes=(TextView)findViewById(R.id.IdForRes);


        commentsET = (EditText) findViewById(R.id.CommentsET);

        drivesP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flagDrive) {
                    distance.setVisibility(View.VISIBLE);
                    parking.setVisibility(View.VISIBLE);
                    flagDrive = true;
                } else {
                    distance.setVisibility(View.GONE);
                    parking.setVisibility(View.GONE);
                    flagDrive = false;
                }

            }
        });

        tipP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flagTip) {
                    privateTip.setVisibility(View.VISIBLE);
                    waitersTip.setVisibility(View.VISIBLE);
                    tipBonus.setVisibility(View.VISIBLE);
                    flagTip = true;
                } else {
                    privateTip.setVisibility(View.GONE);
                    waitersTip.setVisibility(View.GONE);
                    tipBonus.setVisibility(View.GONE);
                    flagTip = false;
                }

            }
        });
        final Intent intentA = new Intent(this, SetWorksActivity.class);

        if (db.getAllWorks().size() == 0) {
            startActivity(intentA);
        }
        else {
            spinner = (Spinner) findViewById(R.id.spinnerTE);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getWorksName());
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            if (db.getAllWorks().get(workIdPosition).getTypeOfDrive() < 2)
                distance.setHint(R.string.MainDriveManually);



            Intent intent = getIntent();
            id = intent.getIntExtra("id", 9999);
            if (id != 9999)
                flagIsUpdate = true;

            if(!flagIsUpdate) {
                if (db.getAllWorks().get(workIdPosition).getTypeOfDrive() == 1)
                    distance.setText(String.valueOf(db.getAllWorks().get(workIdPosition).getPerKilometer()));
                if(intent.getStringExtra("date")!=null) {
                    dateCalender = intent.getStringExtra("date");
                    dateTV.setText(dateCalender);
                    date=dateCalender;
                }
            }


            if (flagIsUpdate)
                updateShift();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aaaaaaaa", String.valueOf(idForRes.getText()));
        if(Integer.parseInt(String.valueOf(idForRes.getText()))!=9999) {
            id = Integer.parseInt(String.valueOf(idForRes.getText()));
            Log.d("aaaaaaaa",String.valueOf(id));
            flagIsUpdate=true;
            updateShift();

        }

    }

    public void updateShift()
    {
        event = db.getEvent(id);
        work = db.getWork(event.getTypeEvent());


        if (event.getIsSmsSent() == 1)
            isSmsSent = 1;

        sendButton = (Button) findViewById(R.id.sendBt);
        sendButton.setText(R.string.DialogCalenderUpdate);



        Calendar d = new GregorianCalendar(Integer.parseInt(event.getDate().split("/")[2]),Integer.parseInt(event.getDate().split("/")[1])-1,Integer.parseInt(event.getDate().split("/")[0]));
        dayOfWeek = d.get(Calendar.DAY_OF_WEEK);

        Log.e("day", String.valueOf(dayOfWeek));

        dateTV.setText(event.getDate());

        if (event.getHours() != 0)
            hours.setText(Double.toString(event.getHours()));
        if (event.getSum() != 0)
            sumTv.setText(Double.toString(event.getSum()));
        if (event.getSumCash() != 0)
            sumCashTV.setText(Double.toString(event.getSumCash()));

        if (event.getManualSalary() != 0)
            manualSalaryET.setText(Double.toString(event.getManualSalary()));

        hourStart = event.getHourStart();
        minuteStart = event.getMinuteStart();
        hourEnd = event.getHourEnd();
        minuteEnd = event.getMinuteEnd();
        if (hourStart != 99)
            timeStartTV.setText(makeHourFine(hourStart, minuteStart));
        if (hourEnd != 99)
            timeEndTV.setText(makeHourFine(hourEnd, minuteEnd));

        if (event.getLocation() != null)
            locationET.setText(event.getLocation());
        if (event.getComments() != null)
            commentsET.setText(event.getComments());


        date = event.getDate();


        if (event.getTipForSal() != 0)
            tipBonus.setText(String.valueOf(event.getTipForSal()));
        if (event.getTravelers() != 0)
            travelers.setText(String.valueOf(event.getTravelers()));
        if (event.getDistance() != 0)
            distance.setText(String.valueOf(event.getDistance()));
        if (event.getParking() != 0)
            parking.setText(String.valueOf(event.getParking()));

        if (sumDrive(work, event.getDistance(), event.getParking()) != 0)
            isDrives = true;

        if (event.getPrivateTip() != 0)
            privateTip.setText(String.valueOf(event.getPrivateTip()));
        if (event.getWaitersTip() != 0)
            waitersTip.setText(String.valueOf(event.getWaitersTip()));
        int i = 0;
        boolean f = true;
        for (; i < db.getAllWorks().size() && f; i++) {
            if (event.getTypeEvent() == db.getAllWorks().get(i).getId())
                f = false;
        }
        if (!f)
            spinner.setSelection(i - 1);
        else
            spinner.setSelection(0);

    }

    public String[] getWorksName ()
        {
            List<Work> works = new LinkedList<Work>();
            works = db.getAllWorks();
            String[] worksName = new String[works.size()];
            for (int i = 0; i < works.size(); i++) {
                worksName[i] = "";
                if (works.get(i).getWorkName() != null)
                    worksName[i] = works.get(i).getWorkName();

            }
            return worksName;
        }


    public static String makeHourFine(int hour,int minute){
        String str="";
        if(hour<10)
            str+=0;
        str+=Integer.toString(hour)+":";
        if(minute<10)
            str+=0;
        str+=Integer.toString(minute);
        return str;
    }

    //function: Adjust the DatePickerDialog
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                dayOfWeek=0;
                Calendar d = new GregorianCalendar(year,month,day);
                dayOfWeek = d.get(Calendar.DAY_OF_WEEK);
                Log.d("day",String.valueOf(dayOfWeek));

                dateTV = (TextView)findViewById(R.id.DateTV);
                date=day+"/"+(month+1)+"/"+year+"";
                dateTV.setText(date);
            }

        };
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    //function: Adjust the TimeStartPickerDialog
    public void showTimeStartPickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                timeStartTV = (TextView)findViewById(R.id.timeStartTV);
                hourStart=hour;
                minuteStart=minute;
                timeStartTV.setText(makeHourFine(hour,minute));
                if(hourEnd!=99||minuteEnd!=99) {
                    if ((hourEnd + (minuteEnd / 60.0)) - (hourStart + (minuteStart / 60.0)) >= 0) {
                        hoursD = Round((hourEnd + (minuteEnd / 60.0)) - (hourStart + (minuteStart / 60.0)), 2);
                    } else
                        hoursD = Round((hourEnd + (minuteEnd / 60.0)) + 24 - (hourStart + (minuteStart / 60.0)), 2);

                    hours = (EditText) findViewById(R.id.HoursET);
                    hours.setText(Double.toString(hoursD));
                }

            }

        };
        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }
    //function: Adjust the TimeEndPickerDialog
    public void showTimeEndPickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                timeEndTV = (TextView)findViewById(R.id.timeEndTV);
                timeEndTV.setText(makeHourFine(hour, minute));
                hourEnd=hour;
                minuteEnd=minute;
                if(hourStart!=99||minuteStart!=99) {
                    if ((hourEnd + (minuteEnd / 60.0)) - (hourStart + (minuteStart / 60.0)) >= 0) {
                        hoursD =Round((hourEnd + (minuteEnd / 60.0)) - (hourStart + (minuteStart / 60.0)), 2);
                    } else
                        hoursD = Round((hourEnd + (minuteEnd / 60.0)) + 24 - (hourStart + (minuteStart / 60.0)), 2);

                    hours = (EditText) findViewById(R.id.HoursET);
                    hours.setText(Double.toString(hoursD));
                }


            }


        };
        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinner.setSelection(position);
        workIdPosition=position;
        work=db.getAllWorks().get(position);
        typeEventI=db.getAllWorks().get(position).getId();

        if(work.getTypeOfDrive()<2)
            distance.setHint(R.string.MainDriveManually);
        else
            distance.setHint(R.string.MainDistance);

        if(work.getTypeOfDrive()==1&&!flagIsUpdate)
            distance.setText(String.valueOf(work.getPerKilometer()));

        if(work.getTypeOfWork()>3) {
            manualSalaryET.setVisibility(View.VISIBLE);
            manualSalaryTV.setVisibility(View.VISIBLE);
            if (work.getTypeOfWork() == 4){
            manualSalaryTV.setText(R.string.MainPerHour);
            manualSalaryET.setHint(R.string.SetHPerHour);
            } else {
                manualSalaryTV.setText(R.string.MainManuallySalary);
                manualSalaryET.setHint(R.string.SetHSalary);
            }
        }
        else{
            manualSalaryET.setVisibility(View.GONE);
            manualSalaryTV.setVisibility(View.GONE);}

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }





    public void SendMethod(View view) {
        sendEvent();

    }


    public void sendEvent(){
        sum=0;
        sumWithCash=0;
        hoursD = 0;
        tipI = 0;
        travelersI = 0;
        distanceI = 0;
        parkingI = 0;




        if (!(hours.getText().toString().isEmpty()))
            hoursD += Double.parseDouble(hours.getText().toString());
        if (!(tipBonus.getText().toString().isEmpty()))
            tipI += Double.parseDouble(tipBonus.getText().toString());
        if (!(distance.getText().toString().isEmpty()))
            distanceI += Double.parseDouble(distance.getText().toString());
        if (!(parking.getText().toString().isEmpty()))
            parkingI += Double.parseDouble(parking.getText().toString());
        if (!(privateTip.getText().toString().isEmpty()))
            privateTipI += Double.parseDouble(privateTip.getText().toString());
        if (!(waitersTip.getText().toString().isEmpty()))
            waitersTipI += Double.parseDouble(waitersTip.getText().toString());
        if (!(locationET.getText().toString().isEmpty()))
            location = locationET.getText().toString();

        if (!(manualSalaryET.getText().toString().isEmpty()))
            manualSalary = Double.parseDouble(manualSalaryET.getText().toString());
        if (!(commentsET.getText().toString().isEmpty()))
            comments = commentsET.getText().toString();


        work= db.getAllWorks().get(workIdPosition);
        sum +=  Round(sumWork(work, hoursD, distanceI, parkingI, tipI, manualSalary), 2);

        sumWithCash = sum + privateTipI + waitersTipI;
        if(work.getTypeOfWork()==4)
            sumWithCash+=manualSalary*hoursD;

        sumTv.setText(Double.toString(sum));
        sumCashTV.setText(Double.toString(sumWithCash));

        Event newEvent = new Event(date, typeEventI, distanceI, travelersI, parkingI, tipI, privateTipI,
                waitersTipI, hourStart, minuteStart, hourEnd, minuteEnd, sum, sumWithCash, hoursD, location,manager,isSmsSent,manualSalary,comments);
        if (date != "0/0/0") {
            if (flagIsUpdate) {
                db.updateEvent(newEvent, id);
                Toast.makeText(getApplicationContext(),getString(R.string.MainUpdate)+String.valueOf(sum)+"("+String.valueOf(sumWithCash)+")", Toast.LENGTH_LONG).show();
            }
            else {
                db.addEvent(newEvent);
                Toast.makeText(getApplicationContext(),getString(R.string.MainNewShift)+String.valueOf(sum)+"("+String.valueOf(sumWithCash)+")", Toast.LENGTH_LONG).show();
                idForRes.setText(String.valueOf(db.getAllEvents().get(db.getAllEvents().size() - 1).getId()));
            }
            Intent intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), R.string.MainValidDate, Toast.LENGTH_LONG).show();

    }
    public static double sumDrive(Work work,double distance,double parking) {
        double d=0;
       int travelers;
        if(distance!=0) {
            if (work.getTypeOfDrive() == 0)
                if(distance%100==99)
                {
                    distance=distance/100;
                    travelers=(int)distance%10;

                    distance=Round(distance/10,0);

                    Log.d("s",String.valueOf(distance));
                    if(travelers>0&&travelers<6)
                        switch (travelers){
                            case 5:
                                d+=distance*1.2+25+parking;
                                break;
                            case 4:
                                d+=distance*0.9+25+parking;
                                break;
                            case 3:
                                d+=distance*0.8+25+parking;
                                break;
                            default:
                                d+=distance*0.7+parking;
                        }


                }
                else
                    d += distance + parking;
            else if (work.getTypeOfDrive() == 1)
                d += distance + parking;
            else
                d += distance * work.getPerKilometer() + work.bonusDrive + parking;
        }
        return Round(d,2);
    }

    public static double Round(double n, int digits)
    {
        double k = Math.pow(10, digits);
        return Math.floor(n * k + 0.5) / k;
    }

    public double sumWork(Work work, double hours,double distance,double parking ,double tipBonus,double manualSalary) {

        double sum=0;
        Log.e("msg",String.valueOf(work.getTypeOfWork()));
        int type = work.getTypeOfWork();
        switch (type){
            case 1:
                sum+=SumTypeGlobal(work, hours);
                break;
            case 2:
                sum+=SumTypeStepped(work, hours);
                break;

            case 3:
                sum+=SumTypeSteppedPerHour(work, hours);
                break;
            case 4:
                sum+= SumTypeManuallyPerHour (work, manualSalary);
                break;
            case 5:
                sum+=SumTypeManually(work, manualSalary);
                break;
        }
        if(distance!=0)
              sum+=sumDrive(work,distance,parking);
        sum+=tipBonus;
        return sum;
    }

    public double SumTypeGlobal (Work work,double hours)
    {
        double sum=0;
        sum+=work.getGlobal();
        return sum;
    }

    public double SumTypeSteppedPerHour (Work work,double hours)
    {

        double sum=0;
        boolean flag=false;
        Work WorkHelper;
        String startT,endT;
        startT =timeStartTV.getText().toString();
        endT=timeEndTV.getText().toString();
        int regularMinutes=0,hoursHelp,specialMinutes=0;
        int hourStartShift,hourStartNight,hourEndShift,hourEndNight,hourStartSabbath,hourEndSabbath;
        int minuteStartShift,minuteStartNight,minuteEndShift,minuteEndNight,minuteStartSabbath,minuteEndSabbath;

        hourStartShift = hourStart;
        minuteStartShift = minuteStart+hourStartShift*60;
        hourEndShift = hourEnd;
        minuteEndShift = minuteEnd+hourEndShift*60;


        if(minuteEndShift<minuteStartShift)
            minuteEndShift += 24 * 60;

        if(work.getSabbaticalPerHour()!=0&&startT!=  getString(R.string.timeStartP)&&endT!=getString(R.string.timeEndP)) {

            flag=true;
            hourStartSabbath = Integer.parseInt(work.getStartSabbaticalHour().split(":")[0]);
            hourEndSabbath= Integer.parseInt(work.getEndSabbaticalHour().split(":")[0]);
            minuteStartSabbath = Integer.parseInt(work.getStartSabbaticalHour().split(":")[1]);
            Log.d("startS",String.valueOf(minuteStartSabbath));
            minuteStartSabbath+=hourStartSabbath*60;
            Log.d("startS",String.valueOf(minuteStartSabbath));
            minuteEndSabbath = Integer.parseInt(work.getEndSabbaticalHour().split(":")[1]);
            minuteEndSabbath+=hourEndSabbath*60;



            if(dayOfWeek==6||dayOfWeek==7) {
                if (dayOfWeek == 6) {
                    minuteEndSabbath += 24 * 60;
                    if (minuteEndShift < minuteStartSabbath)
                        flag = false;
                }
                if (dayOfWeek == 7) {
                    if (minuteStartShift > minuteEndSabbath)
                        flag = false;
                }



                Log.d("starts", String.valueOf(minuteStartShift));
                Log.d("ends", String.valueOf(minuteEndShift));
                Log.d("startS", String.valueOf(minuteStartSabbath));
                Log.d("endS", String.valueOf(minuteEndSabbath));

                if (flag) {

                    if (minuteStartSabbath - minuteStartShift > 0 && dayOfWeek == 6)
                        regularMinutes += minuteStartSabbath - minuteStartShift;

                    if (minuteEndShift - minuteEndSabbath > 0)
                        regularMinutes += minuteEndShift - minuteEndSabbath;

                    Log.d("regulaM", String.valueOf(regularMinutes));
                    specialMinutes = (int) (hoursD * 60) - regularMinutes;
                    Log.d("specialM", String.valueOf(specialMinutes));
                    switch ((int) work.getSabbaticalPerHour() / 1000) {
                        case 1:
                            sum += (work.getPerHour() * 1.25) * ((double) specialMinutes / 60);
                            break;
                        case 2:
                            sum += (work.getPerHour() * 1.50) * ((double) specialMinutes / 60);
                            break;
                        case 3:
                            sum += (work.getSabbaticalPerHour() % 1000) * ((double) specialMinutes / 60);
                            break;
                        case 4:{
                            if(specialMinutes/60>8) {
                                sum +=8*1.5*work.getPerHour();
                                if((specialMinutes/60)>10) {
                                    sum += (2 * 1.75*work.getPerHour());
                                    sum +=((specialMinutes/60)-10)*2* work.getPerHour();
                                    Log.e("n1",String.valueOf(sum));
                                }
                                else {
                                    sum += ((specialMinutes/60) - 8) * 1.75 * work.getPerHour();
                                    Log.e("n2",String.valueOf(sum));
                                }
                            }
                            else {

                                sum += (specialMinutes/60) * 1.5 * work.getPerHour();
                                Log.e("n3",String.valueOf(sum));
                            }
                            break;
                        }
                    }

                    }
                    if (work.getNightPerHour() != 0&&dayOfWeek==7) {
                        int specialBackMinutes = specialMinutes;
                        double hoursDD = regularMinutes;
                        double DHoursAfter = hours - (specialBackMinutes / 60);
                        hourStartNight = Integer.parseInt(work.getStartNightHour().split(":")[0]);
                        hourEndNight = Integer.parseInt(work.getEndNightHour().split(":")[0]);
                        minuteStartNight = Integer.parseInt(work.getStartNightHour().split(":")[1]);
                        minuteStartNight += hourStartNight * 60;
                        minuteEndNight = Integer.parseInt(work.getEndNightHour().split(":")[1]);
                        minuteEndNight += hourEndNight * 60;

                        if (minuteEndNight < 13 * 60)
                            minuteEndNight += 24 * 60;

                        if (minuteStartNight < 13 * 60)
                            minuteStartNight += 24 * 60;

                        if (minuteStartNight - minuteEndSabbath > 0)
                            regularMinutes = minuteStartNight - minuteEndSabbath;

                        if (minuteEndShift - minuteEndNight > 0)
                            regularMinutes += minuteEndShift - minuteEndNight;

                        Log.d("regulaM", String.valueOf(regularMinutes));
                        specialMinutes = (int) hoursDD - regularMinutes;
                        Log.d("specialM", String.valueOf(specialMinutes));
                        switch ((int) work.getNightPerHour() / 1000) {
                            case 1:

                                sum += (work.getPerHour() * 1.25) * ((double) specialMinutes / 60);
                                Log.e("n4",String.valueOf(sum));
                                break;
                            case 2:

                                sum += (work.getPerHour() * 1.50) * ((double) specialMinutes / 60);
                                Log.e("n5",String.valueOf(sum));
                                break;
                            case 3:

                                sum += (work.getNightPerHour() % 1000) * ((double) specialMinutes / 60);
                                Log.e("n6",String.valueOf(sum));
                                break;
                            case 4:
                                if (specialMinutes > 0) {
                                    Log.e("n7",String.valueOf(sum));
                                    if (hours > 7) {
                                        if((specialBackMinutes / 60)<7){
                                           sum += (7 - (specialBackMinutes / 60)) * work.getPerHour();
                                            Log.e("n8",String.valueOf(sum));
                                        }
                                        if (hours > 9) {
                                            if (specialBackMinutes / 60 <= 7) {
                                                sum += 2 * 1.25 * work.getPerHour();
                                                sum += (DHoursAfter - 2 - (7 - (specialBackMinutes / 60))) * 1.5 * work.getPerHour();
                                                Log.e("n9", String.valueOf(sum));
                                            } else {
                                                if (specialBackMinutes / 60 < 9) {
                                                    sum += (2 - (specialBackMinutes / 60 - 7)) * 1.25 * work.getPerHour();
                                                    sum += (DHoursAfter - (2 - (specialBackMinutes / 60 - 7))) * 1.5 * work.getPerHour();
                                                    Log.e("n10", String.valueOf(sum));
                                                } else {
                                                    sum += DHoursAfter * 1.5 * work.getPerHour();
                                                    Log.e("n11", String.valueOf(sum));
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (specialBackMinutes / 60 <= 7){
                                                sum +=  (DHoursAfter-(7-specialBackMinutes/60)) * 1.25 * work.getPerHour();
                                                Log.e("n12",String.valueOf(sum));}
                                            else {
                                                sum += (2 - (specialBackMinutes / 60 - 7)) * 1.25 * work.getPerHour();
                                                Log.e("n13",String.valueOf(sum));
                                            }
                                        }
                                    } else {

                                        sum += DHoursAfter * work.getPerHour();
                                        Log.e("n14",String.valueOf(sum));
                                    }
                                    break;


                                }


                        }
                    }
                   else {
                        sum += (work.getPerHour() * ((double) regularMinutes / 60));
                        Log.e("n16",String.valueOf(sum));
                    }
                }
            else
                flag= false;
            }


       if(work.getNightPerHour()!=0&&startT!=  getString(R.string.timeStartP)&&endT!=getString(R.string.timeEndP)&&flag ==false) {
           Log.e("nn","a1");
           flag=true;
           hourStartNight = Integer.parseInt(work.getStartNightHour().split(":")[0]);
           hourEndNight = Integer.parseInt(work.getEndNightHour().split(":")[0]);
           minuteStartNight = Integer.parseInt(work.getStartNightHour().split(":")[1]);
           minuteStartNight+=hourStartNight*60;
           minuteEndNight = Integer.parseInt(work.getEndNightHour().split(":")[1]);
           minuteEndNight+=hourEndNight*60;

           if (minuteEndNight < 13*60)
               minuteEndNight += 24*60;

           if (minuteStartNight < 13*60)
               minuteStartNight += 24*60;
           if ((minuteStartShift+24*60)>(minuteStartNight)&&((minuteStartShift+24*60)<minuteEndNight))
               minuteStartShift += 24*60;

           Log.d("starts",String.valueOf(minuteStartShift));
           Log.d("ends",String.valueOf(minuteEndShift));
           Log.d("startN",String.valueOf(minuteStartNight));
           Log.d("endN",String.valueOf(minuteEndNight));

            if (minuteStartShift <minuteStartNight && minuteEndShift < minuteStartNight){
                flag = false;}
            if (flag) {

                    if (minuteStartNight - minuteStartShift > 0)
                        regularMinutes += minuteStartNight - minuteStartShift;

                    if (minuteEndShift - minuteEndNight > 0)
                        regularMinutes +=minuteEndShift - minuteEndNight;
                Log.d("regulaM",String.valueOf(regularMinutes));
                specialMinutes = (int)(hoursD*60)-regularMinutes;
                Log.d("specialM",String.valueOf(specialMinutes));
                switch ((int)work.getNightPerHour()/1000){
                    case 1:
                        Log.e("nn","s8");
                        sum+=(work.getPerHour()*1.25)*(( double)specialMinutes/60);
                        break;
                    case 2:
                        Log.e("nn","s9");
                        sum+=(work.getPerHour()*1.50)*(( double)specialMinutes/60);
                        break;
                    case 3:
                        Log.e("nn","s10");
                        sum+=(work.getNightPerHour()%1000)*(( double)specialMinutes/60);
                        break;
                    case 4:{
                        Log.e("nn","s11");
                        if(specialMinutes>0) {
                            if (hours > 7) {
                                sum += 7 * work.getPerHour();
                                if (hours > 9) {
                                    sum += (2 * 1.25 * work.getPerHour());
                                    sum += (hours - 9) * 1.5 * work.getPerHour();
                                } else
                                    sum += (hours - 7) * 1.25 * work.getPerHour();
                            } else {
                                Log.e("nn","s12");
                                sum += hours * work.getPerHour();
                            }
                        }
                        break;
                    }


                }
                if((int)work.getNightPerHour()/1000<4)
                    sum+=(work.getPerHour()*(( double)regularMinutes/60));

            }

        }
        if(flag==false){
            Log.e("nnn","s13");
        if(hours>work.getHourI()&&work.getHourI()!=0) {
            sum += work.getHourI() * work.getPerHour();
            if(hours>work.getHourII()&&work.getHourII()!=0) {
                sum += ((work.getHourII() - work.getHourI()) * work.getStepII());
                sum += work.stepIII*(hours-work.getHourII());
            }
            else
                sum+=(hours-work.getHourI())*work.getStepII();
        }
        else
        sum+=work.getPerHour()*hours;
        }
        return sum;
    }
    public double SumTypeStepped (Work work,double hours)
    {
        double sum=0;

        if (hours > work.getHourII()&& work.getStepIII()!=0 )
            sum += work.getStepIII();
        else if (hours > work.getHourI()&& work.getStepII()!=0 )
            sum += work.getStepII();
        else if (work.getGlobal()!=0 )
            sum += work.getGlobal();

        if(hours>work.getHourIII())
            sum+=(hours-work.getHourIII())*work.getAddPerHour();
        return sum;
    }
    public double SumTypeManuallyPerHour (Work work,double manualSalary)
    {
        double sum=0;
        if(manualSalary<work.getGlobal())
            sum+=(work.getGlobal()-manualSalary)*hoursD;
        return sum;
    }
    public double SumTypeManually (Work work,double manualSalary)
    {
        double sum=0;
        sum+=manualSalary;
        return sum;
    }



    public void deleteOrShowMethod() {
        final Intent intent = new Intent(this, EventsActivity.class);
        if(flagIsUpdate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.MainDelete)
                    .setMessage(R.string.MainAreYouSure)
                    .setPositiveButton(R.string.MainDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteEvent(id);
                            startActivity(intent);
                        }})
                    .setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        switch (id) {
            case R.id.action_sendShift:
                sendEvent();
                return true;


            case R.id.action_delete:
                deleteOrShowMethod();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 9999);

        Log.d("ss", Locale.getDefault().getLanguage());

        if( Locale.getDefault().getLanguage().equals("iw")||Locale.getDefault().getLanguage().equals("he")){
            getMenuInflater().inflate(R.menu.menu_main_rtl, menu);}
        else
            getMenuInflater().inflate(R.menu.menu_main, menu);

        if(id!=9999)
            getMenuInflater().inflate(R.menu.menu_main_update, menu);

        return true;
    }

}
