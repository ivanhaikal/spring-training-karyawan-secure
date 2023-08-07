package com.idstar.karyawan.controller;

import com.idstar.karyawan.configuration.Config;
import com.idstar.karyawan.dao.request.RegisterModel;
import com.idstar.karyawan.model.oauth.User;
import com.idstar.karyawan.repository.oauth.UserRepository;
import com.idstar.karyawan.service.UserService;
import com.idstar.karyawan.service.email.EmailSender;
import com.idstar.karyawan.util.EmailTemplate;
import com.idstar.karyawan.util.Response;
import com.idstar.karyawan.util.SimpleStringUtils;
import com.idstar.karyawan.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user-register/")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Autowired
    public Response response;

    @PostMapping("/register")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> saveRegisterManual(@Valid @RequestBody RegisterModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepository.checkExistingEmail(objModel.getEmail());
        if (null != user) {
            return new ResponseEntity<Map>(response.Error("Email is Registered, try another email or click Forget Password"), HttpStatus.OK);
        }
        map = serviceReq.registerManual(objModel);
        Map sendOTP = sendEmailegister(objModel);
        Map sendOTPUri = sendEmailegisterTymeleaf(objModel);

        return new ResponseEntity<Map>(sendOTPUri, HttpStatus.OK);
    }

    @Value("${expired.token.password.minute:}")
    private int expiredToken;

    @Autowired
    public TemplateResponse templateCRUD;

    @Autowired
    public EmailTemplate emailTemplate;

    @Autowired
    public EmailSender emailSender;

    // Step 2: sendp OTP berupa URL: guna updeta enable agar bisa login:
    @PostMapping("/send-otp")//send OTP
    public Map sendEmailegister(@RequestBody RegisterModel user) {
        String message = "Thanks, please check your email for activation.";

        if (user.getEmail() == null) return templateCRUD.templateEror("No email provided");
        User found = userRepository.findOneByUsername(user.getEmail());
        if (found == null) return templateCRUD.notFound("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getRegisterTemplate();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{USERNAME}}", found.getFullname());
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", found.getFullname());
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
        return templateCRUD.templateSukses(message);
    }

    @GetMapping("/register-confirm-otp/{token}")
    public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {
        User user = userRepository.findOneByOTP(tokenOtp);
        if (null == user) {
            return new ResponseEntity<Map>(templateCRUD.templateEror("OTP tidak ditemukan"), HttpStatus.OK);
        }
        if (user.isEnabled()) {
            return new ResponseEntity<Map>(templateCRUD.templateSukses("Akun Anda sudah aktif, Silahkan melakukan login"), HttpStatus.OK);
        }
        String today = config.convertDateToString(new Date());
        String dateToken =
                config.convertDateToString(user.getOtpExpiredDate());
        if (Long.parseLong(today) > Long.parseLong(dateToken)) {
            return new ResponseEntity<Map>(templateCRUD.templateEror("Your token is expired.Please Get token again."), HttpStatus.OK);
        }
        //update user
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity<Map>(templateCRUD.templateSukses("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
    }

    @Value("${BASEURL:}")
    private String BASEURL;

    @PostMapping("/send-otp-tymeleaf")//send OTP
    public Map sendEmailegisterTymeleaf(@RequestBody RegisterModel user) {
        String message = "Thanks, please check your email for activation.";
        if (user.getEmail() == null) return templateCRUD.templateEror("No email provided");
        User found = userRepository.findOneByUsername(user.getEmail());
        if (found == null) return templateCRUD.notFound("Email not found");

        String template = emailTemplate.getRegisterTemplate();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();
            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{USERNAME}}",
                    (found.getFullname() == null ? found.getUsername() :
                            found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",
                    BASEURL + "user-register/web/index/" + otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}",
                    (found.getFullname() == null ? found.getUsername() :
                            found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",
                    BASEURL + "user-register/web/index/" + found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
        return templateCRUD.templateSukses(found);
    }
}
