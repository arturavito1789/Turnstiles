
package turnstiles;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TotalsTurnstiles")
public class TotalsTurnstiles implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idTotalsTurnstiles; 
    @Column(name="tekDay")
    private Date tekDay;
    @Column(name="firstDayMonth")
    private Date firstDayMonth;
    @Column(name="workingHours")
    private int workingHours;

    public TotalsTurnstiles() {
    }

    public int getIdTotalsTurnstiles() {
        return idTotalsTurnstiles;
    }

    public void setIdTotalsTurnstiles(int idTotalsTurnstiles) {
        this.idTotalsTurnstiles = idTotalsTurnstiles;
    }

    public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(Date tekDay) {
        this.tekDay = tekDay;
    }

    public Date getFirstDayMonth() {
        return firstDayMonth;
    }

    public void setFirstDayMonth(Date firstDayMonth) {
        this.firstDayMonth = firstDayMonth;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
    
    
}
