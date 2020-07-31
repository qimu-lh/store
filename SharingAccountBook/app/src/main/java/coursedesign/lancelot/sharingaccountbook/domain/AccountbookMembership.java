package coursedesign.lancelot.sharingaccountbook.domain;
import java.util.Date;
/**
 * Created by LENOVO on 2018/5/1.
 */

public class AccountbookMembership {
    private Integer membershipId;
    private Integer accountbookId;
    private Integer userId;
    private String ifManager;//No 或者 Yes

    public AccountbookMembership(){}

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public Integer getAccountbookId() {
        return accountbookId;
    }

    public void setAccountbookId(Integer accountbookId) {
        this.accountbookId = accountbookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIfManager() {
        return ifManager;
    }

    public void setIfManager(String ifManager) {
        this.ifManager = ifManager;
    }

}
