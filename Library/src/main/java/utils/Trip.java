package utils;

import org.javatuples.Triplet;

import java.io.Serializable;
import java.time.LocalDate;

public class Trip implements Serializable {
    private String username;
    private Integer idd;
    private LocalDate date;

    public Trip(){}

    public Trip(String value0, Integer value1, LocalDate value2) {
        this.username = value0;
        this.idd = value1;
        this.date = value2;
    }

    public String getUsername(){
        return username;
    }

    public Integer getIdd(){
        return idd;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setIdd(Integer id){
        this.idd = id;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }


    @Override
    public int hashCode() {
        return username.hashCode() * idd * date.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Trip){
            return username.equals(((Trip) obj).getUsername())  && idd.equals(((Trip)obj).getIdd()) && date.equals(((Trip) obj).getDate());
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
