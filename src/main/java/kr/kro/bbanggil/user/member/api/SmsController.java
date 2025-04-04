package kr.kro.bbanggil.user.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.admin.service.AdminMainServiceImpl;
import kr.kro.bbanggil.common.scheduler.NewsletterScheduler;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import kr.kro.bbanggil.user.bakery.service.BakeryServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsServiceImpl smsService;

    

    // 인증번호 전송
    @PostMapping("/send")
    public ResponseEntity<?> SendSMS(@RequestBody SmsRequestDTO smsRequestDTO){
        String result = smsService.sendCertificationCode(smsRequestDTO.getPhoneNumber());
//        return ResponseEntity.ok("문자를 전송했습니다.");
        return ResponseEntity.ok(result);
    }
    
    // 인증번호 확인
    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody SmsRequestDTO smsRequestDTO) {
        boolean isVerified = smsService.verifyCode(smsRequestDTO.getPhoneNumber(), smsRequestDTO.getCode());
        if (isVerified) {
            return ResponseEntity.ok("인증 성공!");
        } else {
            return ResponseEntity.badRequest().body("인증 실패! 코드가 올바르지 않거나 만료되었습니다.");
        }
    }
}
