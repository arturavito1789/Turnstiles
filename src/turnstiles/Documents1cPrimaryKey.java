
package turnstiles;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Documents1cPrimaryKey implements Serializable {
    @Column(name = "idEmployees", nullable = false)
    private int idEmployees;
 
    @Column(name = "idSubdivision", nullable = false)
    private  int idSubdivision;   
    
    @Column(name = "documentType", nullable = false)
    private String documentType;
 
    @Column(name = "number", nullable = false)
    private String number;
    
    @Column(name = "date_Time", nullable = false)
    private Date date_Time;

    @Column(name = "tekDay", nullable = false)
    private Date tekDay;

   
    public Documents1cPrimaryKey() {
    }

     public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(Date tekDay) {
        this.tekDay = tekDay;
    }
    
    public int getIdEmployees() {
        return idEmployees;
    }

    public void setIdEmployees(int idEmployees) {
        this.idEmployees = idEmployees;
    }

    public int getIdSubdivision() {
        return idSubdivision;
    }

    public void setIdSubdivision(int idSubdivision) {
        this.idSubdivision = idSubdivision;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate_Time() {
        return date_Time;
    }

    public void setDate_Time(Date date_Time) {
        this.date_Time = date_Time;
    }
 
  @Override
    public int hashCode() {
	     return Objects.hash(getIdEmployees(), getIdSubdivision(), getDocumentType(), getNumber(), getDate_Time());
    }

       @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	} 
	if(!(o instanceof Documents1cPrimaryKey)) {
	    return false;
	}
	    
       Documents1cPrimaryKey bk_info = (Documents1cPrimaryKey) o;
	
       return Objects.equals(getIdEmployees(), bk_info.getIdEmployees()) && Objects.equals(getIdSubdivision(), bk_info.getIdSubdivision()) && Objects.equals(getDocumentType(), bk_info.getDocumentType()) && Objects.equals(getNumber(), bk_info.getNumber()) && Objects.equals(getDate_Time(), bk_info.getDate_Time());
    }



}
