package shape.web;

import com.google.gson.Gson;
import net.plsar.RouteAttributes;
import net.plsar.annotations.Bind;
import net.plsar.annotations.Controller;
import net.plsar.annotations.Design;
import net.plsar.annotations.network.Get;
import net.plsar.annotations.network.Post;
import net.plsar.model.NetworkRequest;
import net.plsar.model.ViewCache;
import net.plsar.security.SecurityManager;
import shape.Grazie;
import shape.model.Business;
import shape.model.Place;
import shape.model.Result;
import shape.model.Town;
import shape.repo.BusinessRepo;
import shape.repo.TownRepo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

@Controller
public class IngestRouter {

    public IngestRouter(){
        this.gson = new Gson();
        this.grazie = new Grazie();
    }

    Gson gson;
    Grazie grazie;

    @Bind
    TownRepo townRepo;

    @Bind
    BusinessRepo businessRepo;


    @Design("/layouts/guest.jsp")
    @Get("ingest")
    public String index() { return "/pages/ingest/index.jsp"; }

    @Post("/ingest/perform")
    public String execute(NetworkRequest req, SecurityManager security, ViewCache cache) throws IOException {
        if(!security.isAuthenticated(req)){
            cache.set("message", "Please signin to continue...");
            return "redirect:/signin";
        }
        if(!security.hasRole(grazie.getSuperRole(), req)){
            cache.set("message", "Please signin to continue...");
            return "redirect:/home";
        }

        RouteAttributes routeAttributes = req.getRouteAttributes();
        String key = routeAttributes.get("maps.key");

        String placenames = req.getValue("place");
        String townname = req.getValue("location");

        if((placenames == null ||
                placenames.equals("")) &&
                        (townname == null ||
                            townname.equals(""))){
            cache.set("message", "nope, not going to happen...");
            return "redirect:/ingest";
        }

        int count = 0;
        String[] bits = placenames.split(",");
        for(String placename : bits) {
            if(placename.equals(""))continue;

            String q = placename + " in " + townname;
            String query = URLEncoder.encode(q, "utf-8");

            URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&radius=50000&key=" + key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream in = connection.getInputStream();
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }

            Result results = gson.fromJson(sb.toString(), Result.class);
            Town storedTown = townRepo.get(townname);

            if (storedTown == null) {
                Town town = new Town();
                town.setName(townname);
                townRepo.save(town);
                storedTown = townRepo.get(townname);
            }

            for (int z = 0; z < results.getResults().size(); z++) {
                Place place = results.getResults().get(z);
                String latitude = place.getGeometry().getLocation().getLat();
                String longitude = place.getGeometry().getLocation().getLng();
                if (z == 0) {
                    storedTown.setLatitude(latitude);
                    storedTown.setLongitude(longitude);
                    townRepo.update(storedTown);
                }

                String concatenated = place.getName() + " " + place.getFormatted_address();
                Business storedBusiness = businessRepo.getLatLng(latitude, longitude);
                if (storedBusiness == null) {
                    Business business = new Business();
                    business.setName(place.getName());
                    business.setLatitude(latitude);
                    business.setLongitude(longitude);
                    business.setTownId(storedTown.getId());
                    business.setAddress(concatenated);
                    businessRepo.save(business);
                }
            }

            count+= results.getResults().size();
        }

        cache.set("message", "Successfully ingested " + count + " places!");
        return "redirect:/ingest";
    }
}
