package com.myrest.entities;

import com.myrest.exceptions.InvalidDateFormatException;
import com.myrest.exceptions.ReservationNotPossibleException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.expression.ExpressionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@ToString
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
    static String formatTemplate = "dd.MM.yyyy";

    public Reservation(Integer id, String beginDate, String endDate, Integer customerId, Integer scooterId) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.scooterId = scooterId;
    }

    public Reservation(){}

    public static Date getStringToDate(String date){
        Date dateRet;
        try{
            dateRet = new SimpleDateFormat(formatTemplate).parse(date);
        }catch (Exception e){
            throw new ExpressionException("Wrong date format!!! Try \"dd.MM.yyyy\"");
        }
        return dateRet;
    }

    public static boolean isReservationStartAndEndValid(String startString, String endString){
        Date start = getStringToDate(startString);
        Date end = getStringToDate(endString);
        Date today = new Date();
        boolean isValid = start.getTime() < end.getTime() && start.getTime() > today.getTime();
        if(!isValid) throw new ReservationNotPossibleException("Reservation date is not possible!!!");
        return true;
    }

    public static Boolean checkIfResevationExistsInList(List<Reservation> reservations, String beginDateString, String endDateString, Integer skipId){
        Date beginDate;
        Date endDate;
        try{
            beginDate = new SimpleDateFormat(formatTemplate).parse(beginDateString);
            endDate = new SimpleDateFormat(formatTemplate).parse(endDateString);
        }catch (Exception e){
            throw new InvalidDateFormatException("Wrong date format!!! Try 'dd.MM.yyyy'");
        }

        long reqBeginDateTime = beginDate.getTime();
        long reqEndDateTime = endDate.getTime();

        for(Reservation reservation: reservations){
            String bd = reservation.getBeginDate();
            long beginDateTime = Reservation.getStringToDate(bd).getTime();

            String ed = reservation.getEndDate();
            long endDateTime = Reservation.getStringToDate(ed).getTime();

            if(((reqBeginDateTime<=beginDateTime && reqEndDateTime>=endDateTime)
                    || (reqBeginDateTime>=beginDateTime && reqBeginDateTime<=endDateTime)
                    || (reqEndDateTime>=beginDateTime && reqEndDateTime<=endDateTime))
                    && !reservation.getId().equals(skipId)) {
                throw new ReservationNotPossibleException("Rezerwacja pokrywa się z inną, już istniejącą!!! id = " + reservation.getId());
            }
        }

        return false;
    }
}

