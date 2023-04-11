package shape.before;

import net.plsar.Dao;
import net.plsar.PersistenceConfig;
import net.plsar.drivers.Drivers;
import net.plsar.implement.RouteEndpointBefore;
import net.plsar.model.*;
import net.plsar.security.SecurityManager;
import shape.model.User;
import shape.repo.UserRepo;

public class SessionBefore implements RouteEndpointBefore {

    @Override
    public BeforeResult before(FlashMessage flashMessage, ViewCache viewCache, NetworkRequest req, NetworkResponse resp, SecurityManager securityManager, BeforeAttributes beforeAttributes) {
        PersistenceConfig persistenceConfig = new PersistenceConfig();
        persistenceConfig.setDriver(Drivers.H2);
        persistenceConfig.setUrl("jdbc:h2:~/devDb");
        persistenceConfig.setUser("sa");
        persistenceConfig.setPassword("");

        Dao dao = new Dao(persistenceConfig);
        String activeUser = securityManager.getUser(req);
        UserRepo userRepo = new UserRepo(dao);
        User user = userRepo.get(activeUser);
        Long id = user.getId();
        viewCache.set("sessionUserId", id);
        viewCache.set("sessionUser", activeUser);

        return new BeforeResult();
    }
}
