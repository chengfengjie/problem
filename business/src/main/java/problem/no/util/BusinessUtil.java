package problem.no.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessUtil {

    public static String createPasswordSalt(Date date) {
        return DigestUtils.sha1Hex(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date));
    }
    public static String encodePassword(String pwd, String salt) {
        return DigestUtils.sha1Hex(DigestUtils.sha1Hex(pwd) + salt);
    }

}
