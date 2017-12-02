package mx.com.code4living.catalogoliverpool;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by marcrobito on 02/12/17.
 */

public class RecentQueries extends RealmObject {
    private Date date;
    private String consulta;

    public void setDate(Date date){
        this.date = date;
    }

    public void setConsulta(String consulta){
        this.consulta = consulta;
    }

    public Date getDate(){
        return date;
    }

    public String getConsulta(){
        return consulta;
    }
}
