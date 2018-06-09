package my.ungersinc.user.shiftmanager;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    //events database
    private static final String TABLE_SHIFTS = "shifts";
    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TYPEOFEVENT = "typeOfEvent";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_TRAVELERS = "travelers";
    private static final String KEY_PARKING= "parking";
    private static final String KEY_TIPFORSAL = "tipForSal";
    private static final String KEY_WAITERSTIP = "waitersTip";
    private static final String KEY_PRIVATETIP = "privateTip";
    private static final String KEY_HOURSTART = "hourStart";
    private static final String KEY_MINUTESTART = "minuteStart";
    private static final String KEY_HOUREND = "hourEnd";
    private static final String KEY_MINUTEEND = "minuteEnd";
    private static final String KEY_HOURS = "hours";
    private static final String KEY_SUM = "sum";
    private static final String KEY_SUMCASH = "sumCash";
    private static final String KEY_LOCATION = "locationEvent";
    private static final String KEY_MANAGER="Manager";//out
    private static final String KEY_ISSMSSENT="IsSmsSent";//out
    private static final String KEY_COMMENTS="comments";
    private static final String KEY_MANUALSALARY="manualSalary";



    private static final String[] COLUMNSEVENT = {KEY_ID,KEY_DATE,KEY_DISTANCE,KEY_TRAVELERS,
            KEY_PARKING,KEY_TIPFORSAL,KEY_PRIVATETIP,KEY_WAITERSTIP,KEY_HOURSTART,
            KEY_MINUTESTART,KEY_HOUREND,KEY_MINUTEEND,KEY_SUM,KEY_SUMCASH,
            KEY_HOURS,KEY_TYPEOFEVENT,KEY_LOCATION,KEY_COMMENTS,KEY_MANUALSALARY};

    private static final String TABLE_WORKS = "works";
    // Books Table Columns names
    //private static final String KEY_ID = "id";
    private static final String KEY_WORKNAME = "workName";
    private static final String KEY_TYPEWORK = "typeOfWork";
    private static final String KEY_TYPEDRIVE = "typeDrive";

    private static final String KEY_GLOBAL = "global";

    private static final String KEY_START = "start";
    private static final String KEY_HOURI = "hourI";
    private static final String KEY_ADDPERHOUR = "addPerHour";

    private static final String KEY_PERHOUR = "perHour";


    private static final String KEY_STEPI = "stepI";
    private static final String KEY_STEPII = "stepII";
    private static final String KEY_HOURII = "hourII";
    private static final String KEY_STEPIII = "stepIII";
    private static final String KEY_HOURIII = "hourIII";

    private static final String KEY_PERKILOMETER = "perKilometer";
    private static final String KEY_BONUSDRIVE = "bonusDrive";
    private static final String KEY_COLOR = "color";

    private static final String KEY_STARTNIGHTHOUR = "startNightHour";
    private static final String KEY_ENDNIGHTHOUR = "endNightHour";
    private static final String KEY_NIGHTPERHOUR = "nightPerHour";


    private static final String KEY_STARTSABBATICALHOUR = "startSabbaticalHour";
    private static final String KEY_ENDSABBATICALHOUR = "endSabbaticalHour";
    private static final String KEY_SABBATICALPERHOUR = "sabbaticalPerHour";


    private static final String[] COLUMNSWORKS = {KEY_ID,KEY_WORKNAME,KEY_TYPEWORK,KEY_GLOBAL,
            KEY_START,KEY_HOURI,KEY_ADDPERHOUR,KEY_PERHOUR,KEY_STEPI,
            KEY_STEPII,KEY_HOURII,KEY_STEPIII,KEY_HOURIII,KEY_PERKILOMETER,
            KEY_BONUSDRIVE,KEY_COLOR,KEY_TYPEDRIVE,KEY_STARTNIGHTHOUR,KEY_ENDNIGHTHOUR,KEY_NIGHTPERHOUR,KEY_STARTSABBATICALHOUR,KEY_ENDSABBATICALHOUR,KEY_SABBATICALPERHOUR};


    // Database Version
    private static final int DATABASE_VERSION =22 ;
    // Database Name
    private static final String DATABASE_NAME = "EventDB";
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

@Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
    String CREATE_Works_TABLE = "CREATE TABLE works ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "workName TEXT, "+ "typeOfWork INTEGER, "+"global REAL, "+"start REAL, "+
            "hourI REAL, "+"addPerHour REAL, "+"PerHour REAL, "+"stepI REAL, "+
            "stepII REAL, "+"hourII REAL, "+"stepIII REAL, "+
            "hourIII REAL, "+"perKilometer REAL,"+"bonusDrive REAL,"+"color TEXT DEFAULT '#ffffff',"+"typeDrive INTEGER DEFAULT 1," +
            "startNightHour TEXT,"+"endNightHour TEXT,"+"startSabbaticalHour TEXT,"+"endSabbaticalHour TEXT,"+
            "nightPerHour REAL,"+"sabbaticalPerHour REAL)";

    String CREATE_Shifts_TABLE = "CREATE TABLE shifts ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "date TEXT, "+ "distance REAL, "+"travelers REAL, "+"parking REAL, "+
            "tipForSal REAL, "+"privateTip REAL, "+"waitersTip REAL, "+"hourStart INTEGER, "+
            "minuteStart INTEGER, "+"hourEnd INTEGER, "+"minuteEnd INTEGER, "+
            "sum REAL, "+"sumCash REAL,"+"hours REAL,"+"typeOfEvent INTEGER,"+"locationEvent TEXT,"+"comments TEXT,"+"manualSalary REAL)";
        // create books table
        db.execSQL(CREATE_Shifts_TABLE);
        db.execSQL(CREATE_Works_TABLE);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String CREATE_Works_TABLE = "CREATE TABLE IF NOT EXISTS works ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "workName TEXT, "+ "typeOfWork INTEGER, "+"global REAL, "+"start REAL, "+
                "hourI REAL, "+"addPerHour REAL, "+"PerHour REAL, "+"stepI REAL, "+
                "stepII REAL, "+"hourII REAL, "+"stepIII REAL, "+
                "hourIII REAL, "+"perKilometer REAL,"+"bonusDrive REAL,"+"color TEXT DEFAULT '#ffffff',"+"typeDrive INTEGER DEFAULT 1," +
                "startNightHour TEXT DEFAULT '',"+"endNightHour TEXT  DEFAULT '',"+"startSabbaticalHour TEXT  DEFAULT '',"+"endSabbaticalHour TEXT  DEFAULT '',"+
                "nightPerHour REAL DEFAULT 0,"+"sabbaticalPerHour REAL DEFAULT 0)";

        String CREATE_Shifts_TABLE = "CREATE TABLE IF NOT EXISTS shifts ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, "+ "distance REAL, "+"travelers REAL, "+"parking REAL, "+
                "tipForSal REAL, "+"privateTip REAL, "+"waitersTip REAL, "+"hourStart INTEGER, "+
                "minuteStart INTEGER, "+"hourEnd INTEGER, "+"minuteEnd INTEGER, "+
                "sum REAL, "+"sumCash REAL,"+"hours REAL,"+"typeOfEvent INTEGER,"+"locationEvent TEXT,"+"comments TEXT,"+"manualSalary REAL)";

        String upgrade6a = "ALTER TABLE works ADD COLUMN color TEXT DEFAULT '#ffffff'";
        String upgrade7a = "ALTER TABLE works ADD COLUMN typeDrive INTEGER DEFAULT 1";
        String upgrade8a = "ALTER TABLE events ADD COLUMN comments TEXT ";
        String upgrade8b = "ALTER TABLE events ADD COLUMN manualSalary REAL DEFAULT 0";

        String upgrade18a = "ALTER TABLE works ADD COLUMN startNightHour TEXT DEFAULT ''";
        String upgrade18b = "ALTER TABLE works ADD COLUMN endNightHour TEXT DEFAULT ''";
        String upgrade18c = "ALTER TABLE works ADD COLUMN startSabbaticalHour TEXT DEFAULT '' ";
        String upgrade18d = "ALTER TABLE works ADD COLUMN endSabbaticalHour TEXT DEFAULT ''";
        String upgrade18e = "ALTER TABLE works ADD COLUMN nightPerHour REAL DEFAULT 0";
        String upgrade18f = "ALTER TABLE works ADD COLUMN sabbaticalPerHour REAL DEFAULT 0";

        String upgrade19a = "SELECT CAST(endSabbaticalHour AS Text) FROM "+TABLE_WORKS;

        String upgrade17a = "SELECT CAST(travelers AS REAL) FROM "+TABLE_SHIFTS;
        String upgrade17b = "SELECT CAST(parking AS REAL) FROM "+TABLE_SHIFTS;
        String upgrade17c = "SELECT CAST(tipForSal AS REAL) FROM "+TABLE_SHIFTS;
        String upgrade17d = "SELECT CAST(privateTip AS REAL) FROM "+TABLE_SHIFTS;
        String upgrade17e = "SELECT CAST(waitersTip AS REAL) FROM "+TABLE_SHIFTS;

        if(newVersion==22) {
            db.execSQL(CREATE_Works_TABLE);
            db.execSQL(CREATE_Shifts_TABLE);
            String query = "SELECT  * FROM " + TABLE_WORKS;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getColumnCount() < 18) {

                db.execSQL(upgrade18a);
                db.execSQL(upgrade18b);
                db.execSQL(upgrade18c);
                db.execSQL(upgrade18d);
                db.execSQL(upgrade18e);
                db.execSQL(upgrade18f);
                db.rawQuery(upgrade19a, null);
            }


            db.rawQuery(upgrade17a, null);
            db.rawQuery(upgrade17b, null);
            db.rawQuery(upgrade17c, null);
            db.rawQuery(upgrade17d, null);
            db.rawQuery(upgrade17e, null);

        }


    }



    public void addEvent(Event event){
        //for logging
        Log.d("msg2",event.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, event.getDate()); // get date

        values.put(KEY_DISTANCE, event.getDistance());
        values.put(KEY_TRAVELERS, event.getTravelers());
        values.put(KEY_PARKING, event.getParking());
        values.put(KEY_TIPFORSAL, event.getTipForSal());
        values.put(KEY_PRIVATETIP, event.getPrivateTip());
        values.put(KEY_WAITERSTIP, event.getWaitersTip());
        values.put(KEY_HOURSTART, event.getHourStart());
        values.put(KEY_MINUTESTART, event.getMinuteStart());
        values.put(KEY_HOUREND, event.getHourEnd());
        values.put(KEY_MINUTEEND, event.getMinuteEnd());

        values.put(KEY_SUM, event.getSum());
        values.put(KEY_SUMCASH, event.getSumCash());
        values.put(KEY_HOURS, event.getHours());
        values.put(KEY_TYPEOFEVENT, event.getTypeEvent()); // get date
        values.put(KEY_LOCATION, event.getLocation()); // get date
        //new
        //values.put(KEY_MANAGER, event.getManager());
       // values.put(KEY_ISSMSSENT, event.getIsSmsSent());

        values.put(KEY_COMMENTS, event.getComments());
        values.put(KEY_MANUALSALARY, event.getManualSalary());



        // 3. insert
        db.insert(TABLE_SHIFTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }
    public void addWork(Work work){
        //for logging
        //Log.d("msg2",work.getWorkName().toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_WORKNAME, work.getWorkName()); // get date

        values.put(KEY_TYPEWORK, work.getTypeOfWork());

        values.put(KEY_GLOBAL, work.getGlobal());
        values.put(KEY_START, work.getStart());
        values.put(KEY_HOURI, work.getHourI());
        values.put(KEY_ADDPERHOUR, work.getAddPerHour());

        values.put(KEY_PERHOUR, work.getPerHour());
        values.put(KEY_STEPI, work.getStepI());
        values.put(KEY_STEPII, work.getStepII());
        values.put(KEY_HOURII, work.getHourII());
        values.put(KEY_STEPIII, work.getStepIII());
        values.put(KEY_HOURIII, work.getHourIII());
        values.put(KEY_PERKILOMETER, work.getPerKilometer());
        values.put(KEY_BONUSDRIVE, work.getBonusDrive());
        values.put(KEY_COLOR, work.getColor());
        values.put(KEY_TYPEDRIVE, work.getTypeOfDrive());

        values.put(KEY_STARTNIGHTHOUR, work.getStartNightHour());
        values.put(KEY_ENDNIGHTHOUR, work.getEndNightHour());
        values.put(KEY_STARTSABBATICALHOUR, work.getStartSabbaticalHour());
        values.put(KEY_ENDSABBATICALHOUR, work.getEndSabbaticalHour());
        values.put(KEY_NIGHTPERHOUR, work.getNightPerHour());
        values.put(KEY_SABBATICALPERHOUR, work.getSabbaticalPerHour());

        // 3. insert
        db.insert(TABLE_WORKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
        Log.e("1", work.getWorkName());
    }

    public Event getEvent(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_SHIFTS, // a. table
                        COLUMNSEVENT, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Event event = new Event();
       // Event.setId(Integer.parseInt(cursor.getString(0)));
        event.setDate(cursor.getString(1));

        event.setDistance(Double.parseDouble(cursor.getString(2)));
        event.setTravelers(Double.parseDouble(cursor.getString(3)));
        event.setParking(Double.parseDouble(cursor.getString(4)));
        event.setTipForSal(Double.parseDouble(cursor.getString(5)));
        event.setPrivateTip(Double.parseDouble(cursor.getString(6)));
        event.setWaitersTip(Double.parseDouble(cursor.getString(7)));
        event.setHourStart(Integer.parseInt(cursor.getString(8)));
        event.setMinuteStart(Integer.parseInt(cursor.getString(9)));
        event.setHourEnd(Integer.parseInt(cursor.getString(10)));
        event.setMinuteEnd(Integer.parseInt(cursor.getString(11)));
        event.setSum(Double.parseDouble(cursor.getString(12)));
        event.setSumCash(Double.parseDouble(cursor.getString(13)));
        event.setHours(Double.parseDouble(cursor.getString(14)));
        event.setTypeEvent(Integer.parseInt(cursor.getString(15)));
        event.setLocation(cursor.getString(16));
        //new
        //event.setManager(cursor.getString(17));
        //event.setIsSmsSent(Integer.parseInt(cursor.getString(18)));

        if(cursor.getString(17) != null)
            event.setComments(cursor.getString(17));
        if (cursor.getString(18)!=null)
            event.setManualSalary(Double.parseDouble(cursor.getString(18)));



        //log
        String[] str = cursor.getColumnNames();
        Log.i("msg", str.toString());
        // 5. return book
        return event;
    }
    public Work getWork(int id) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_WORKS, // a. table
                        COLUMNSWORKS, // b. column names
                        " id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Work work = new Work();
        // Event.setId(Integer.parseInt(cursor.getString(0)));
        work.setWorkName(cursor.getString(1));
        work.setTypeOfWork(Integer.parseInt(cursor.getString(2)));
        work.setGlobal(Double.parseDouble(cursor.getString(3)));
        work.setStart(Double.parseDouble(cursor.getString(4)));
        work.setHourI(Double.parseDouble(cursor.getString(5)));
        work.setAddPerHour(Double.parseDouble(cursor.getString(6)));
        work.setPerHour(Double.parseDouble(cursor.getString(7)));
        work.setStepI(Double.parseDouble(cursor.getString(8)));
        work.setStepII(Double.parseDouble(cursor.getString(9)));
        work.setHourII(Double.parseDouble(cursor.getString(10)));
        work.setStepIII(Double.parseDouble(cursor.getString(11)));
        work.setHourIII(Double.parseDouble(cursor.getString(12)));
        work.setPerKilometer(Double.parseDouble(cursor.getString(13)));
        work.setBonusDrive(Double.parseDouble(cursor.getString(14)));
        work.setColor(cursor.getString(15));
        work.setTypeOfDrive(Integer.parseInt(cursor.getString(16)));

        work.setStartNightHour(cursor.getString(17));
        work.setEndNightHour(cursor.getString(18));

        work.setNightPerHour(Double.parseDouble(cursor.getString(19)));
        work.setStartSabbaticalHour(cursor.getString(20));
        work.setEndSabbaticalHour(cursor.getString(21));


        work.setSabbaticalPerHour(Double.parseDouble(cursor.getString(22)));


        //log
        String[] str = cursor.getColumnNames();
        Log.i("msg", str.toString());
        // 5. return book
        return work;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new LinkedList<Event>();


        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SHIFTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Event event = null;
        if (cursor.moveToFirst()) {
            do {
                event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setDate(cursor.getString(1));
                event.setDistance(Double.parseDouble(cursor.getString(2)));
                event.setTravelers(Double.parseDouble(cursor.getString(3)));
                event.setParking(Double.parseDouble(cursor.getString(4)));
                event.setTipForSal(Double.parseDouble(cursor.getString(5)));
                event.setPrivateTip(Double.parseDouble(cursor.getString(6)));
                event.setWaitersTip(Double.parseDouble(cursor.getString(7)));
                event.setHourStart(Integer.parseInt(cursor.getString(8)));
                event.setMinuteStart(Integer.parseInt(cursor.getString(9)));
                event.setHourEnd(Integer.parseInt(cursor.getString(10)));
                event.setMinuteEnd(Integer.parseInt(cursor.getString(11)));
                event.setSum(Double.parseDouble(cursor.getString(12)));
                event.setSumCash(Double.parseDouble(cursor.getString(13)));
                event.setHours(Double.parseDouble(cursor.getString(14)));
                if(cursor.getString(15)!=null)
                    event.setTypeEvent(Integer.parseInt(cursor.getString(15)));
                if(cursor.getString(16)!=null)
                    event.setLocation(cursor.getString(16));
                //new
               // if(cursor.getString(17)!=null)
                 //   event.setManager(cursor.getString(17));
               // if(cursor.getString(18) != null)
                //    event.setIsSmsSent(Integer.parseInt(cursor.getString(18)));

                if(cursor.getString(17) != null)
                    event.setComments(cursor.getString(17));
                if (cursor.getString(18)!=null)
                  event.setManualSalary(Double.parseDouble(cursor.getString(18)));


                // Add book to books
                events.add(event);
                Log.i("shit",String.valueOf(event.getId())+","+event.getDate());
            } while (cursor.moveToNext());

        }
        Log.i("crap",String.valueOf(events.size()));


        events=sortList(events);
        Log.i("crap",String.valueOf(events.size()));

        // return books
        return events;
    }
   public List<Work> getAllWorks() {
       List<Work> works = new LinkedList<Work>();


       // 1. build the query
       String query = "SELECT  * FROM " + TABLE_WORKS;

       // 2. get reference to writable DB
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(query, null);

       // 3. go over each row, build book and add it to list
       Work work = null;
       if (cursor.moveToFirst()) {
           do {
               work = new Work();
               work.setId(Integer.parseInt(cursor.getString(0)));
               work.setWorkName(cursor.getString(1));
               work.setTypeOfWork(Integer.parseInt(cursor.getString(2)));
               work.setGlobal(Double.parseDouble(cursor.getString(3)));
               work.setStart(Double.parseDouble(cursor.getString(4)));
               work.setHourI(Double.parseDouble(cursor.getString(5)));
               work.setAddPerHour(Double.parseDouble(cursor.getString(6)));
               work.setPerHour(Double.parseDouble(cursor.getString(7)));
               work.setStepI(Double.parseDouble(cursor.getString(8)));
               work.setStepII(Double.parseDouble(cursor.getString(9)));
               work.setHourII(Double.parseDouble(cursor.getString(10)));
               work.setStepIII(Double.parseDouble(cursor.getString(11)));
               work.setHourIII(Double.parseDouble(cursor.getString(12)));
               work.setPerKilometer(Double.parseDouble(cursor.getString(13)));
               work.setBonusDrive(Double.parseDouble(cursor.getString(14)));
               work.setColor(cursor.getString(15));
               work.setTypeOfDrive(Integer.parseInt(cursor.getString(16)));

               work.setStartNightHour(cursor.getString(17));
               work.setEndNightHour(cursor.getString(18));


               work.setStartSabbaticalHour(cursor.getString(19));
               work.setEndSabbaticalHour(cursor.getString(20));

               work.setNightPerHour(Double.parseDouble(cursor.getString(21)));
               work.setSabbaticalPerHour(Double.parseDouble(cursor.getString(22)));



               // Add book to books
               if(work.getStart()==1)
                   works.add(0,work);
               else
               works.add(work);
           } while (cursor.moveToNext());

       }
       Log.i("crap",String.valueOf(works.size()));

       // return books
       return works;
   }

    public int updateEvent(Event event,int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, event.getDate()); // get date


        values.put(KEY_DISTANCE, event.getDistance());
        values.put(KEY_TRAVELERS, event.getTravelers());
        values.put(KEY_PARKING, event.getParking());
        values.put(KEY_TIPFORSAL, event.getTipForSal());
        values.put(KEY_PRIVATETIP, event.getPrivateTip());
        values.put(KEY_WAITERSTIP, event.getWaitersTip());
        values.put(KEY_HOURSTART, event.getHourStart());
        values.put(KEY_MINUTESTART, event.getMinuteStart());
        values.put(KEY_HOUREND, event.getHourEnd());
        values.put(KEY_MINUTEEND, event.getMinuteEnd());

        values.put(KEY_SUM, event.getSum());
        values.put(KEY_SUMCASH, event.getSumCash());
        values.put(KEY_HOURS, event.getHours());
        values.put(KEY_TYPEOFEVENT, event.getTypeEvent());
        values.put(KEY_LOCATION, event.getLocation());

        //new
       // values.put(KEY_MANAGER, event.getManager());
       // values.put(KEY_ISSMSSENT, event.getIsSmsSent());

        values.put(KEY_COMMENTS, event.getComments());
        values.put(KEY_MANUALSALARY, event.getManualSalary());




        // 3. updating row
        int i = db.update(TABLE_SHIFTS, //table
                values, // column/value
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(id) }); //selections args

        // 4. close
        db.close();

        return i;

    }
    public int updateWork(Work work,int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_WORKNAME, work.getWorkName());
        values.put(KEY_TYPEWORK, work.getTypeOfWork());
        values.put(KEY_GLOBAL, work.getGlobal());

        values.put(KEY_START, work.getStart());
        values.put(KEY_HOURI, work.getHourI());
        values.put(KEY_ADDPERHOUR, work.getAddPerHour());

        values.put(KEY_PERHOUR, work.getPerHour());

        values.put(KEY_STEPI, work.getStepI());
        values.put(KEY_STEPII, work.getStepII());
        values.put(KEY_HOURII, work.getHourII());
        values.put(KEY_STEPIII, work.getStepIII());
        values.put(KEY_HOURIII, work.getHourIII());

        values.put(KEY_PERKILOMETER, work.getPerKilometer());
        values.put(KEY_BONUSDRIVE, work.getBonusDrive());
        values.put(KEY_COLOR, work.getColor());
        values.put(KEY_TYPEDRIVE, work.getTypeOfDrive());

        values.put(KEY_STARTNIGHTHOUR, work.getStartNightHour());
        values.put(KEY_ENDNIGHTHOUR, work.getEndNightHour());
        values.put(KEY_STARTSABBATICALHOUR, work.getStartSabbaticalHour());
        values.put(KEY_ENDSABBATICALHOUR, work.getEndSabbaticalHour());
        values.put(KEY_NIGHTPERHOUR, work.getNightPerHour());
        values.put(KEY_SABBATICALPERHOUR, work.getSabbaticalPerHour());


        // 3. updating row
        int i = db.update(TABLE_WORKS, //table
                values, // column/value
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(id) }); //selections args

        // 4. close
        db.close();
        Log.d("er", work.workName.toString());

        return i;

    }

    public void deleteEvent(int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        //db.delete(TABLE_SHIFTS, //table name
            //    KEY_ID + " = ?",  // selections
              //  new String[]{String.valueOf(id)}); //selections args


        String args = String.valueOf(id);
        String deleteQuery = "DELETE FROM shifts where id='" + args + "'";
        db.execSQL(deleteQuery);
        db.close();


    }
    public void deleteWork(int id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
       /* db.delete(TABLE_WORKS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(id) }); //selections args*/
        String args = String.valueOf(id);
        String deleteQuery = "DELETE FROM works where id='" + args + "'";
        db.execSQL(deleteQuery);


        // 3. close
        db.close();


    }
    public static int dateToInt (String oldDate) {
        String[] date= oldDate.split("/");
        int day=Integer.parseInt(date[0]);
        int month=Integer.parseInt(date[1]);
        int year=Integer.parseInt(date[2]);

        day+=year*365+month*30;
        return day;
    }
    public static int dateToInt (Event event) {
         String[] date= event.getDate().split("/");
         int day=Integer.parseInt(date[0]);
         int month=Integer.parseInt(date[1]);
         int year=Integer.parseInt(date[2]);

        day+=year*365+month*30;
        return day;
    }
    public List<Event> sortList(List<Event> events){
        int indexBig=0;
        List<Event> sorted= new LinkedList<Event>();
        Event gadol;
        boolean ifISmall=false;

        for (int i=0,j;i<events.size();) {
            gadol=events.get(i);
            indexBig=i;


            for ( j=i+1;j<events.size();j++) {
                if(dateToInt(events.get(j))>dateToInt(gadol)){
                    ifISmall=true;
                    gadol=events.get(j);
                    indexBig=j;
            }

            }
            if(ifISmall){
                Log.i("boom",String.valueOf(events.get(indexBig).getId()));
                events.remove(indexBig);}
            else
                i++;
            sorted.add(gadol);
            Log.i("kaka",String.valueOf(gadol.getId()));
        }




        return sorted ;
    }
}