package my.ungersinc.user.shiftmanager;

/**
 * Created by USER on 03/07/2015.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ungersinc.shiftmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class newArrayAdapter extends ArrayAdapter<Event> {
    private final Context context;
    private final List<Event> events;
    private final List<Work> works;
    private int typeShow;


    public newArrayAdapter(Context context , List<Event> events,List<Work> works,int typeShow ) {

        super(context, -1, events);
        this.context = context;
        this.events = events;

            if (!events.isEmpty()){
                if (events.get(0).getDate() != null)
                    events.add(0, new Event());}
            else
            events.add(0, new Event());



        this.works = works;
        this.typeShow= typeShow;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MySQLiteHelper db = new MySQLiteHelper(getContext());


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.ui1, parent, false);

        TextView hours = (TextView) rowView.findViewById(R.id.eventHours);
        TextView date = (TextView) rowView.findViewById(R.id.eventDate);
        TextView sum = (TextView) rowView.findViewById(R.id.eventSum);
        TextView sumCash = (TextView) rowView.findViewById(R.id.eventSumCash);
        TextView plusCash = (TextView) rowView.findViewById(R.id.PlusCash);
        FrameLayout Fl = (FrameLayout) rowView.findViewById(R.id.FLColor);
        final RelativeLayout RL = (RelativeLayout) rowView.findViewById(R.id.RLid);
        ImageView imgCar = (ImageView) rowView.findViewById(R.id.imgCar);
        FrameLayout FlBreak = (FrameLayout) rowView.findViewById(R.id.breakUi);
        final TextView location = (TextView) rowView.findViewById(R.id.eventLocation);
        final ImageView bClick = (ImageView)rowView.findViewById(R.id.ClickNew);


        if (position != 0) {



            hours.setText("(" + String.valueOf(events.get(position).getHours()) + ")");

            date.setText(events.get(position).getDate());

            sum.setText(String.valueOf(events.get(position).getSum()));


            switch (typeShow) {

                case 1:
                    sumCash.setText(String.valueOf(events.get(position).getSumCash()));
                    plusCash.setText(R.string.PlusCash);
                    break;

                case 2:
                    sumCash.setText(String.valueOf(MainActivity.sumDrive(db.getWork(events.get(position).getTypeEvent()), events.get(position).getDistance(), events.get(position).getParking())));
                    plusCash.setText(R.string.PlusDrives);
                    break;

                case 3:
                    sumCash.setText(String.valueOf(events.get(position).getTipForSal()));
                    plusCash.setText(R.string.PlusBonus);
                    break;

                default:
                    sumCash.setText(String.valueOf(events.get(position).getSumCash() - events.get(position).getSum()));
                    plusCash.setText(R.string.PlusOnlyCash);

            }
            if (Double.parseDouble(String.valueOf(sumCash.getText())) == 0) {
                sumCash.setText("--");
                plusCash.setText("");
            }


            if (events.get(position).getDistance() > 0)
                imgCar.setVisibility(View.VISIBLE);


            int i = 0;
            for (boolean f = true; i < works.size() && f; i++)
                if (works.get(i).getId() == events.get(position).getTypeEvent())
                    f = false;

            Fl.setBackgroundColor(Color.parseColor(works.get(i - 1).getColor()));


            if (position > 1)
                if (Integer.parseInt(events.get(position).getDate().split("/")[1]) != Integer.parseInt(events.get(position - 1).getDate().split("/")[1]))
                    FlBreak.setVisibility(View.VISIBLE);


            if(position==1)
                FlBreak.setVisibility(View.VISIBLE);


            RL.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    RL.setBackgroundColor(Color.parseColor("#F3F3F3"));
                    final Intent intent = new Intent(getContext(), EventsActivity.class);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.MainDelete)
                            .setMessage(R.string.MainAreYouSure)
                            .setPositiveButton(R.string.MainDelete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    db.deleteEvent(events.get(position).getId());
                                    rowView.getContext().startActivity(intent);

                                }
                            })
                            .setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                    return false;
                }
            });

            RL.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_SCROLL)
                        RL.setBackgroundColor(Color.parseColor("#F3F3F3"));
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        RL.setBackgroundColor(Color.GRAY);
                    if (event.getAction() == MotionEvent.ACTION_CANCEL)
                        RL.setBackgroundColor(Color.parseColor("#F3F3F3"));


                    return false;

                }
            });
            RL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("id", events.get(position).getId());
                    rowView.getContext().startActivity(intent);
                }
            });


            location.setVisibility(View.VISIBLE);
            if (events.get(position).getLocation() != null && !events.get(position).getLocation().isEmpty())
                location.setText(events.get(position).getLocation());
            else
                location.setText(works.get(i - 1).getWorkName());


        } else {

            hours.setVisibility(View.GONE);
            date.setVisibility(View.GONE);

            sum.setVisibility(View.GONE);
            sumCash.setVisibility(View.GONE);
            plusCash.setVisibility(View.GONE);
            // Fl = (FrameLayout) rowView.findViewById(R.id.FLColor);
             RL.setBackgroundColor(Color.parseColor("#c0e6f3"));
            imgCar.setVisibility(View.GONE);
            FlBreak.setVisibility(View.GONE);

            bClick.setVisibility(View.VISIBLE);
            location.setVisibility(View.INVISIBLE);
            final SharedPreferences sharedPref = getContext().getSharedPreferences("newEvent", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();

            if(sharedPref.getBoolean("isNew",false)) {
                bClick.setBackgroundResource(R.drawable.stop_pic);
                Date timeNow = Calendar.getInstance().getTime();
                double hoursD = 0;
                if ((timeNow.getHours() + (timeNow.getMinutes() / 60.0)) - (sharedPref.getInt("hours", 0) + (sharedPref.getInt("minutes", 0) / 60.0)) >= 0) {
                    hoursD = MainActivity.Round(((timeNow.getHours() + (timeNow.getMinutes() / 60.0)) - (sharedPref.getInt("hours", 0) + (sharedPref.getInt("minutes", 0) / 60.0))), 2);
                } else
                    hoursD = MainActivity.Round(((timeNow.getHours() + (timeNow.getMinutes() / 60.0)) + 24 - (sharedPref.getInt("hours", 0) + (sharedPref.getInt("minutes", 0) / 60.0))), 2);
                date.setVisibility(View.VISIBLE);
                date.setText(String.valueOf(hoursD));
            }
            bClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentA = new Intent(getContext(), SetWorksActivity.class);
                    boolean f= true;
                    if (db.getAllWorks().size() == 0) {
                        getContext().startActivity(intentA);
                        f= false;
                    }

                    Date today = Calendar.getInstance().getTime();
                    String date = (today.getDate()+"/"+(today.getMonth()+1)+"/"+(today.getYear()+1900));

                    if(f) {
                    if(!sharedPref.getBoolean("isNew",false)) {

                        editor.putBoolean("isNew", true);
                        editor.putString("Date", date);
                        editor.putInt("hours", today.getHours());
                        editor.putInt("minutes", today.getMinutes());
                        editor.commit();
                        bClick.setBackgroundResource(R.drawable.stop_pic);
                    }
                    else {
                        editor.putBoolean("isNew", false);
                        editor.commit();
                        double hoursD = 0;
                        if ((today.getHours() + (today.getMinutes() / 60.0)) - (sharedPref.getInt("hours", 0) + (sharedPref.getInt("minutes", 0) / 60.0)) >= 0) {
                            hoursD = MainActivity.Round(((today.getHours() + (today.getMinutes() / 60.0)) - (sharedPref.getInt("hours", 0) + (sharedPref.getInt("minutes", 0) / 60.0))), 2);
                        } else
                            hoursD = MainActivity.Round(((today.getHours() + (today.getMinutes() / 60.0)) + 24 - (sharedPref.getInt("hours", 0) + (sharedPref.getInt("minutes", 0) / 60.0))), 2);

                        Event newEvent = new Event(sharedPref.getString("Date","0/0/0"), works.get(0).getId(), 0, 0, 0, 0, 0,
                                0,sharedPref.getInt("hours",0),sharedPref.getInt("minutes", 0), today.getHours(), today.getMinutes(), 0, 0, hoursD, "", "", 0, 0, "");
                        db.addEvent(newEvent);

                        int intID=0;
                        for(int i =0; i<db.getAllEvents().size();i++)
                            if(db.getAllEvents().get(i).getDate().equals(sharedPref.getString("Date","0/0/0"))&&db.getAllEvents().get(i).getHourStart()==sharedPref.getInt("hours",0)){
                                intID = db.getAllEvents().get(i).getId();
                            }




                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("id", intID);
                        getContext().startActivity(intent);
                        location.setText("start");
                    }}

                }
            });
        }
        // change t}he icon for Windows and iPhone
        return rowView;
    }
}
