package coursedesign.lancelot.sharingaccountbook.domain;
import java.util.Date;
/**
 * Created by LENOVO on 2018/5/1.
 */

public class ExpenditureRecord {
    private Integer recordId;
    private Integer accountbookId;
    private String type;
    private float value;
    private String reason;
    private String comment;
    //"yyyy-MM-dd HH:mm"
    private String addDate;
    private Integer operatorId;

    public ExpenditureRecord(){}

    public Integer getRecordId() {
        return recordId;
    }
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    public Integer getAccountbookId() {
        return accountbookId;
    }
    public void setAccountbookId(Integer accountbookId) {
        this.accountbookId = accountbookId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
