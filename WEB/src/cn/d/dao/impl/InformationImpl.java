package cn.d.dao.impl;

import cn.d.domain.Information;
import cn.d.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class InformationImpl {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    public void update(String a,String b) {
        String sql = "update information set s_state=? where s_name=? and j_name='空调温度'";
        template.update(sql,b,a);
    }
    public void moshi(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='模式'";
        template.update(sql,a,b);
    }
    public void fengsu(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='风速'";
        template.update(sql,a,b);
    }
    public void wendu(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='温度'";
        template.update(sql,a,b);
    }
    public void shidu(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='湿度'";
        template.update(sql,a,b);
    }
    public void vent(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='门窗磁'";
        template.update(sql,a,b);
    }

    public void deng1(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='灯光1'";
        template.update(sql,a,b);
    }

    public void deng2(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='灯光2'";
        template.update(sql,a,b);
    }

    public void deng3(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='灯光3'";
        template.update(sql,a,b);
    }

    public void deng4(String a,String b){
        String sql = "update information set s_state=? where s_name=? and j_name='灯光4'";
        template.update(sql,a,b);
    }

    public List<Information> chuancan(String a){
        String sql = "select * from information where s_name=?";
        List<Information> information = template.query(sql,new BeanPropertyRowMapper<Information>(Information.class),a);
        return information;
    }

}
