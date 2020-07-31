package coursedesign.lancelot.sharingaccountbook.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LENOVO on 2018/6/2.
 */

public class ReUtil {
    Pattern pattern;
    Matcher matcher;
    String defaultNamePattern = "([a-zA-z0-9])*";
    String defaultUserPwdPattern = "([a-zA-z0-9\\.])*";

    public void useDefaultNamePattern(){
        pattern= Pattern.compile(defaultNamePattern);
    }
    public void useDefaultPwdPattern(){
        pattern = Pattern.compile(defaultUserPwdPattern);
    }
    public void useUserPattern(String regexPattern){
        pattern = Pattern.compile(regexPattern);
    }
    public boolean matched(String input){
        if (pattern == null){
            pattern = Pattern.compile(defaultNamePattern);
        }
        matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
