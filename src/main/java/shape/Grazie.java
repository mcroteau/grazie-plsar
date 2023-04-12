package shape;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.plsar.*;
import net.plsar.drivers.Drivers;
import net.plsar.environments.Environments;
import net.plsar.schemes.RenderingScheme;
import net.plsar.security.renderer.AuthenticatedRenderer;
import net.plsar.security.renderer.GuestRenderer;

import org.apache.commons.validator.routines.EmailValidator;

public class Grazie {

	public static void main(String[] args){
		PLSAR plsar = new PLSAR(6001);
		plsar.setNumberOfPartitions(4);
		plsar.setNumberOfRequestExecutors(10);

		plsar.setSecurityAccess(AuthAccess.class);

		PersistenceConfig persistenceConfig = new PersistenceConfig();
		persistenceConfig.setDriver(Drivers.H2);
		persistenceConfig.setUrl("jdbc:h2:~/devDb");
		persistenceConfig.setUser("sa");
		persistenceConfig.setPassword("");

		SchemaConfig schemaConfig = new SchemaConfig();
		schemaConfig.setSchema("schema.sql");
		schemaConfig.setEnvironment(Environments.DEVELOPMENT);
		persistenceConfig.setSchemaConfig(schemaConfig);
		plsar.setPersistenceConfig(persistenceConfig);

		PropertiesConfig propertiesConfig = new PropertiesConfig();
		propertiesConfig.setPropertiesFile("grazie.properties");
		plsar.setPropertiesConfig(propertiesConfig);

		plsar.addViewRenderer(AuthenticatedRenderer.class);
		plsar.addViewRenderer(GuestRenderer.class);

		ViewConfig viewConfig = new ViewConfig();
		viewConfig.setViewsPath("webapp");
		viewConfig.setResourcesPath("assets");
		viewConfig.setViewExtension(".jsp");
		viewConfig.setRenderingScheme(RenderingScheme.RELOAD_EACH_REQUEST);
		plsar.setViewConfig(viewConfig);

		plsar.start();
	}

	public String getUserMaintenance(){
		return "user:maintenance:";
	}
	public String getBusinessMaintenance(){
		return "businesses:maintenance:";
	}

	public String getDonorRole(){return "donor-role";}
	public String getSuperRole(){return "super-role";}
	public String getUserRole(){return "user-role";}

	public String getSuperEmail(){ return "croteau.mike@gmail.com"; }
	public String getSuperPassword(){ return "password"; }

//	public static final String SUPER_USERNAME = "croteau.mike@gmail.com";
//	public static final String SUPER_PASSWORD = "password";

	public String getDateFormat(){ return "yyyyMMddHHmm"; }
	public String getDatePretty(){ return "HH:mmaa dd MMM"; }

//	public static final String DATE_FORMAT  = "yyyyMMddHHmm";
//	public static final String DATE_PRETTY  = "HH:mmaa dd MMM";

	public String getOceanEndpoint(){ return "https://tips.sfo3.digitaloceanspaces.com/"; }

//	public static final String OCEAN_ENDPOINT = "https://tips.sfo3.digitaloceanspaces.com/";

    public String getPhone(String phone){
		if(phone != null)
			return phone
					.replaceAll("[^a-zA-Z0-9]", "")
					.replaceAll(" ", "")
					.replaceAll(" ", "")
					.replaceAll(" ", "");
		return "";
	}

	public String getSpaces(String email) {
		if(email != null)
			return email.replaceAll(" ", "")
					.replaceAll(" ", "")
					.replaceAll(" ", "");
		return "";
	}

	public String getExt(final String path) {
		String result = null;
		if (path != null) {
			result = "";
			if (path.lastIndexOf('.') != -1) {
				result = path.substring(path.lastIndexOf('.'));
				if (result.startsWith(".")) {
					result = result.substring(1);
				}
			}
		}
		return result;
	}
	public boolean isValidMailbox(String str){
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(str);
	}

	public int getNumber(int max){
		Random r = new Random();
		return r.nextInt(max);
	}

