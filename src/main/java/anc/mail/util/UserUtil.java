package anc.mail.util;

import anc.mail.entity.User;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author anchore
 * @date 2018/7/15
 */
public class UserUtil {
    public static User fromAddress(Address address) {
        if (address instanceof InternetAddress) {
            InternetAddress inetAddress = ((InternetAddress) address);
            return User.of(inetAddress.getPersonal(), inetAddress.getAddress());
        }
        return null;
    }

    public static Address toAddress(User user) throws UnsupportedEncodingException {
        return new InternetAddress(user.getAddress(), user.getName());
    }

    public static User[] fromAddresses(Address[] addresses) {
        if (addresses == null) {
            return new User[0];
        }
        return Arrays.stream(addresses).map(UserUtil::fromAddress).toArray(User[]::new);
    }

    public static Address[] toAddresses(User[] users) {
        if (users == null) {
            return new Address[0];
        }
        return Arrays.stream(users).map(user -> {
            try {
                return UserUtil.toAddress(user);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }).toArray(Address[]::new);
    }
}