package my.ungersinc.user.shiftmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ungersinc.shiftmanager.R;

import java.util.List;

/**
 * Created by USER on 14/08/2015.
 */
public class showWorks extends Fragment {
    MySQLiteHelper db ;
    int indexChosen;

    private Intent intent;
    private List<Work> works;

    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_show_works, container, false);
        Log.d("s", "1");
        db = new MySQLiteHelper(getActivity());
        Log.d("s", "1");
        intent = new Intent(getActivity(), SetWorksActivity.class);
        works = db.getAllWorks();


        ListView list = (ListView) v.findViewById(R.id.listWorks);


        newWorkAdapter adapter = new newWorkAdapter(getActivity(), works);

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if(works.get(position)!=null) {
                    indexChosen = works.get(position).getId();
                    intent.putExtra("id", indexChosen);
                }
                startActivity(intent);

            }
        });
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        intent = new Intent(getActivity(), SetWorksActivity.class);
        works = db.getAllWorks();


        ListView list = (ListView) v.findViewById(R.id.listWorks);

        newWorkAdapter adapter = new newWorkAdapter(getActivity(), works);

        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if(works.get(position)!=null) {
                indexChosen = works.get(position).getId();
                intent.putExtra("id", indexChosen);
                }
                startActivity(intent);

            }
        });
    }

    public static showWorks newInstance(String text) {

        showWorks f = new showWorks();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
