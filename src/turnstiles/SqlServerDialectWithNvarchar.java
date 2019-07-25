
package turnstiles;

import java.sql.Types;
import org.hibernate.type.StandardBasicTypes;

public class SqlServerDialectWithNvarchar extends org.hibernate.dialect.SQLServer2012Dialect {
    
      public SqlServerDialectWithNvarchar() {
          super(); 
          registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
    }
}
