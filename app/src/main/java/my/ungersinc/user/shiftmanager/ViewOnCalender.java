package my.ungersinc.user.shiftmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


import com.squareup.timessquare.CalendarPickerView;
import com.ungersinc.shiftmanager.R;


import java.util.Calendar;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class ViewOnCalender extends Fragment {
    MySQLiteHelper db;
    CalendarView calendar;
    List<Event> events;
    List<Date> datesEvents;
    List<String> colors;
    String[] str;
    int flag = 0;
    Intent intent;
    final DialogFragment newDialog = new DialogEventCalender();
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_view_on_calender, container, false);
        db = new MySQLiteHelper(getActivity());
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        events = db.getAllEvents();
        datesEvents = new LinkedList<Date>() {};
        colors = new LinkedList<String>() {};


        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);


        CalendarPickerView calendar = (CalendarPickerView) v.findViewById(R.id.calendar_view);
        Date today ;
        today = Calendar.getInstance().getTime();

        Date endCal = new Date(today.getYear()+1,today.getMonth(),today.getDay());
        Log.d("23", endCal.toString());



        calendar.init(new Date(2014 - 1900, 1, 1), endCal)
                .withSelectedDate(today);
        for (int i = 0; i < events.size(); i++) {
            str = events.get(i).getDate().split("/");
            datesEvents.add(new Date(Integer.parseInt(str[2]) - 1900, Integer.parseInt(str[1]) - 1, Integer.parseInt(str[0])));
           // colors.add(db.getWork(events.get(i).getTypeEvent()).getColor());
        }
        calendar.highlightDates(datesEvents);
        calendar.setVerticalFadingEdgeEnabled(true);


        final Bundle bundle = new Bundle();
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String[] strAr;

                flag = 0;

                for (int j = 0; j < events.size() && flag<2; j++) {
                    strAr = events.get(j).getDate().split("/");
                    if (Integer.parseInt(strAr[2]) == date.getYear() + 1900 && Integer.parseInt(strAr[1]) == date.getMonth() + 1 && Integer.parseInt(strAr[0]) == date.getDate()) {
                        if(flag==0)
                             bundle.putInt("shift", events.get(j).getId());
                        else
                             bundle.putInt("shift2", events.get(j).getId());

                        flag ++;
                    }
                }
                if (flag==0) {
                    bundle.putInt("shift", 9999);
                    bundle.putInt("shift2", 9999);
                }

                if (flag==1)
                    bundle.putInt("shift2", 9999);

                bundle.putString("date",String.valueOf(date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900)));
                Log.d("s",String.valueOf(date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900)));
                newDialog.setArguments(bundle);
                newDialog.show(getFragmentManager(), "dialog");
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
        return v;
    }

    public static ViewOnCalender newInstance(String text) {

        ViewOnCalender f = new ViewOnCalender();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}


