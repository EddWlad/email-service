package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.ResetMail;
import com.tidsec.mail_service.entities.User;
import com.tidsec.mail_service.repositories.IUserRepository;
import com.tidsec.mail_service.service.IResetMailService;
import com.tidsec.mail_service.service.IUserService;
import com.tidsec.mail_service.util.EmailUtil;
import com.tidsec.mail_service.util.MailPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailUtil emailUtil;
    private final IResetMailService resetMailService;
    private final IUserService userService;

    @PostMapping("/sendMail")
    public ResponseEntity<Integer> sendMail(@RequestBody String username) throws Exception {
       int result = 0;
       final int EXPIRATION_TIME = 10;

       User us = userService.findOneByUsername(username);
       if(us != null && us.getId()>0){

           // 🔹 Verifica si el usuario ya tiene un ResetMail y elimínalo
           ResetMail existingResetMail = resetMailService.findByRandom(us.getUsername());
           if (existingResetMail != null) {
               resetMailService.delete(existingResetMail);
           }

           ResetMail resetMail = new ResetMail();
           resetMail.setRandom(UUID.randomUUID().toString());
           resetMail.setUser(us);
           resetMail.setExpiration(EXPIRATION_TIME);
           resetMailService.save(resetMail);

           MailPassword mail = new MailPassword();
           mail.setFrom("www.edison1992@gmail.com");
           mail.setTo(us.getEmail());
           mail.setSubject("RESET PASSWORD - GESMAIL");

           Map<String, Object> model = new HashMap<>();
           String url = "http://localhost:4200/forgot/" + resetMail.getRandom();
           model.put("resetUrl", url);
           model.put("user", resetMail.getUser().getUsername());
           mail.setModel(model);

           emailUtil.sendMail(mail);
           result = 1;
       }

       return ResponseEntity.ok(result);
    }

    @GetMapping("/reset/check/{random}")
    public ResponseEntity<Integer> checkRandom(@PathVariable String random){
        int result = 0;

        ResetMail resetMail = resetMailService.findByRandom(random);
        if(resetMail != null && !resetMail.isExpired()){
            result = 1;
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reset/{random}")
    public ResponseEntity<Integer> resetPassword(@PathVariable("random") String random, @RequestBody String password){
        int rpta = 0;
        ResetMail rm = resetMailService.findByRandom(random);
        if(rm != null && rm.getId()>0){
            if(!rm.isExpired()){
                userService.changePassword(rm.getUser().getUsername(), password);
                resetMailService.delete(rm);
                rpta = 1;
            }
        }
        return ResponseEntity.ok(rpta);
    }
}
