package coursedesign.lancelot.sharingaccountbook.Utils;
import coursedesign.lancelot.sharingaccountbook.domain.User;
import java.util.Date;
/**
 * Created by LENOVO on 2018/5/1.
 */

public class UserCodeGenerator {
    public static String generateCode(User user){
        String userCodePartA = user.getUserName();
        String userCodePartB = new Date().getDate()+ "";
        String userCodePartC = user.getPhoneNumber();
        String userCodeString = userCodePartA + userCodePartB + userCodePartC;

        return MD5Code.encode(userCodeString);
    }
}
