package mubex.renewal_foodsns.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class PasswordUtil {

    private static String getToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(var a : bytes) {
            sb.append(String.format("%02x", a));
        }

        return sb.toString();
    }

    public static String encryptPassword(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update((password).getBytes());
            byte[] saltedPassword = md.digest();

            return getToHex(saltedPassword);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("비밀 번호를 암호화 할 수 없습니다.");
        }
    }

    /**
     *
     * @param encryptedPassword  암호화된 패스워드
     * @param password  비교할 패스워드
     * @return true, false
     */
    public static boolean match(String encryptedPassword, String password) {
        String pwd = PasswordUtil.encryptPassword(password);

        return Objects.equals(encryptedPassword, pwd);
    }
}
