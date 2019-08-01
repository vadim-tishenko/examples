import lombok.Data;

@Data
public class TfcSensor {
    long idTr;
    long gmteventtime;
    byte isLocked;
    long idSensor;
    double val;
    long gmtsystime;
    long idSensorTarget;
}
