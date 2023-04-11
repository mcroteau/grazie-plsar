package shape.web;

import net.plsar.RouteAttributes;
import net.plsar.annotations.Before;
import net.plsar.annotations.Bind;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Design;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.NetworkRequest;
import net.plsar.model.NetworkResponse;
import net.plsar.model.ViewCache;
import net.plsar.security.SecurityManager;
import shape.Grazie;
import shape.before.SessionBefore;
import shape.model.*;
import shape.repo.BusinessRepo;
import shape.repo.TownRepo;
import shape.repo.UserRepo;
import shape.service.SeaService;
import shape.service.SmsService;
import java.util.List;

@Controller
public class IdentityRouter {

	public IdentityRouter(){
		this.smsService = new SmsService();
		this.seaService = new SeaService();
		this.grazie = new Grazie();
	}

	SmsService smsService;
	SeaService seaService;
	Grazie grazie;

	@Bind
	UserRepo userRepo;

	@Bind
	TownRepo townRepo;

	@Bind
	BusinessRepo businessRepo;

	@Post("/authenticate")
	public String authenticate(NetworkRequest req,
							   NetworkResponse resp,
							   SecurityManager security,
							   ViewCache cache){

		try{

			String email = grazie.getSpaces(req.getValue("email"));
			String password = req.getValue("password");
			if(!security.signin(email, password, req, resp)){
				cache.set("message", "Wrong phone and password");
				return "redirect:/signin";
			}

			User authUser = userRepo.getEmail(email);

//			req.getSession(true).set("name", authUser.getEmail());
//			req.getSession(true).set("userId", authUser.getId());

		} catch ( Exception e ) {
			e.printStackTrace();
			cache.set("message", "Please yell at one of us, something is a little off.");
			return "redirect:/";
		}

		return "redirect:/";
	}

	@Before({SessionBefore.class})
	@Design("/designs/guest.jsp")
	@Get("/signin")
	public String signin(NetworkRequest req,
						 NetworkResponse resp,
						 SecurityManager security){
		if(security.isAuthenticated(req))security.signout(req, resp);
		return "/pages/signin.jsp";
	}

	@Before({SessionBefore.class})
	@Design("/designs/guest.jsp")
	@Get("/signup")
	public String signup(NetworkRequest req, ViewCache cache){
		try{
			RouteAttributes routeAttributes = req.getRouteAttributes();
			String key = (String) routeAttributes.get("sms.key");
			smsService.send("9079878652", "data?", key);
		}catch(Exception ex){}

		List<Town> towns = townRepo.getList();
		if(req.getValue("z") != null &&
				!req.getValue("z").equals("Select a Town/City")){
			Long townId = Long.parseLong(req.getValue("z"));
			List<Business> businesses = businessRepo.getList(townId);
			cache.set("townId", townId);
			cache.set("businesses", businesses);
		}
		cache.set("towns", towns);
		return "/pages/signup.jsp";
	}

	@Post("/register")
	public String register(NetworkRequest req,
						   NetworkResponse resp,
						   SecurityManager security,
						   ViewCache cache){
		security.signout(req, resp);
		SignMeUp signMeUp = req.get(SignMeUp.class);
		String email = grazie.getSpaces(signMeUp.getEmail());
		User existingUser = userRepo.getEmail(email);
		if(existingUser != null){
			cache.set("message", "You might be already registered. We found an account associated with the email provided.");
			return "redirect:/signup";
		}

		String password = signMeUp.getPassword();

		if(!grazie.isValidMailbox(signMeUp.getEmail())){
			cache.set("message", "Please enter a valid email!");
			return "redirect:/signup";
		}

		User user = new User();
		user.setUuid(grazie.getString(8).toUpperCase());
		user.setGuid(grazie.getString(8).toUpperCase());
		user.setEmail(email);
		user.setPassword(security.hash(password));

		if(signMeUp.getTownId() == null){
			cache.set("message", "Please select a town and a business to continue..");
			return "redirect:/signup";
		}

		//todo:
		if(signMeUp.getBusinessId() == null){
			cache.set("message", "Please select a business to continue..");
			return "redirect:/signup?z=" + signMeUp.getTownId();
		}

		userRepo.save(user);

		User savedUser = userRepo.getSaved();
		userRepo.saveUserRole(savedUser.getId(), grazie.getUserRole());

		String permission = grazie.getUserMaintenance() + savedUser.getId();
		userRepo.savePermission(savedUser.getId(), permission);

		UserBusiness userBusiness = new UserBusiness();
		userBusiness.setUserId(savedUser.getId());
		userBusiness.setBusinessId(signMeUp.getBusinessId());
		userRepo.saveBusiness(userBusiness);

		UserBusiness savedUserBusiness = userRepo.getSavedBusiness();
		String businessPermission = grazie.getBusinessMaintenance() + savedUserBusiness.getId();
		userRepo.savePermission(savedUserBusiness.getId(), businessPermission);

		Business business = businessRepo.get(signMeUp.getBusinessId());
		try{
			RouteAttributes routeAttributes = req.getRouteAttributes();
			String key = (String) routeAttributes.get("sms.key");
			smsService.send("9079878652", "conversion, " + user.getEmail() + " @ " + business.getName(), key);
		}catch(Exception ex){}

		if(!security.signin(user.getEmail(), password, req, resp)){
			cache.set("message", "Successfully registered, you now need to activate your account");
			return "redirect:/signin";
		}

		cache.set("message", "Congratulations! Successfully registered! Now you can upload an image and set your name!");
		return "redirect:/users/edit/" + savedUser.getId();
	}

	@Get("/signout")
	public String signout(NetworkRequest req,
						  NetworkResponse resp,
						  ViewCache cache,
						  SecurityManager security){
		security.signout(req, resp);
		cache.set("message", "Successfully signed out");
		return "redirect:/";
	}

	@Before({SessionBefore.class})
	@Design("/designs/guest.jsp")
	@Get("/unauthorized")
	public String unauthorized(ViewCache cache){
		return "/pages/401.jsp";
	}

}