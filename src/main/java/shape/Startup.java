package shape;

import net.plsar.Dao;
import net.plsar.PersistenceConfig;
import net.plsar.annotations.ServerStartup;
import net.plsar.drivers.Drivers;
import net.plsar.implement.ServerListener;
import net.plsar.security.SecurityManager;
import shape.model.*;
import shape.repo.BusinessRepo;
import shape.repo.RoleRepo;
import shape.repo.TownRepo;
import shape.repo.UserRepo;

@ServerStartup
public class Startup implements ServerListener {

    public Startup(){
        this.grazie = new Grazie();
    }

    Grazie grazie;

    @Override
    public void startup() {

        PersistenceConfig persistenceConfig = new PersistenceConfig();
        persistenceConfig.setDriver(Drivers.H2);
        persistenceConfig.setUrl("jdbc:h2:~/devDb");
        persistenceConfig.setUser("sa");
        persistenceConfig.setPassword("");

        Dao dao = new Dao(persistenceConfig);
        UserRepo userRepo = new UserRepo(dao);
        RoleRepo roleRepo = new RoleRepo(dao);
        TownRepo townRepo = new TownRepo(dao);
        BusinessRepo businessRepo = new BusinessRepo(dao);

        Role superRole = roleRepo.get(grazie.getSuperRole());
        Role userRole = roleRepo.get(grazie.getUserRole());

        if(superRole == null){
            superRole = new Role();
            superRole.setName(grazie.getSuperRole());
            roleRepo.save(superRole);
        }

        if(userRole == null){
            userRole = new Role();
            userRole.setName(grazie.getUserRole());
            roleRepo.save(userRole);
        }

        User existing = userRepo.getPhone("9073477052");
        String password = SecurityManager.dirty(grazie.getSuperPassword());

        superRole = roleRepo.get(grazie.getSuperRole());

        if(existing == null){
            User superUser = new User();
            superUser.setGuid(grazie.getString(8).toUpperCase());
            superUser.setUuid(grazie.getString(8).toUpperCase());
            superUser.setName("Super UserCredential!");
            superUser.setPhone("9073477052");
            superUser.setEmail(grazie.getSuperRole());
            superUser.setPassword(password);
            userRepo.save(superUser);
            User savedUser = userRepo.getSaved();
            userRepo.saveUserRole(savedUser.getId(), superRole.getId());
            String permission = grazie.getUserMaintenance() + savedUser.getId();
            userRepo.savePermission(savedUser.getId(), permission);
        }

        genMocks(userRepo, townRepo, businessRepo);

    }

    public void genMocks(UserRepo userRepo, TownRepo townRepo, BusinessRepo businessRepo){

        String[] towns = new String[]{"Antilles", "Burbin Banks Delaware", "Habiskus"};
        for(String name: towns){
            Town town = new Town();
            town.setName(name);
            townRepo.save(town);

            Town savedTown = townRepo.getSaved();
            savedTown.setLatitude("35.2226");
            savedTown.setLongitude("97.4395");
            townRepo.update(savedTown);
        }

        Business one = new Business();
        one.setName("Software Projects Inc.");
        one.setAddress("Software Projects Inc. Fairbanks Alaska");
        one.setLatitude("45.452191");
        one.setLongitude("-123.9128525");
        one.setTownId(1L);
        businessRepo.save(one);

        Business dos = new Business();
        dos.setName("Petco");
        dos.setAddress("Petco 625 H. Street");
        dos.setLatitude("38.891032");
        dos.setLongitude("-77.168679");
        dos.setTownId(2L);
        businessRepo.save(dos);

        Business tres = new Business();
        tres.setName("Everything But Water");
        tres.setAddress("Everything But Water 4724 1/4 Admiralty Way, Marina Del Rey, CA 90292");
        tres.setLatitude("33.9544111");
        tres.setLongitude("-118.4867234");
        tres.setTownId(1L);
        businessRepo.save(tres);



        String[] names = new String[]{ "Mitch Rithmithgan",
                "Cheech Nordom",
                "Tito Chavez",
                "Lisa Churnem",
                "Manuel Smith",
                "Allen Aspinwall"};

        for(int z = 0; z < names.length; z++){
            String name = names[z];
            User user = new User();
            user.setGuid(grazie.getString(8).toUpperCase());
            user.setUuid(grazie.getString(8).toUpperCase());
            user.setEmail("croteau.mike+" + z + "@gmail.com");
            user.setPassword(SecurityManager.dirty("password"));
            user.setTownId(2L);
            userRepo.save(user);

            User savedUser = userRepo.getSaved();
            userRepo.saveUserRole(savedUser.getId(), grazie.getUserRole());
            String permission = grazie.getUserMaintenance() + savedUser.getId();
            userRepo.savePermission(savedUser.getId(), permission);

            savedUser.setImageUri("https://tips.sfo3.digitaloceanspaces.com/tnTPoUwD9.png");
            savedUser.setName(name);
            savedUser.setPhone("9079878652");
            savedUser.setStripeAccountId("acct_1KKrmu2XYbZOgi9P");
            userRepo.update(savedUser);

            UserBusiness userBusiness = new UserBusiness();
            userBusiness.setUserId(savedUser.getId());
            userBusiness.setBusinessId(2L);

            userRepo.saveBusiness(userBusiness);

            UserBusiness savedUserBusiness = userRepo.getSavedBusiness();

            String businessPermission = grazie.getBusinessMaintenance() + savedUserBusiness.getId();
            userRepo.savePermission(savedUser.getId(), businessPermission);

        }
    }
}