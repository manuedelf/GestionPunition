package com.manue.gestionpunition.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wede7391 on 13/02/2015.
 */
public class Punition implements Serializable {
    public long id;
    public long childId;
    public String name;
    public Date begin;
    public Date end;
    public long calendarId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Punition punition = (Punition) o;

        if (id != punition.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Punition{" +
                "id=" + id +
                ", childId=" + childId +
                ", name='" + name + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", calendarId=" + calendarId +
                '}';
    }
}
