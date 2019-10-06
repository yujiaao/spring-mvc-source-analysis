import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class registrationValidator implements Validator {

    public boolean supports(Class cl) {
        return Registration.class.isAssignableFrom(cl);

    }

    public void validate(Object ob, Errors errors) {
        Registration reg = (Registration) ob;
        if (reg.getUsername() == null || reg.getUsername().length() == 0) {
            errors.rejectValue("username", "error.empty.username");
        } else if (reg.getPassword() == null || reg.getPassword().length() == 0) {
            errors.rejectValue("password", "error.empty.password");
        }

    }

}