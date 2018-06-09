package my.ungersinc.user.shiftmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ungersinc.shiftmanager.R;

import java.util.List;

/**
 * Created by USER on 14/08/2015.
 */
public class newWorkAdapter extends ArrayAdapter<Work> {
    private final Context context;
    private final List<Work> works;
    Work work;

    public newWorkAdapter(Context context , List<Work> works) {
        super(context, -1,works);
        this.context = context;
        this.works = works;
        works.add(work);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MySQLiteHelper db = new MySQLiteHelper(getContext());
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.work_ui, parent, false);
        final RelativeLayout Rl = (RelativeLayout) rowView.findViewById(R.id.backgroundUi);
        TextView workName = (TextView) rowView.findViewById(R.id.workNameU);

        if(works.get(position)!=null) {
            workName.setText(works.get(position).getWorkName());
            Rl.setBackgroundColor(Color.parseColor(works.get(position).getColor()));

        }
        else
        {
            workName.setText(R.string.WorkAdapterNewWork);
        }


        Rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(works.get(position)!=null) {
                    Rl.setBackgroundColor(Color.parseColor(works.get(position).getColor()));
                    final Intent intent = new Intent(getContext(), EventsActivity.class);

                    final AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                    builder2.setTitle(R.string.MainDelete)
                            .setMessage(R.string.SetDoDelete1)
                            .setPositiveButton(R.string.MainDelete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    for (int i = 0; i < db.getAllEvents().size(); i++) {
                                        if (db.getAllEvents().get(i).getTypeEvent() == works.get(position).getId()) {
                                            db.deleteEvent(db.getAllEvents().get(i).getId());
                                        }
                                    }
                                    db.deleteWork( works.get(position).getId());
                                    rowView.getContext().startActivity(intent);


                                }
                            })
                            .setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                    ;

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.MainDelete)
                            .setMessage(R.string.SetDoDelete2)
                            .setPositiveButton(R.string.MainDelete, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    AlertDialog alertDialogb = builder2.create();
                                    alertDialogb.show();


                                }
                            })
                            .setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialoga = builder.create();
                    alertDialoga.show();


                }

                    return false;
            }

        });

        Rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(works.get(position)!=null){
                if (event.getAction() == MotionEvent.ACTION_SCROLL)
                    Rl.setBackgroundColor(Color.parseColor(works.get(position).getColor()));
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    Rl.setBackgroundColor(Color.GRAY);
                if (event.getAction() == MotionEvent.ACTION_CANCEL)
                    Rl.setBackgroundColor(Color.parseColor(works.get(position).getColor()));}


                return false;

            }
        });
        Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SetWorksActivity.class);
                if(works.get(position)!=null)
                    intent.putExtra("id", works.get(position).getId());
                rowView.getContext().startActivity(intent);
            }
        });




        // change the icon for Windows and iPhone
        return rowView;
    }
}
