package go.nvhieucs.cama.authentication;

public class JWTUtils {
    static public final String SECRET = "hieudeptrai";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
    public static final String SIGN_IN_URL = "/sign-in";
}
