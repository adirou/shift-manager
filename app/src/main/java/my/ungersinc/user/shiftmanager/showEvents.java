package my.ungersinc.user.shiftmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ungersinc.shiftmanager.R;

import java.util.List;


public class showEvents extends Fragment {
    MySQLiteHelper db ;
    int indexChosen;

    private Intent intent;
    private List<Event> events;
    private int typeShow=0;
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.activity_show_events, container, false);
        db = new MySQLiteHelper(getActivity());
        intent = new Intent(getActivity(), MainActivity.class);
        events = db.getAllEvents();


         final ListView list = (ListView) v.findViewById(R.id.list);

        newArrayAdapter adapter = new newArrayAdapter(getActivity(), events, db.getAllWorks(),typeShow);

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                indexChosen = events.get(position).getId();
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("id", indexChosen);
                startActivity(intent);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabSmall);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                typeShow++;
                if(typeShow>3)
                    typeShow=0;
                newArrayAdapter adapter2 = new newArrayAdapter(getActivity(), events, db.getAllWorks(),typeShow);
                list.setAdapter(adapter2);
            }
        });
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        intent = new Intent(getActivity(), MainActivity.class);
        events = db.getAllEvents();


        ListView list = (ListView) v.findViewById(R.id.list);

        newArrayAdapter adapter = new newArrayAdapter(getActivity(), events,db.getAllWorks(),typeShow);

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                indexChosen = events.get(position).getId();
                intent.putExtra("id", indexChosen);
                startActivity(intent);

            }
        });
    }

    public static showEvents newInstance(String text) {

        showEvents f = new showEvents();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}


