package Login.system.Controller;

import Login.system.Entities.SignupActivationDTO;
import Login.system.Entities.SignupDTO;
import Login.system.Entities.User;
import Login.system.Service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupDTO signupDTO) throws Exception{
        signupService.signup(signupDTO);
    }

    @PostMapping("/signup/activation")
    public void activation(@RequestBody SignupActivationDTO signupActivationDTO) throws Exception{
        signupService.activate(signupActivationDTO);
    }
}
