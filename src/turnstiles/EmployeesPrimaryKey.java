/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Embeddable
public class EmployeesPrimaryKey implements Serializable {

    // @Id
    // @GeneratedValue (strategy = GenerationType.IDENTITY) - c этой инструкцией
    // не работает. Не может генерировать значение поле idEmployees автоматом
    // даже если его заполнить то всеравно при вставки в sql ошибка происходит.
    
    @Column(name = "idEmployees", nullable = false)
    private int idEmployees;
 
    @Column(name = "idSubdivision", nullable = false)
    private  int idSubdivision;

    public EmployeesPrimaryKey() {
    }
    
    @Override
	public boolean equals(Object o) {
	    if (this == o) {
		return true;
	    } 
	    if(!(o instanceof EmployeesPrimaryKey)) {
		return false;
	    }
	    
            EmployeesPrimaryKey bk_info = (EmployeesPrimaryKey) o;
	
            return Objects.equals(getIdEmployees(), bk_info.getIdEmployees()) && Objects.equals(getIdSubdivision(), bk_info.getIdSubdivision());
	}

	@Override
	public int hashCode() {
	     return Objects.hash(getIdEmployees(), getIdSubdivision());
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
    
}
