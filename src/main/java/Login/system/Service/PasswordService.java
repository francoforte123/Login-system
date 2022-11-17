package Login.system.Service;

import Login.system.Entities.RequestPasswordDTO;
import Login.system.Entities.ResetPasswordDTO;
import Login.system.Entities.User;
import Login.system.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User request(RequestPasswordDTO requestPasswordDTO) throws Exception{
        User userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
        if(userFromDB == null) throw new Exception("Cannot find user");
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
        mailNotificationService.sendPasswordResetMail(userFromDB);
        return userRepository.save(userFromDB);
    }

    public User restore(ResetPasswordDTO resetPasswordDTO) throws Exception{
        User userFromDB = userRepository.findByPasswordResetCode(resetPasswordDTO.getResetPassword());
        if(userFromDB == null) throw new Exception("Cannot find user");
        userFromDB.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);
        userFromDB.setActive(true);
        userFromDB.setActivationCode(null);
        return userRepository.save(userFromDB);
    }
}
