package my.ungersinc.user.shiftmanager;

/**
 * Created by USER on 12/08/2015.
 */
public class Work {
    public String workName, color,startNightHour,endNightHour,startSabbaticalHour,endSabbaticalHour;
    public int id, typeOfWork,typeOfDrive;
    public double global, start,hourI,addPerHour,PerHour,stepI,stepII,hourII,stepIII,hourIII,perKilometer,bonusDrive,nightPerHour,sabbaticalPerHour;

    public Work() {
    }


    public Work(String workName, int typeOfWork, double global, double start, double hourI,
                double addPerHour, double perHour, double stepI, double stepII, double hourII,
                double stepIII, double hourIII, double perKilometer ,double bonusDrive, String color,int typeOfDrive,
                String startNightHour, String endNightHour, String startSabbaticalHour, String endSabbaticalHour, double nightPerHour, double sabbaticalPerHour) {
        this.workName = workName;
        this.typeOfWork = typeOfWork;
        this.global = global;
        this.start = start;
        this.hourI = hourI;
        this.addPerHour = addPerHour;
        PerHour = perHour;
        this.stepI = stepI;
        this.stepII = stepII;
        this.hourII = hourII;
        this.stepIII = stepIII;
        this.perKilometer = perKilometer;
        this.hourIII = hourIII;
        this.color = color;
        this.typeOfDrive = typeOfDrive;
        this.bonusDrive = bonusDrive;
        this.startNightHour = startNightHour;
        this.endNightHour = endNightHour;
        this.startSabbaticalHour = startSabbaticalHour;
        this.endSabbaticalHour = endSabbaticalHour;
        this.nightPerHour = nightPerHour;
        this.sabbaticalPerHour = sabbaticalPerHour;

    }

    public String getStartNightHour() {
        return startNightHour;
    }

    public double getNightPerHour() {
        return nightPerHour;
    }

    public void setNightPerHour(double nightPerHour) {
        this.nightPerHour = nightPerHour;
    }

    public double getSabbaticalPerHour() {
        return sabbaticalPerHour;
    }

    public void setSabbaticalPerHour(double sabbaticalPerHour) {
        this.sabbaticalPerHour = sabbaticalPerHour;
    }

    public void setStartNightHour(String startNightHour) {
        this.startNightHour = startNightHour;
    }

    public String getEndNightHour() {
        return endNightHour;
    }

    public void setEndNightHour(String endNightHour) {
        this.endNightHour = endNightHour;
    }

    public String getStartSabbaticalHour() {
        return startSabbaticalHour;
    }

    public void setStartSabbaticalHour(String startSabbaticalHour) {
        this.startSabbaticalHour = startSabbaticalHour;
    }

    public String getEndSabbaticalHour() {
        return endSabbaticalHour;
    }

    public void setEndSabbaticalHour(String endSabbaticalHour) {
        this.endSabbaticalHour = endSabbaticalHour;
    }

    public int getTypeOfDrive() {
        return typeOfDrive;
    }

    public void setTypeOfDrive(int typeOfDrive) {
        this.typeOfDrive = typeOfDrive;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(int typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public double getGlobal() {
        return global;
    }

    public void setGlobal(double global) {
        this.global = global;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getHourI() {
        return hourI;
    }

    public void setHourI(double hourI) {
        this.hourI = hourI;
    }

    public double getPerHour() {
        return PerHour;
    }

    public void setPerHour(double perHour) {
        PerHour = perHour;
    }

    public double getAddPerHour() {
        return addPerHour;
    }

    public void setAddPerHour(double addPerHour) {
        this.addPerHour = addPerHour;
    }

    public double getStepI() {
        return stepI;
    }

    public void setStepI(double stepI) {
        this.stepI = stepI;
    }

    public double getStepII() {
        return stepII;
    }

    public void setStepII(double stepII) {
        this.stepII = stepII;
    }

    public double getHourII() {
        return hourII;
    }

    public void setHourII(double hourII) {
        this.hourII = hourII;
    }

    public double getStepIII() {
        return stepIII;
    }

    public void setStepIII(double stepIII) {
        this.stepIII = stepIII;
    }

    public double getHourIII() {
        return hourIII;
    }

    public void setHourIII(double hourIII) {
        this.hourIII = hourIII;
    }

    public double getPerKilometer() {
        return perKilometer;
    }

    public void setPerKilometer(double perKilometer) {
        this.perKilometer = perKilometer;
    }

    public double getBonusDrive() {
        return bonusDrive;
    }

    public void setBonusDrive(double bonusDrive) {
        this.bonusDrive = bonusDrive;
    }
}
