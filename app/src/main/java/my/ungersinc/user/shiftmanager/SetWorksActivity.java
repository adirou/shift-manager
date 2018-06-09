package my.ungersinc.user.shiftmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ungersinc.shiftmanager.R;
import com.woalk.apps.lib.colorpicker.ColorPickerSwatch;
import com.woalk.apps.lib.colorpicker.ColorPickerDialog;

import java.util.Locale;

public class SetWorksActivity extends AppCompatActivity{

        // on button click

    Spinner spinnerType, spinnerDrive;
    EditText WorkNameTxt,GlobalTxt,HourITxt,AddPerHourTxt,PerHourTxt,StepIITxt,StepIIITxt,HourIITxt,HourIIITxt,PerKilometerTxt,BonusDriveTxt,perHourNTxt,perHourSTxt;
    TextView GlobalC,StepIIC,StepIIIC,PerHourC,AddPerHourC,BonusDriveC,PerKilometerC,setMessageC,buttonSend,bFromN,bTillN,bFromS,bTillS;
    boolean flagIsUpdate=false, plusIflag=false,plusIIflag=false;
    double  defaultF=0;
    Work work;
    String mSelectedColor;
    CheckBox defaultCB,cbNight,cbSabbatical;
    RadioGroup rgN,rgS;
    RadioButton r125N,r150N,rOtherN, rIsraelN, r125S,r150S,rOtherS, rIsraelS;
    GridLayout glSpecial;

    ImageButton plusI , plusII;

    public FrameLayout Fl;

    String workName,fromN,tillN,fromS,tillS;
    int typeWork,id,typeDrive;
    double global,start,hourI,addPerHour,perHour,stepI,stepII,stepIII, hourII,hourIII,perKilometer,bonusDrive,perHourN,perHourS;
    GradientDrawable GD;

