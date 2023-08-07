package com.idstar.karyawan.service.impl;

import com.idstar.karyawan.configuration.Config;
import com.idstar.karyawan.dao.request.LoginModel;
import com.idstar.karyawan.dao.request.RegisterModel;
import com.idstar.karyawan.model.oauth.Role;
import com.idstar.karyawan.model.oauth.User;
import com.idstar.karyawan.repository.oauth.RoleRepository;
import com.idstar.karyawan.repository.oauth.UserRepository;
import com.idstar.karyawan.service.oauth.Oauth2UserDetailsService;
import com.idstar.karyawan.service.UserService;
import com.idstar.karyawan.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    Config config = new Config();
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    RoleRepository repoRole;

    @Autowired
    UserRepository repoUser;

    @Autowired
    private Oauth2UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public Response response;

    @Autowired
    public Response responses;


    @Override
    public Map registerManual(RegisterModel objModel) {
        Map map = new HashMap();
        try {

            if (!response.isValidEmail(objModel.getEmail())) {
                return response.Error("Email is invalid");
            }

            if (objModel.getEmail() == "") {
                return response.Error("Fill the email");
            }

            if (objModel.getPhone_number() == "") {
                return response.Error("Fill the phone number");
            }

            if (objModel.getPassword() == "") {
                return response.Error("Fill the password");
            }

            if (objModel.getName() == "") {
                return response.Error("Fill the name");
            }

            if (response.chekNull(objModel.getName())) {
                return response.Error("Name is required");
            }

            if (response.chekNull(objModel.getEmail())) {
                return response.Error("Email is required");
            }

            if (response.chekNull(objModel.getPassword())) {
                return response.Error("Password is required");
            }

            if (objModel.getPassword().length() < 6) {
                return response.Error("Password Minimum password 6 character");
            }

            if (objModel.getName().length() > 100) {
                return response.Error("Name must be less than 100!");
            }

            if (response.chekNull(objModel.getInterest())) {
                return response.Error("Interest is required");
            }

            if (!response.nameNotSimbol(objModel.getName())) {
                return response.Error("Name do not use number or symbol");
            }

            User checkEmail = repoUser.checkExistingEmail(objModel.getEmail());
            if (null != checkEmail) {
                return response.Error("Email is Registered, try another email or click Forget Password");
            }

            String str = objModel.getInterest().replaceAll(",", " | ");

            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user = new User();
            user.setUsername(objModel.getEmail());
            user.setFullname(objModel.getName());
            user.setRegion(objModel.getDomicile());
            user.setStatus("User");
            user.setPhone_number(objModel.getPhone_number());
            user.setGender(objModel.getGender());
            user.setPassion(str);
            user.setEnabled(false);
            user.setTempatLahir(objModel.getTempatLahir());
            user.setTanggalLahir(objModel.getTanggalLahir());
            user.setNamaIbuKandung(objModel.getNamaIbuKandung());

            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = repoRole.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            User obj = repoUser.save(user);

            String url = baseUrl + "user-login/login?email=" + objModel.getEmail() +
                    "&password=" + objModel.getPassword();
            ResponseEntity<Map> responses = restTemplateBuilder.build().postForEntity(url, objModel, Map.class);
            return response.Sukses(responses.getBody());

        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            return response.Error("eror:" + e);
        }
    }

    @Value("${BASEURL}")
    private String baseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Map login(LoginModel loginModel) {
        /**
         * bussines logic for login here
         * **/
        try {

            if (loginModel.getEmail() == "") {
                return response.Error("Fill the email");
            }

            if (loginModel.getPassword() == "") {
                return response.Error("Fill the password");
            }

            if (response.chekNull(loginModel.getEmail())) {
                return response.Error("Email is required");
            }

            if (response.chekNull(loginModel.getPassword())) {
                return response.Error("Password is required");
            }

            Map<String, Object> map = new HashMap<>();

            User checkUser = repoUser.findOneByUsername(loginModel.getEmail());
            if (checkUser == null) {
                return response.Error("Email Not Found");
            }
            if (checkUser.isBlocked()) {
                return response.Error("Your account is blocked, please contact Admin!");
            }

            if ((checkUser != null) && (encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return response.Error(map);
                }
            }
            if (checkUser == null) {
                return response.notFound("User not found");
            }
            if (!(encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                return response.Error("Wrong Password");
            }
            String url = baseUrl + "/oauth/token?username=" + loginModel.getEmail() +
                    "&password=" + loginModel.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });
            System.out.println("reponse=" + response.getBody());
            if (response.getStatusCode() == HttpStatus.OK) {
                User user = repoUser.findOneByUsername(loginModel.getEmail());
                List<String> roles = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                }

                repoUser.save(user);
                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));
                map.put("user", user);
                return responses.Sukses(map);
            } else {
                return responses.Error(map);
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return response.Error("invalid login");
            }
            return response.Error(e);
        } catch (Exception e) {
            e.printStackTrace();

            return response.Error(e);
        }
    }

    @Override
    public Map getDetailProfile(Principal principal) {
        User idUser = getUserIdToken(principal, userDetailsService);
        try {

            User obj = repoUser.save(idUser);
            return response.Sukses(obj);
        } catch (Exception e) {
            return response.Error(e);
        }
    }

    private User getUserIdToken(Principal principal, Oauth2UserDetailsService userDetailsService) {
        UserDetails user = null;
        String username = principal.getName();
        if (!StringUtils.isEmpty(username)) {
            user = userDetailsService.loadUserByUsername(username);
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        User idUser = repoUser.findOneByUsername(user.getUsername());
        if (null == idUser) {
            throw new UsernameNotFoundException("User name not found");
        }
        return idUser;
    }
}