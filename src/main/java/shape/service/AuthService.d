package shape.service;

import net.stargzr.model.NetworkRequest;
import net.stargzr.model.NetworkResponse;
import net.stargzr.security.SecurityManager;
import shape.Grazie;
import shape.model.User;
import shape.repo.UserRepo;
import perched.Parakeet;
import qio.annotate.Inject;
import qio.annotate.Service;
import qio.model.web.PageCache;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    @Bind
    UserRepo userRepo;

    public boolean signin(String phone, String password){
        User userCredential = userRepo.getPhone(phone);
        if(userCredential == null) {
            userCredential = userRepo.getEmail(phone);
        }
        return Parakeet.login(phone, password);
    }

    public boolean signout(){
        return Parakeet.logout();
    }

    public boolean isAuthenticated(){
        return Parakeet.isAuthenticated();
    }

    public boolean isAdministrator(){
        return Parakeet.hasRole(Underscore.SUPER_ROLE);
    }

    public boolean hasPermission(String permission){
        return Parakeet.hasPermission(permission);
    }

    public boolean hasRole(String role){
        return Parakeet.hasRole(role);
    }

    public User getUser(NetworkRequest req, SecurityManager security){
        String credential = security.getUser(req);
        User userCredential = userRepo.getPhone(credential);
        if(userCredential == null){
            userCredential = userRepo.getEmail(credential);
        }
        return userCredential;
    }

    public String deAuthenticate(PageCache data, HttpServletRequest req) {
        signout();
        data.put("message", "Successfully signed out");
        req.getSession().setAttribute("username", "");
        req.getSession().setAttribute("userId", "");
        return "redirect:/";
    }
}
