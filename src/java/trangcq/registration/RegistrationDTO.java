/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.registration;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.sql.Timestamp;
import trangcq.role.RoleDTO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class RegistrationDTO implements Serializable {

    
    private transient int id;
    private String email;
    @SerializedName(value = "name")
    private String fullname;
    private String password, phone, address;
    private Timestamp createDate;
    @SerializedName(value = "picture")
    private String avatar;
    private RoleDTO role;
    private StatusDTO status;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String fullname) {
        this.fullname = fullname;
    }

    public RegistrationDTO(int id, String email, String password, String fullname, RoleDTO role, StatusDTO status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.status = status;
    }

    public RegistrationDTO(String email, String password, String fullname, String phone, String address) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
    }

    public RegistrationDTO(int id, String email, String password, String fullname, String phone, String address, Timestamp createDate, RoleDTO role, StatusDTO status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.createDate = createDate;
        this.role = role;
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

}
