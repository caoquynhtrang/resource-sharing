/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import trangcq.registration.RegistrationDAO;
import trangcq.registration.RegistrationDTO;
import trangcq.util.GoogleUtil;

/**
 *
 * @author USER
 */
public class GoogleLoginAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(GoogleLoginAction.class);

    private final String SUCCESS = "success";
    private String code;

    public GoogleLoginAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            String accessToken = GoogleUtil.getToken(code);
            RegistrationDTO userDTO = GoogleUtil.getUserInfor(accessToken);

            RegistrationDAO dao = new RegistrationDAO();
            RegistrationDTO userLogin = dao.checkLoginWithGoogle(userDTO.getEmail());
            if (userLogin == null) {
                int idInsert = dao.insertAccountWithGoogle(userDTO.getEmail(), userDTO.getFullname(), 3, 1, userDTO.getAvatar());
                if (idInsert != -1) {
                    userDTO.setId(idInsert);
                    session.put("USER", userDTO);
                }
            } else {
                session.put("USER", userLogin);
            }
            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