    public MySQLiteHelper db = new MySQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_works);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setMessageC = (TextView) findViewById(R.id.setMessageTV);
        if (db.getAllWorks().size() == 0)
            setMessageC.setText(R.string.SetMessageFirstWork);


        mSelectedColor = "#9fffcc";
        Fl = (FrameLayout) findViewById(R.id.FLPaint);
        GD = (GradientDrawable) Fl.getBackground();
        GD.setColor(Color.parseColor(mSelectedColor));
        Fl.setBackground(GD);

        Fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ColorPickerDialog dialog = ColorPickerDialog.newInstance(
                        R.string.color_picker_default_title,
                        new int[]{Color.parseColor("#FF57F6FF"), Color.parseColor("#0894FF"), Color.parseColor("#55E800"), Color.parseColor("#E5FF00")
                                , Color.parseColor("#ffa9c8"), Color.parseColor("#FF9FFFCC"), Color.parseColor("#FFEDECE6"), Color.parseColor("#ffac43")},
                        Color.parseColor(mSelectedColor),
                        4, // Number of columns
                        ColorPickerDialog.SIZE_SMALL);

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                        mSelectedColor = String.format("#%X", color);
                        Fl = (FrameLayout) findViewById(R.id.FLPaint);
                        GD = (GradientDrawable) Fl.getBackground();
                        GD.setColor(Color.parseColor(mSelectedColor));
                        Fl.setBackground(GD);

                    }

                });

                dialog.show(getFragmentManager(), "some_tag");
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 9999);
        if (id != 9999)
            flagIsUpdate = true;


        spinnerType = (Spinner) findViewById(R.id.spinnerTypeSal);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeWork_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        spinnerDrive = (Spinner) findViewById(R.id.spinnerDrive);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.typeDrive_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDrive.setAdapter(adapter);


        perHourN = 0;
        perHourS = 0;


        WorkNameTxt = (EditText) findViewById(R.id.workNameET);
        GlobalTxt = (EditText) findViewById(R.id.salaryGlobalStartStepIET);
        GlobalTxt.setVisibility(View.GONE);
        HourITxt = (EditText) findViewById(R.id.hoursIET);
        HourIITxt = (EditText) findViewById(R.id.hoursIIET);
        HourIIITxt = (EditText) findViewById(R.id.hoursIIIET);
        StepIITxt = (EditText) findViewById(R.id.stepIIET);
        StepIIITxt = (EditText) findViewById(R.id.stepIIIET);
        AddPerHourTxt = (EditText) findViewById(R.id.addPerHourET);
        PerHourTxt = (EditText) findViewById(R.id.perHourET);
        PerKilometerTxt = (EditText) findViewById(R.id.perKilometerET);
        BonusDriveTxt = (EditText) findViewById(R.id.bonusDrivesET);
        BonusDriveTxt = (EditText) findViewById(R.id.bonusDrivesET);

        perHourNTxt = (EditText) findViewById(R.id.perHourN);
        perHourSTxt = (EditText) findViewById(R.id.perHourS);


        GlobalC = (TextView) findViewById(R.id.globalStartC);
        StepIIC = (TextView) findViewById(R.id.stepIIC);
        StepIIIC = (TextView) findViewById(R.id.stepIIIC);
        AddPerHourC = (TextView) findViewById(R.id.addPerHourC);
        PerHourC = (TextView) findViewById(R.id.perHourC);
        PerKilometerC = (TextView) findViewById(R.id.perKilometerTV);
        BonusDriveC = (TextView) findViewById(R.id.bonusDriveTV);

        //connect to ids night/sabbatical
         bFromN = (TextView) findViewById(R.id.bFromN);
        bFromS = (TextView) findViewById(R.id.bFromS);
        bTillN = (TextView) findViewById(R.id.bTillN);
        bTillS = (TextView) findViewById(R.id.bTillS);


        rgN = (RadioGroup) findViewById(R.id.rgN);
        rgS = (RadioGroup) findViewById(R.id.rgS);

        r125N = (RadioButton) findViewById(R.id.rb125N);
        r125S = (RadioButton) findViewById(R.id.rb125S);
        r150N = (RadioButton) findViewById(R.id.rb150N);
        rIsraelN = (RadioButton) findViewById(R.id.rbIsraelN);
        r150S = (RadioButton) findViewById(R.id.rb150S);
        rOtherN = (RadioButton) findViewById(R.id.rbOtherN);
        rOtherS = (RadioButton) findViewById(R.id.rbOtherS);
        rIsraelS = (RadioButton) findViewById(R.id.rbIsraelS);

        cbNight = (CheckBox) findViewById(R.id.cbN);
        cbSabbatical = (CheckBox) findViewById(R.id.cbS);

        glSpecial = (GridLayout) findViewById(R.id.gridSpecial);

        plusI = (ImageButton) findViewById(R.id.imageButtonI);
        plusI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlusI();
            }
        });


        plusII = (ImageButton) findViewById(R.id.imageButtonII);
        plusII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlusII();
            }
        });

        defaultCB = (CheckBox) findViewById(R.id.DefaultCB);
        defaultCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    defaultF = 1;
                else
                    defaultF = 0;
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // GlobalTxt.setText("");HourITxt.setText("");AddPerHourTxt.setText("");PerHourTxt.setText("");StepIITxt.setText("");StepIIITxt.setText("");HourIITxt.setText("");HourIIITxt.setText("");
                spinnerType.setSelection(position);
                typeWork = position + 1;


                switch (typeWork) {
                    case 1:
                        glSpecial.setVisibility(View.GONE);
                        GlobalTxt.setVisibility(View.VISIBLE);
                        GlobalTxt.setHint(R.string.SetHSalary);
                        HourITxt.setVisibility(View.INVISIBLE);
                        HourIITxt.setVisibility(View.GONE);
                        HourIIITxt.setVisibility(View.GONE);
                        StepIITxt.setVisibility(View.GONE);
                        StepIIITxt.setVisibility(View.GONE);
                        AddPerHourTxt.setVisibility(View.GONE);
                        PerHourTxt.setVisibility(View.GONE);

                        GlobalC.setVisibility(View.VISIBLE);
                        GlobalC.setText(R.string.SetGlobal);
                        StepIIC.setVisibility(View.GONE);
                        StepIIIC.setVisibility(View.GONE);
                        AddPerHourC.setVisibility(View.GONE);
                        PerHourC.setVisibility(View.GONE);

                        plusI.setVisibility(View.GONE);
                        plusII.setVisibility(View.GONE);

                        if (db.getAllWorks().size() == 0)
                            setMessageC.setText(getString(R.string.SetMessageFirstWork) + "\n" + getString(R.string.SetMessageGlobal));
                        else
                            setMessageC.setText(getString(R.string.SetMessageGlobal));
                        break;
                    case 2:
                        glSpecial.setVisibility(View.GONE);
                        GlobalTxt.setVisibility(View.VISIBLE);
                        GlobalTxt.setHint(R.string.SetHSalary);
                        HourITxt.setVisibility(View.INVISIBLE);
                        HourIITxt.setVisibility(View.GONE);
                        HourIIITxt.setVisibility(View.VISIBLE);
                        StepIITxt.setVisibility(View.GONE);
                        StepIIITxt.setVisibility(View.GONE);
                        AddPerHourTxt.setVisibility(View.VISIBLE);
                        PerHourTxt.setVisibility(View.GONE);


                        GlobalC.setVisibility(View.VISIBLE);
                        GlobalC.setText(R.string.SetStep);
                        StepIIC.setVisibility(View.VISIBLE);
                        StepIIIC.setVisibility(View.GONE);
                        AddPerHourC.setVisibility(View.VISIBLE);
                        PerHourC.setVisibility(View.GONE);

                        StepIIC.setText(R.string.SetStepII);
                        StepIIIC.setText(R.string.SetStepIII);
                        StepIITxt.setHint(R.string.SetHSalary);
                        StepIIITxt.setHint(R.string.SetHSalary);
                        setMessageC.setText(getString(R.string.SetMessageStepped));

                        plusI.setVisibility(View.VISIBLE);
                        plusI.setBackgroundResource(R.drawable.plus_add_green);
                        plusII.setVisibility(View.GONE);

                        break;
                    case 3:
                        glSpecial.setVisibility(View.VISIBLE);
                        GlobalTxt.setVisibility(View.GONE);
                        HourITxt.setVisibility(View.INVISIBLE);
                        HourIITxt.setVisibility(View.GONE);
                        HourIIITxt.setVisibility(View.GONE);
                        StepIITxt.setVisibility(View.GONE);
                        StepIIITxt.setVisibility(View.GONE);
                        AddPerHourTxt.setVisibility(View.GONE);
                        PerHourTxt.setVisibility(View.VISIBLE);

                        GlobalC.setVisibility(View.GONE);
                        StepIIC.setVisibility(View.VISIBLE);
                        StepIIIC.setVisibility(View.GONE);
                        AddPerHourC.setVisibility(View.GONE);
                        PerHourC.setVisibility(View.VISIBLE);

                        StepIIC.setText(R.string.SetOverTime);
                        StepIIIC.setText(R.string.SetOverTimeII);
                        StepIITxt.setHint(R.string.SetHPerHour);
                        StepIIITxt.setHint(R.string.SetHPerHour);

                        plusI.setVisibility(View.VISIBLE);
                        plusI.setBackgroundResource(R.drawable.plus_add_green);
                        plusII.setVisibility(View.GONE);


                        setMessageC.setText(getString(R.string.SetMessagePerHour));

                        break;

                    case 4:
                        glSpecial.setVisibility(View.GONE);
                        GlobalTxt.setVisibility(View.VISIBLE);
                        GlobalTxt.setHint(R.string.SetHPerHour);
                        HourITxt.setVisibility(View.INVISIBLE);
                        HourIITxt.setVisibility(View.GONE);
                        HourIIITxt.setVisibility(View.GONE);
                        StepIITxt.setVisibility(View.GONE);
                        StepIIITxt.setVisibility(View.GONE);
                        AddPerHourTxt.setVisibility(View.GONE);
                        PerHourTxt.setVisibility(View.GONE);

                        GlobalC.setVisibility(View.VISIBLE);
                        GlobalC.setText(R.string.SetMinPerHour);
                        StepIIC.setVisibility(View.GONE);
                        StepIIIC.setVisibility(View.GONE);
                        AddPerHourC.setVisibility(View.GONE);
                        PerHourC.setVisibility(View.GONE);

                        setMessageC.setText(getString(R.string.SetMessageManuallyPerHour));


                        plusI.setVisibility(View.GONE);
                        plusII.setVisibility(View.GONE);

                        break;
                    case 5:
                        glSpecial.setVisibility(View.GONE);
                        GlobalTxt.setVisibility(View.INVISIBLE);
                        HourITxt.setVisibility(View.INVISIBLE);
                        HourIITxt.setVisibility(View.GONE);
                        HourIIITxt.setVisibility(View.GONE);
                        StepIITxt.setVisibility(View.INVISIBLE);
                        StepIIITxt.setVisibility(View.GONE);
                        AddPerHourTxt.setVisibility(View.GONE);
                        PerHourTxt.setVisibility(View.GONE);

                        GlobalC.setVisibility(View.INVISIBLE);
                        StepIIC.setVisibility(View.INVISIBLE);
                        StepIIIC.setVisibility(View.GONE);
                        AddPerHourC.setVisibility(View.GONE);
                        PerHourC.setVisibility(View.GONE);

                        plusI.setVisibility(View.INVISIBLE);
                        plusII.setVisibility(View.GONE);

                        setMessageC.setText(getString(R.string.SetMessageManually));

                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        spinnerDrive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  PerKilometerTxt.setText("");BonusDriveTxt.setText("");
                spinnerDrive.setSelection(position);
                typeDrive = position;

                switch (position) {
                    case 0:
                        PerKilometerTxt.setVisibility(View.GONE);
                        BonusDriveTxt.setVisibility(View.GONE);
                        PerKilometerC.setVisibility(View.GONE);
                        BonusDriveC.setVisibility(View.GONE);

                        break;
                    case 1:
                        PerKilometerTxt.setVisibility(View.VISIBLE);
                        BonusDriveTxt.setVisibility(View.GONE);
                        PerKilometerC.setVisibility(View.VISIBLE);
                        BonusDriveC.setVisibility(View.GONE);

                        PerKilometerC.setText(R.string.SetPermanentRepayment);
                        PerKilometerTxt.setHint(R.string.SetHPermanantRepayment);
                        break;


                    case 2:
                        PerKilometerTxt.setVisibility(View.VISIBLE);
                        BonusDriveTxt.setVisibility(View.VISIBLE);
                        PerKilometerC.setVisibility(View.VISIBLE);
                        BonusDriveC.setVisibility(View.VISIBLE);

                        PerKilometerC.setText(R.string.SetPerKilometer);
                        PerKilometerTxt.setHint(R.string.SetHPerKilometer);
                        break;

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        if (flagIsUpdate)

        {

            work = db.getWork(id);
            Log.e("fromN", String.valueOf(work.getStartNightHour()));
            Log.e("tillN", String.valueOf(work.getEndNightHour()));
            Log.e("fromS", String.valueOf(work.getStartSabbaticalHour()));
            Log.e("tillS", String.valueOf(work.getEndSabbaticalHour()));
            Log.e("perN", String.valueOf(work.getNightPerHour()));
            Log.e("PerS", String.valueOf(work.getSabbaticalPerHour()));
            if (work.getStart() == 1)
                defaultCB.setChecked(true);

            mSelectedColor = work.getColor();
            Fl = (FrameLayout) findViewById(R.id.FLPaint);
            GD = (GradientDrawable) Fl.getBackground();
            GD.setColor(Color.parseColor(mSelectedColor));
            Fl.setBackground(GD);

            if (work.getGlobal() != 0)
                GlobalTxt.setText(Double.toString(work.getGlobal()));
            if (work.getHourI() != 0)
                HourITxt.setText(Double.toString(work.getHourI()));
            if (work.getHourII() != 0)
                HourIITxt.setText(Double.toString(work.getHourII()));
            if (work.getHourIII() != 0)
                HourIIITxt.setText(Double.toString(work.getHourIII()));
            if (work.getStepII() != 0)
                StepIITxt.setText(Double.toString(work.getStepII()));
            if (work.getStepIII() != 0)
                StepIIITxt.setText(Double.toString(work.getStepIII()));
            if (work.getPerHour() != 0)
                PerHourTxt.setText(Double.toString(work.getPerHour()));
            if (work.getAddPerHour() != 0)
                AddPerHourTxt.setText(Double.toString(work.getAddPerHour()));
            if (work.getPerKilometer() != 0)
                PerKilometerTxt.setText(Double.toString(work.getPerKilometer()));
            if (work.getBonusDrive() != 0)
                BonusDriveTxt.setText(Double.toString(work.getBonusDrive()));


            if (work.getWorkName() != "")
                WorkNameTxt.setText(work.getWorkName());


            spinnerType.setSelection(work.getTypeOfWork() - 1);
            spinnerDrive.setSelection(work.getTypeOfDrive());


            if (work.getStartNightHour().length() != 0) {
                bFromN.setText(work.getStartNightHour());
            }
            if (work.getEndNightHour().length() != 0) {
                {
                    bTillN.setText(work.getEndNightHour());
                }
                if (work.getStartSabbaticalHour().length() != 0) {
                    bFromS.setText(work.getStartSabbaticalHour());
                }
                if (work.getEndSabbaticalHour().length() != 0) {
                    bTillS.setText(work.getEndSabbaticalHour());
                }

                if (work.getNightPerHour() != 0) {
                    Log.e("123", String.valueOf(work.getNightPerHour()));
                    bFromN.setEnabled(true);
                    bTillN.setEnabled(true);
                    rIsraelN.setEnabled(true);
                    r125N.setEnabled(true);
                    r150N.setEnabled(true);
                    rOtherN.setEnabled(true);
                    cbNight.setChecked(true);
                    switch ((int) work.getNightPerHour() / 1000) {
                        case 1:
                            r125N.setChecked(true);
                            break;
                        case 2:
                            r150N.setChecked(true);
                            break;
                        case 3:
                            rOtherN.setChecked(true);
                            perHourNTxt.setEnabled(true);
                            perHourNTxt.setFocusable(true);
                            break;
                        case 4:
                            rIsraelN.setChecked(true);
                            break;
                    }
                    Log.e("2",String.valueOf(work.getNightPerHour()));
                    Log.e("3",String.valueOf(MainActivity.Round(work.getNightPerHour() % 1000,2)));
                    if (work.getNightPerHour() % 1000 != 0)
                        perHourNTxt.setText(String.valueOf(MainActivity.Round(work.getNightPerHour() % 1000,2)));
                }
                if (work.getSabbaticalPerHour() != 0) {
                    bFromS.setEnabled(true);
                    bTillS.setEnabled(true);
                    rIsraelS.setEnabled(true);
                    r125S.setEnabled(true);
                    r150S.setEnabled(true);
                    rOtherS.setEnabled(true);
                    cbSabbatical.setChecked(true);
                    switch ((int) work.getSabbaticalPerHour() / 1000) {
                        case 1:
                            r125S.setChecked(true);
                            break;
                        case 2:
                            r150S.setChecked(true);
                            break;
                        case 3:
                            rOtherS.setChecked(true);
                            perHourSTxt.setEnabled(true);
                            perHourSTxt.setFocusable(true);
                            break;
                        case 4:
                            rIsraelS.setChecked(true);
                            break;
                    }
                    if (work.getSabbaticalPerHour() % 1000 != 0)
                        perHourSTxt.setText(String.valueOf(MainActivity.Round(work.getSabbaticalPerHour() % 1000,2)));
                }
            }


            rgN.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbIsraelN: {

                            perHourNTxt.setEnabled(false);
                            break;
                        }
                        case R.id.rb125N: {

                            perHourNTxt.setEnabled(false);
                            break;
                        }
                        case R.id.rb150N: {

                            perHourNTxt.setEnabled(false);
                            break;
                        }
                        case R.id.rbOtherN: {
                            perHourNTxt.setVisibility(View.VISIBLE);
                            perHourNTxt.setEnabled(true);
                            perHourNTxt.setFocusable(true);
                            break;
                        }

                        default:
                            break;
                    }
                }
            });
            rgS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbIsraelS: {

                            perHourSTxt.setEnabled(false);
                            break;
                        }
                        case R.id.rb125S: {

                            perHourSTxt.setEnabled(false);
                            break;
                        }
                        case R.id.rb150S: {

                            perHourSTxt.setEnabled(false);
                            break;
                        }
                        case R.id.rbOtherS: {
                            perHourSTxt.setVisibility(View.VISIBLE);
                            perHourSTxt.setEnabled(true);
                            perHourSTxt.setFocusable(true);
                            break;
                        }
                        default:
                            break;
                    }
                }
            });
            cbNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        bFromN.setEnabled(true);
                        bTillN.setEnabled(true);
                        rIsraelN.setEnabled(true);
                        r125N.setEnabled(true);
                        r150N.setEnabled(true);
                        rOtherN.setEnabled(true);
                    } else {
                        rIsraelN.setEnabled(false);
                        r125N.setEnabled(false);
                        r150N.setEnabled(false);
                        rOtherN.setEnabled(false);
                        bFromN.setEnabled(false);
                        bTillN.setEnabled(false);
                        perHourNTxt.setEnabled(false);
                    }
                }
            });

            cbSabbatical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rIsraelS.setEnabled(true);
                        bFromS.setEnabled(true);
                        bTillS.setEnabled(true);
                        r125S.setEnabled(true);
                        r150S.setEnabled(true);
                        rOtherS.setEnabled(true);
                    } else {
                        rIsraelS.setEnabled(false);
                        r125S.setEnabled(false);
                        r150S.setEnabled(false);
                        rOtherS.setEnabled(false);
                        bFromS.setEnabled(false);
                        bTillS.setEnabled(false);
                        perHourSTxt.setEnabled(false);
                    }
                }
            });


        }
    }

    public void onClickBtn(View v) {
        sendWork();
    }
    public void sendWork(){
        workName="";fromN="";tillN="";fromS="";tillS="";
        global=0;start=0;hourI=0;addPerHour=0;perHour=0;stepI=0;stepII=0;stepIII=0; hourII=0;hourIII=0;perKilometer=0;bonusDrive=0;perHourN=0;perHourS=0;

        WorkNameTxt=(EditText)findViewById(R.id.workNameET);
        GlobalTxt=(EditText)findViewById(R.id.salaryGlobalStartStepIET);
        HourITxt = (EditText)findViewById(R.id.hoursIET);
        HourIITxt = (EditText)findViewById(R.id.hoursIIET);
        HourIIITxt = (EditText)findViewById(R.id.hoursIIIET);
        StepIITxt = (EditText)findViewById(R.id.stepIIET);
        StepIIITxt = (EditText)findViewById(R.id.stepIIIET);
        AddPerHourTxt= (EditText)findViewById(R.id.addPerHourET);
        PerHourTxt = (EditText)findViewById(R.id.perHourET);
        PerKilometerTxt=(EditText)findViewById(R.id.perKilometerET);
        BonusDriveTxt=(EditText)findViewById(R.id.bonusDrivesET);

        workName+=WorkNameTxt.getText().toString();
        Log.e("2",perHourNTxt.getText().toString());


        if ((rIsraelN.isChecked()||r125N.isChecked()||r150N.isChecked()||rOtherN.isChecked())&&cbNight.isChecked()) {
            switch (rgN.getCheckedRadioButtonId()) {
                case R.id.rbIsraelN: {
                    perHourN += 4000;
                    break;
                }
                case R.id.rb125N: {
                    perHourN += 1000;
                    break;
                }
                case R.id.rb150N: {
                    perHourN += 2000;
                    break;
                }
                case R.id.rbOtherN: {
                    perHourN += 3000;
                    if(perHourNTxt.getText().length()!=0)
                         perHourN += Double.parseDouble(perHourNTxt.getText().toString());

                    break;
                }
                default:
                    break;

            }
            fromN=bFromN.getText().toString();
            tillN = bTillN.getText().toString();
        }
       if ((rIsraelS.isChecked()||r125S.isChecked()||r150S.isChecked()||rOtherS.isChecked())&&cbSabbatical.isChecked()) {

            switch (rgS.getCheckedRadioButtonId())
            {
                case R.id.rbIsraelS: {
                    perHourS += 4000;
                    break;
                }
                case R.id.rb125S: {
                    perHourS += 1000;
                    break;
                }
                case R.id.rb150S: {
                    perHourS += 2000;
                    break;
                }
                case R.id.rbOtherS: {
                    perHourS += 3000;
                    if(perHourSTxt.getText().length()!=0)
                        perHourS += Double.parseDouble(perHourSTxt.getText().toString());
                    break;
                }
                default:
                    break;
            }
           fromS=bFromS.getText().toString();
           tillS = bTillS.getText().toString();
        }





        if (!(GlobalTxt.getText().toString().isEmpty()))
            global += Double.parseDouble(GlobalTxt.getText().toString());
        if (!(HourITxt.getText().toString().isEmpty()))
            hourI += Double.parseDouble(HourITxt.getText().toString());
        if (!(HourIITxt.getText().toString().isEmpty()))
            hourII += Double.parseDouble(HourIITxt.getText().toString());
        if (!(HourIIITxt.getText().toString().isEmpty()))
            hourIII += Double.parseDouble(HourIIITxt.getText().toString());
        if (!(StepIITxt.getText().toString().isEmpty()))
            stepII += Double.parseDouble(StepIITxt.getText().toString());
        if (!(StepIIITxt.getText().toString().isEmpty()))
            stepIII += Double.parseDouble(StepIIITxt.getText().toString());
        if (!(AddPerHourTxt.getText().toString().isEmpty()))
            addPerHour += Double.parseDouble(AddPerHourTxt.getText().toString());
        if (!(PerHourTxt.getText().toString().isEmpty()))
            perHour += Double.parseDouble(PerHourTxt.getText().toString());
        if (!(PerKilometerTxt.getText().toString().isEmpty()))
            perKilometer += Double.parseDouble(PerKilometerTxt.getText().toString());
        if (!(BonusDriveTxt.getText().toString().isEmpty()))
            bonusDrive += Double.parseDouble(BonusDriveTxt.getText().toString());


        Work newWork = new Work(workName,typeWork,global,defaultF,hourI,addPerHour,perHour,stepI,stepII,hourII,stepIII,hourIII,perKilometer,bonusDrive,mSelectedColor,typeDrive,fromN,tillN,fromS,tillS,perHourN,perHourS);
        if(isValid(spinnerType.getSelectedItemPosition(),spinnerDrive.getSelectedItemPosition())) {
            if(defaultF==1)
                for(int i=0;i<db.getAllWorks().size();i++){

                  Work a=db.getAllWorks().get(i);
                    a.setStart(0);
                    db.updateWork(a,a.getId());
                }


            if(flagIsUpdate)
                db.updateWork(newWork,id);
            else
                db.addWork(newWork);
            Intent intent = new Intent(this,EventsActivity.class);
            startActivity(intent);
        }


    }
    public void setPlusI(){
        if(plusIflag==false)
         {
            plusI.setBackgroundResource(R.drawable.minus_remove_green);
            plusIflag = true;
            StepIIIC.setVisibility(View.VISIBLE);
            StepIITxt.setVisibility(View.VISIBLE);
            HourITxt.setVisibility(View.VISIBLE);
            HourIITxt.setVisibility(View.INVISIBLE);
            plusII.setVisibility(View.VISIBLE);
        }
        else if(plusIIflag==false) {
            plusI.setBackgroundResource(R.drawable.plus_add_green);
            plusIflag = false;
            StepIIIC.setVisibility(View.GONE);
            StepIITxt.setVisibility(View.GONE);
            HourITxt.setVisibility(View.INVISIBLE);
            HourIITxt.setVisibility(View.GONE);
            plusII.setVisibility(View.GONE);
            plusII.setBackgroundResource(R.drawable.plus_add_green);
        }
    }
    public void setPlusII(){
        if(plusIIflag==false) {
            plusII.setBackgroundResource(R.drawable.minus_remove_green);
            plusIIflag=true;
            StepIIITxt.setVisibility(View.VISIBLE);
            HourIITxt.setVisibility(View.VISIBLE);

        }
        else  {
            plusII.setBackgroundResource(R.drawable.plus_add_green);
            plusIIflag = false;
            StepIIITxt.setVisibility(View.GONE);
            HourIITxt.setVisibility(View.INVISIBLE);
        }
    }
    public boolean isValid(int positionWorkType,int positionDriveType){

        if(workName=="") {
            Toast.makeText(getApplicationContext(), R.string.SetPleaseWorkName, Toast.LENGTH_LONG).show();
            return false;
        }
        else {

        switch (positionWorkType){
            case 0:
                if(global==0){
                    Toast.makeText(getApplicationContext(),R.string.SetPleaseGlobal, Toast.LENGTH_LONG).show();
                    return false;}
                break;
            case 1:

                if(global==0)
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.SetPleaseStepedFirst), Toast.LENGTH_LONG).show();
                    return false;}
                else if(stepII+hourI==0&&stepIII+hourII>0){
                    Toast.makeText(getApplicationContext(),R.string.SetPleaseStepSecond, Toast.LENGTH_LONG).show();
                    return false;}
                else if((stepII>0&&hourI==0)||(stepII==0&&hourI>0)){
                    Toast.makeText(getApplicationContext(),R.string.SetPleaseStepSecondFull, Toast.LENGTH_LONG).show();
                    return false;}

                else if((stepIII>0&&hourII==0)||(stepIII==0&&hourII>0)){
                    Toast.makeText(getApplicationContext(),R.string.SetPleaseStepThirdFull, Toast.LENGTH_LONG).show();
                    return false;}
                else if((addPerHour>0&&hourIII==0)||(addPerHour==0&&hourIII>0)){
                    Toast.makeText(getApplicationContext(),R.string.SetPleaseOverTimeFull, Toast.LENGTH_LONG).show();
                    return false;}
                break;
            case 2:
               if(perHour==0) {
                    Toast.makeText(getApplicationContext(), R.string.SetPleasePerHour, Toast.LENGTH_LONG).show();
                    return false;}
               else if(stepII+hourI==0&&stepIII+hourII>0){
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseStepSecond, Toast.LENGTH_LONG).show();
                   return false;}
               else if((stepII>0&&hourI==0)||(stepII==0&&hourI>0)){
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseStepSecondFull, Toast.LENGTH_LONG).show();
                   return false;}
               else if((stepIII>0&&hourII==0)||(stepIII==0&&hourII>0)){
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseStepThirdFull, Toast.LENGTH_LONG).show();
                   return false;}
                else if (cbNight.isChecked()&&(bFromN.getText().toString()==getString(R.string.SetFrom)||bTillN.getText().toString()==getString(R.string.SetTo)||
                       (!r125N.isChecked()&&!r150N.isChecked()&&!rOtherN.isChecked()&&!rIsraelN.isChecked()))){
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseNightFull, Toast.LENGTH_LONG).show();
                return false;}
               else if (cbNight.isChecked()&&rOtherN.isChecked()&&perHourNTxt.getText().length() == 0) {
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseNightPerHour, Toast.LENGTH_LONG).show();
                   return false;}
               else  if (cbSabbatical.isChecked()&&(bFromS.getText().toString()==getString(R.string.SetFrom)||bTillS.getText().toString()==getString(R.string.SetTo)||
                       (!r125S.isChecked()&&!r150S.isChecked()&&!rOtherS.isChecked()&&!rIsraelS.isChecked()))){
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseSabbathFull, Toast.LENGTH_LONG).show();
                   return false;}
               else if (cbSabbatical.isChecked()&&rOtherS.isChecked()&&perHourSTxt.getText().length() == 0) {
                   Toast.makeText(getApplicationContext(),R.string.SetPleaseSabbaticalPerHour, Toast.LENGTH_LONG).show();
                   return false;}

                break;

        }
        }
        if (positionDriveType==1&&perKilometer==0) {
            Toast.makeText(getApplicationContext(), R.string.SetPleasePerKilometer, Toast.LENGTH_LONG).show();
            return false;
        }

        return  true;
    }
    public void showTimeFromPickerDialogN(View v) {
        DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                fromN=(MainActivity.makeHourFine(hour, minute));
                bFromN.setText(fromN);
            }
        };
        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }
    public void showTimeFromPickerDialogS(View v) {
        DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                fromS=(MainActivity.makeHourFine(hour, minute));
                bFromS.setText(fromS);
            }

        };
        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }
    public void showTimeTillPickerDialogN(View v) {
        DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                tillN=(MainActivity.makeHourFine(hour, minute));
                bTillN.setText(tillN);
            }

        };
        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }
    public void showTimeTillPickerDialogS(View v) {
        DialogFragment newFragment = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                tillS=(MainActivity.makeHourFine(hour, minute));
                bTillS.setText(tillS);
            }

        };
        newFragment.show(getSupportFragmentManager(), "timeStartPicker");
    }

    public void deleteOrShowMethodWork() {
        final Intent intent = new Intent(this, EventsActivity.class);
        if(flagIsUpdate) {
            final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(R.string.MainDelete)
                    .setMessage(R.string.SetDoDelete1)
                    .setPositiveButton(R.string.MainDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {




                            for (int i = 0; i < db.getAllEvents().size(); i++) {
                                if (db.getAllEvents().get(i).getTypeEvent() == id){
                                    db.deleteEvent(db.getAllEvents().get(i).getId());}
                            }
                            db.deleteWork(id);
                            startActivity(intent);


                        }
                    })
                    .setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    })
;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.MainDelete)
                    .setMessage(R.string.SetDoDelete2)
                    .setPositiveButton(R.string.MainDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            AlertDialog alertDialogb = builder2.create();
                            alertDialogb.show();


                        }})
                    .setNegativeButton(R.string.MainCancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialoga = builder.create();
            alertDialoga.show();


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 9999);

        if( Locale.getDefault().getLanguage().equals("iw")){
            getMenuInflater().inflate(R.menu.menu_set_works_rtl, menu);}
        else
            getMenuInflater().inflate(R.menu.menu_set_works, menu);

        if(id!=9999)
            getMenuInflater().inflate(R.menu.menu_set_works_update, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_addWork:
                sendWork();
                return true;

            case R.id.action_delete:
                deleteOrShowMethodWork();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


