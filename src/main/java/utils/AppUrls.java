package utils;

public final class AppUrls {
    public static final String BASE_URL = ConfigReader.getProperty("base.ui.url", "http://localhost:8080/parabank");
    public static final String HOME_PAGE = ConfigReader.getProperty("home.page.url", BASE_URL + "/index.htm");
    public static final String ABOUT_US_PAGE = BASE_URL + "/about.htm";
    public static final String SERVICES_PAGE = BASE_URL + "/services.htm";
    public static final String PRODUCTS_PAGE = "https://www.parasoft.com/products/";
    public static final String LOCATIONS_PAGE = "https://www.parasoft.com/solutions/";
    public static final String ADMIN_PAGE = BASE_URL + "/admin.htm";
    public static final String FORGOT_LOGIN_INFO_PAGE = BASE_URL + "/lookup.htm";
    public static final String REGISTRATION_PAGE = BASE_URL + "/register.htm";

    private AppUrls() {
    }
}
