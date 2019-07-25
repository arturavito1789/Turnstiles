/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class HibernateQueryResultDataSource implements JRDataSource { 
   
    private String[] fields;   
    private Iterator iterator;   
    private Object currentValue;   

    public HibernateQueryResultDataSource(List list, String[] fields) {   
         this.fields = fields;   
         this.iterator = list.iterator();   
    }   

    public Object getFieldValue(JRField field) throws JRException {   
         Object value = null;   
         int index = getFieldIndex(field.getName());   
             if (index > -1) {   
                Object[] values = (Object[])currentValue;   
                value = values[index];   
             }   
         return value;   
    }   
  
    public boolean next() throws JRException {   
         currentValue = iterator.hasNext() ? iterator.next() : null;   
         return (currentValue != null);   
    }   
  
    private int getFieldIndex(String field) {   
        int index = -1;   
            for (int i = 0; i < fields.length; i++) {   
                if (fields[i].equals(field)) {   
                   index = i;   
                   break;   
                }   
            }   
        return index;   
    }   

}  