	public boolean containsSpecialCharacters(String str) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}

	private String getExtension(final String path) {
		String result = null;
		if (path != null) {
			result = "";
			if (path.lastIndexOf('.') != -1) {
				result = path.substring(path.lastIndexOf('.'));
				if (result.startsWith(".")) {
					result = result.substring(1);
				}
			}
		}
		return result;
	}

	public String getTip(int n) {
		String CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
		StringBuilder uuid = new StringBuilder();
		int divisor = n/6;
		Random rnd = new Random();
		for(int z = 0; z < n;  z++) {
			if( z % divisor == 0 && z > 0) {
				uuid.append("-");
			}
			int index = (int) (rnd.nextFloat() * CHARS.length());
			uuid.append(CHARS.charAt(index));
		}
		return uuid.toString();
	}

	public String getString(int n) {
		String CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
		StringBuilder uuid = new StringBuilder();
		int divisor = n/7;
		Random rnd = new Random();
		for(int z = 0; z < n;  z++) {
			if( z % divisor == 0 && z > 0) {
				uuid.append("-");
			}
			int index = (int) (rnd.nextFloat() * CHARS.length());
			uuid.append(CHARS.charAt(index));
		}
		int index = (int) (rnd.nextFloat() * CHARS.length());
		uuid.append(CHARS.charAt(index));

		return uuid.toString();
	}

	public long getDate(){
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(getDateFormat());
		String date = dtf.format(ldt);
		return Long.parseLong(date);
	}

	public long getDateTimezone(String timezone){
		LocalDateTime ldt = LocalDateTime.now();
		ZoneId zone = ZoneId.systemDefault();
		ZoneOffset zoneOffset = zone.getRules().getOffset(ldt);
		ZonedDateTime zdt = ldt.atOffset(zoneOffset)
							.atZoneSameInstant(ZoneId.of(timezone));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(getDateFormat());
		String date = dtf.format(zdt);
		return Long.parseLong(date);
	}

	public long getDateTimezoneMins(int mins, String timezone){
		LocalDateTime ldt = LocalDateTime.now().plusMinutes(mins);
		ZoneId zone = ZoneId.systemDefault();
		ZoneOffset zoneOffset = zone.getRules().getOffset(ldt);
		ZonedDateTime zdt = ldt.atOffset(zoneOffset)
				.atZoneSameInstant(ZoneId.of(timezone));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(getDateFormat());
		String date = dtf.format(zdt);
		return Long.parseLong(date);
	}

	public double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public String getPretty(Long date){
		String dateString = "";
		try {
			SimpleDateFormat parser = new SimpleDateFormat(getDateFormat());
			Date d = parser.parse(Long.toString(date));

			SimpleDateFormat sdf2 = new SimpleDateFormat(getDatePretty());
			dateString = sdf2.format(d);
		}catch(Exception ex){}
		return dateString;
	}

	public String pad(String value, int places, String character){
		if(value.length() < places){
			value = character.concat(value);
			pad(value, places, character);
		}
		return value;
	}

	public String getEncodedPrefix(String name) {
		String ext = getExt(name);
		if(ext.equals("jpeg"))return "data:image/jpeg;base64,";
		if(ext.equals("jpg"))return "data:image/jpeg;base64,";
		if(ext.equals("png"))return "data:image/png;base64,";
		if(ext.equals("gif"))return "data:image/gif;base64,";
		return "";
	}

	public String getEncoded(String photoPath) {
		try {
			File file = new File(photoPath);
			FileInputStream stream = new FileInputStream(file);

			int bufLength = 2048;
			byte[] buffer = new byte[2048];
			byte[] data;

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int readLength;
			while ((readLength = stream.read(buffer, 0, bufLength)) != -1) {
				out.write(buffer, 0, readLength);
			}

			data = out.toByteArray();

			out.close();
			stream.close();

			return Base64.getEncoder().withoutPadding().encodeToString(data);

		}catch(IOException ioex){
			ioex.printStackTrace();
		}
		return "";
	}

}