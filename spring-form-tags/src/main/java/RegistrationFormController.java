import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class RegistrationFormController extends SimpleFormController {

    @Override
    protected ModelAndView onSubmit(Object command) throws Exception {

        Registration reg = (Registration) command;

        String uname = reg.getUsername();
        String fname = reg.getFname();
        String lname = reg.getLname();

        String gender = reg.getGender();
        String country = reg.getCountry();
        String cb = reg.getCb();
        String addr = reg.getAddr();

        ModelAndView mv = new ModelAndView(getSuccessView());

        mv.addObject("uname", uname);
        mv.addObject("fname", fname);
        mv.addObject("lname", lname);
        mv.addObject("gender", gender);
        mv.addObject("country", country);
        mv.addObject("cb", cb);
        mv.addObject("addr", addr);

        return mv;
    }

}
