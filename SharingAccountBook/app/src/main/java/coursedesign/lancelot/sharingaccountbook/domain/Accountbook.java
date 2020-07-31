package coursedesign.lancelot.sharingaccountbook.domain;
import java.util.Date;
/**
 * Created by LENOVO on 2018/5/1.
 */

public class Accountbook {
    private Integer accountbookId;
    private String bookName;
    private Integer createUserId;
    private Integer managerUserId;

    public Accountbook(){}

    public Integer getAccountbookId() {
        return accountbookId;
    }

    public void setAccountbookId(Integer accountbookId) {
        this.accountbookId = accountbookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(Integer managerUserId) {
        this.managerUserId = managerUserId;
    }
}
