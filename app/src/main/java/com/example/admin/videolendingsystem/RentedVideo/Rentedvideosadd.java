package com.example.admin.videolendingsystem.RentedVideo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ADMIN on 01/06/2017.
 */

public class Rentedvideosadd {

    private String fullName;
    private String videoName;
    private String dateRented;
    private String expectedReturnDate;
    private String returnedDate;
    private String rentedvideokey;
    private String status;
    private String penalty;

    public Rentedvideosadd(){

    }

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public String getVideoName() {return videoName;}
    public void setVideoName(String videoName) {this.videoName = videoName;}

    public String getDateRented() {return dateRented;}
    public void setDateRented(String dateRented) {this.dateRented = dateRented;}

    public String getExpectedReturnDate() {return expectedReturnDate;}
    public void setExpectedReturnDate(String expectedReturnDate) {this.expectedReturnDate = expectedReturnDate;}

    public String getReturnedDate() {return returnedDate;}
    public void setReturnedDate(String returnedDate) {this.returnedDate = returnedDate;}

    public String getRentedvideokey() {return rentedvideokey;}
    public void setRentedvideokey(String rentedvideokey) {this.rentedvideokey = rentedvideokey;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getPenalty() {return penalty;}
    public void setPenalty() {
        Long days;
        int fine = 2;
        int daynum;
        String finalPenalty;
        try {
            SimpleDateFormat format;
            format = new SimpleDateFormat("dd-mm-yyyy");
            days = TimeUnit.DAYS.convert(format.parse(returnedDate).getTime() - format.parse(expectedReturnDate).getTime(), TimeUnit.MILLISECONDS);
            daynum = Integer.valueOf(days.toString());

            int calcpenalty= fine*daynum;
            finalPenalty = Integer.toString(calcpenalty);

            if(calcpenalty<=0){
               this.penalty= "0";
            }else {
                this.penalty = finalPenalty;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // this.penalty = penalty;}

    }


  // public void setPenalty(String penalty) {this.penalty = penalty;}
}
