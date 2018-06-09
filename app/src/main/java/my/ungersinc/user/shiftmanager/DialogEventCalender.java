package my.ungersinc.user.shiftmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ungersinc.shiftmanager.R;

import java.util.List;

/**
 * Created by USER on 09/07/2015.
 */
public class DialogEventCalender extends DialogFragment {
    MySQLiteHelper db ;
    private int idEvent, idEvent2;
    private String str="Update",dateT="s";
    FrameLayout FlR,FlL,FlR2,FlL2;
    RelativeLayout RL2,RL1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();



        View v = inflater.inflate(R.layout.dialog_event_calendar, null);
        TextView location = (TextView) v.findViewById(R.id.locationCtv);
        TextView date = (TextView) v.findViewById(R.id.dateCtv);
        TextView sum = (TextView) v.findViewById(R.id.sumCtv);
        TextView typeShift = (TextView) v.findViewById(R.id.TypeShiftTV);
        FlL = (FrameLayout)v.findViewById(R.id.colorFL);
        FlR = (FrameLayout)v.findViewById(R.id.colorFR);
        RL1=(RelativeLayout)v.findViewById(R.id.Rl1);

        TextView location2 = (TextView) v.findViewById(R.id.locationCtv2);
        TextView date2 = (TextView) v.findViewById(R.id.dateCtv2);
        TextView sum2 = (TextView) v.findViewById(R.id.sumCtv2);
        TextView typeShift2 = (TextView) v.findViewById(R.id.TypeShiftTV2);
        FlL2 = (FrameLayout)v.findViewById(R.id.colorFL2);
        FlR2 = (FrameLayout)v.findViewById(R.id.colorFR2);
        RL2=(RelativeLayout)v.findViewById(R.id.Rl2);

        RL1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    RL1.setBackgroundColor(Color.GRAY);
                if (event.getAction() == MotionEvent.ACTION_CANCEL)
                    RL1.setBackgroundColor(Color.parseColor("#F3F3F3"));
                if (event.getAction() == MotionEvent.ACTION_UP)
                    RL1.setBackgroundColor(Color.parseColor("#F3F3F3"));

                return false;
            }
        });
        RL2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    RL2.setBackgroundColor(Color.GRAY);
                if (event.getAction() == MotionEvent.ACTION_CANCEL)
                    RL2.setBackgroundColor(Color.parseColor("#F3F3F3"));
                if (event.getAction() == MotionEvent.ACTION_UP)
                    RL2.setBackgroundColor(Color.parseColor("#F3F3F3"));

                return false;
            }
        });

        idEvent =  getArguments().getInt("shift",9999);
        idEvent2 =  getArguments().getInt("shift2",9999);
        dateT = getArguments().getString("date");


        if((idEvent!=9999)) {
            db = new MySQLiteHelper(getActivity());
            List<Event> events = db.getAllEvents();

            FlL.setBackgroundColor(Color.parseColor(db.getWork(db.getEvent(idEvent).getTypeEvent()).getColor()));
            FlR.setBackgroundColor(Color.parseColor(db.getWork(db.getEvent(idEvent).getTypeEvent()).getColor()));

            location.setText(db.getEvent(idEvent).getLocation()+"("+db.getEvent(idEvent).getHours()+")");
            date.setText(db.getEvent(idEvent).getDate());
            sum.setText(String.valueOf(db.getEvent(idEvent).getSum()));
            typeShift.setText(db.getWork(db.getEvent(idEvent).getTypeEvent()).getWorkName());
            str=getString(R.string.DialogCalenderUpdate);

            RL2.setVisibility(View.GONE);
            RL1.setVisibility(View.VISIBLE);
            RL1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("id", idEvent);
                    startActivity(intent);
                }
            });

        }
        if(idEvent2!=9999) {

            db = new MySQLiteHelper(getActivity());
            List<Event> events = db.getAllEvents();

            FlL2.setBackgroundColor(Color.parseColor(db.getWork(db.getEvent(idEvent2).getTypeEvent()).getColor()));
            FlR2.setBackgroundColor(Color.parseColor(db.getWork(db.getEvent(idEvent2).getTypeEvent()).getColor()));

            location2.setText(db.getEvent(idEvent2).getLocation()+"("+db.getEvent(idEvent2).getHours()+")");
            date2.setText(db.getEvent(idEvent2).getDate());
            sum2.setText(String.valueOf(db.getEvent(idEvent2).getSum()));
            typeShift2.setText(db.getWork(db.getEvent(idEvent2).getTypeEvent()).getWorkName());
            RL2.setVisibility(View.VISIBLE);
            RL2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("id", idEvent2);
                    startActivity(intent);
                }
            });

        }

        if((idEvent==9999)&&(idEvent2==9999)) {
            location.setText(R.string.DialogCalenderDontHave);
            date.setVisibility(View.INVISIBLE);
            sum.setVisibility(View.INVISIBLE);
            typeShift.setVisibility(View.GONE);
            str=getString(R.string.DialogCalenderNewShift);
            RL2.setVisibility(View.GONE);


        }
        if(idEvent2==9999) {
            builder.setPositiveButton(str, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("id", idEvent);
                    intent.putExtra("date", dateT);
                    startActivity(intent);

                }
            });
        }

        builder.setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getDialog().dismiss();
            }
        });

        builder.setView(v);
        AlertDialog alert = builder.create();
      return alert;
    }


}
