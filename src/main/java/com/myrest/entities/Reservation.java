package com.myrest.entities;

import jakarta.persistence.*;
import org.springframework.expression.ExpressionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservation_id_sequence",
            sequenceName = "reservation_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_id_sequence"
    )

    private Integer id;

    String beginDate;
    String endDate;
    Integer customerId;
    Integer scooterId;

    public Reservation(Integer id, String beginDate, String endDate, Integer customerId, Integer scooterId) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.scooterId = scooterId;
    }

    public Reservation(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String begin_date) {
        this.beginDate = begin_date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String end_date) {
        this.endDate = end_date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getScooterId() {
        return scooterId;
    }

    public void setScooterId(Integer scooterId) {
        this.scooterId = scooterId;
    }

    public static Date getStringToDate(String date, String formatTemplate){
        Date dateRet;
        try{
            dateRet = new SimpleDateFormat(formatTemplate).parse(date);
        }catch (Exception e){
            throw new ExpressionException("Wrong date format!!! Try \"dd.MM.yyyy\"");
        }
        return dateRet;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(beginDate, that.beginDate) && Objects.equals(endDate, that.endDate) && Objects.equals(customerId, that.customerId) && Objects.equals(scooterId, that.scooterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginDate, endDate, customerId, scooterId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", begin_date='" + beginDate + '\'' +
                ", end_date='" + endDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", scooterId='" + scooterId + '\'' +
                '}';
    }

    public static boolean isReservationStartAndEndValid(Date start, Date end){
        Date today = new Date();
        return start.getTime() < end.getTime() && start.getTime() > today.getTime();
    }

    public static Boolean checkIfResevationExistsInList(List<Reservation> reservations, Date beginDate, Date endDate, String format){

        Long reqBeginDateTime = beginDate.getTime();
        Long reqEndDateTime = endDate.getTime();

        for(Reservation reservation: reservations){
            String bd = reservation.getBeginDate();
            Long beginDateTime = Reservation.getStringToDate(bd, format).getTime();

            String ed = reservation.getEndDate();
            Long endDateTime = Reservation.getStringToDate(ed, format).getTime();

            if(reqBeginDateTime<=beginDateTime && reqEndDateTime>=endDateTime) return true;
        }

        return false;
    }
}
