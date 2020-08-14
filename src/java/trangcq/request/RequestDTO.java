/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.request;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import trangcq.registration.RegistrationDTO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class RequestDTO implements Serializable {

    private int id;
    private Date returnDate, receiveDate;
    private int userID, statusID;
    private Date importedDate;
    private StatusDTO statusDTO;
    private RegistrationDTO registrationDTO;

    public RequestDTO() {
    }

    public boolean isNotExpired() throws ParseException{
        Date currentDateWithTime = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(currentDateWithTime);
        Date currentDate = new Date(dateFormat.parse(strDate).getTime());
        boolean isNotExpired = currentDate.before(receiveDate);
        return isNotExpired;
    }
    
    public String getDateImported() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(importedDate);
        return strDate;
    }
    
    public String getDateReturn() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(returnDate);
        return strDate;
    }

    public String getDateReceive() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(receiveDate);
        return strDate;
    }

    public RequestDTO(Date returnDate, Date receiveDate, int userID, int statusID) {
        this.returnDate = returnDate;
        this.receiveDate = receiveDate;
        this.userID = userID;
        this.statusID = statusID;
    }

    public RequestDTO(Date returnDate, Date receiveDate, int statusID) {
        this.returnDate = returnDate;
        this.receiveDate = receiveDate;
        this.statusID = statusID;
    }

   

    
    
    
    public RequestDTO(int id, Date returnDate, Date receiveDate, int userID, int statusID, Date importedDate, StatusDTO statusDTO, RegistrationDTO registrationDTO) {
        this.id = id;
        this.returnDate = returnDate;
        this.receiveDate = receiveDate;
        this.userID = userID;
        this.statusID = statusID;
        this.importedDate = importedDate;
        this.statusDTO = statusDTO;
        this.registrationDTO = registrationDTO;
        

    }

    public StatusDTO getStatusDTO() {
        return statusDTO;
    }

    public void setStatusDTO(StatusDTO statusDTO) {
        this.statusDTO = statusDTO;
    }

    public RegistrationDTO getRegistrationDTO() {
        return registrationDTO;
    }

    public void setRegistrationDTO(RegistrationDTO registrationDTO) {
        this.registrationDTO = registrationDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public Date getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Date importedDate) {
        this.importedDate = importedDate;
    }

    
}
