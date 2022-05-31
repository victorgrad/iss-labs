package utils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BorrowId implements Serializable {
    private String username;
    private Integer id;

    public BorrowId(){}

    public BorrowId(String value0, Integer value1) {
        this.username = value0;
        this.id = value1;
    }

    public String getUsername(){
        return username;
    }

    public Integer getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setId(Integer id){
        this.id = id;
    }


    @Override
    public int hashCode() {
        return username.hashCode() * id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Trip){
            return username.equals(((Trip) obj).getUsername())  && id.equals(((Trip)obj).getIdd());
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
