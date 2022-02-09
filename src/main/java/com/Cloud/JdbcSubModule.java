package com.Cloud;
import java.sql.*;
import java.util.ResourceBundle;
/**
 * 方法描述
 * 注意：实际开发中不建议把数据库链接数据库的信息写死到程序中
 *
 * @since: 1.0.0
 * @author: Mr.Lee
 */
public class JdbcSubModule {

    private static String testEmail;
    private static String testPassword;
    private static String testUsr;
    private static String rtnStr;


    public static String InnerStart(int chic,String email,String Password,String usrname) throws SQLException {

        testEmail = email;
        testPassword = Password;
        testUsr = usrname;

        System.out.println("Request-in: present usrname:"+usrname+",email:"+email+",password:"+Password+"choice is"+ chic);

        //使用资源绑定器绑定属性配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        try {
            Class.forName(driver);
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                switch (chic) {
                    case 1:
                        rtnStr = SignIN(conn);
                        break;
                    case 2:
                        rtnStr = SignUP(conn);
                        break;
                    case 3:
                        Qurey(conn);

                }

                System.out.println("Done the result is:" + rtnStr);
                return rtnStr;

            }

        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rtnStr;
    }

    private static String SignIN(Connection conn) throws SQLException {
        String usr;
        try(PreparedStatement ps=conn.prepareStatement("select * from userdata where Email = ? and Password = ?")){
            ps.setObject(1,testEmail);
            ps.setObject(2,testPassword);
            ResultSet rst = ps.executeQuery();
            if(rst.next()){
                int id = rst.getInt("Number");
                String UserName = rst.getString("UserName");
                String Password = rst.getString("Password");
                String email = rst.getString("Email");
                usr=String.join("~~",String.valueOf(id),UserName,Password ,email,"NormalLogin","Old User");
                System.out.println("Geted user signed in:" +usr);
            }else {
                return "Wrong!";
            }
        }
        return usr;
    }

    private static String SignUP(Connection conn) throws SQLException {
        boolean isSignedUP = false;
        int FinalNum = 0;
        int crtNum = -1;
        String InsUsr;

        try(PreparedStatement ps=conn.prepareStatement("select * from userdata where Email = ? or UserName = ?")) {
            ps.setObject(1, testEmail);
            ps.setObject(2, testUsr);
            ResultSet rst = ps.executeQuery();
            //if(rst.next()){
            while (rst.next()) {
                crtNum = rst.getInt("Number");
                String UserName = rst.getString("UserName");
                String Password = rst.getString("Password");
                String email = rst.getString("Email");
                System.out.println("Geted old usr change pwd :" + String.join(",", String.valueOf(crtNum), UserName, Password, email));
                if (!(email.equals(testEmail) && UserName.equals(testUsr))) {
                    System.out.println("Geted usrname:"+UserName+" but "+testUsr+","+email+" but "+testEmail);
                    System.out.println("Unpaired UsrName&Email wrong!\n");
                    crtNum=-1;
                    return "Wrong!";
                }
                isSignedUP = true;
            }
        }

        if(crtNum != -1){
            try (PreparedStatement psRep = conn.prepareStatement("UPDATE `myclouddrive`.`userdata` SET `Password` = ? WHERE (`Number` = ?)")){
                psRep.setObject(1,testPassword);
                psRep.setObject(2,crtNum);
                psRep.executeUpdate(); // 1
            }
        }else{
        //进行一个用户的搜索
        try (PreparedStatement psFindLast = conn.prepareStatement("select Number from `myclouddrive`.`userdata` order by Number desc limit 1")){
            ResultSet rstFind = psFindLast.executeQuery();
            rstFind.next();
            FinalNum = rstFind.getInt("Number");
            FinalNum++;
            System.out.println("The insert number should be:"+ FinalNum);
        }

        //开始插入用户
        try (PreparedStatement psIns = conn.prepareStatement(
                "INSERT INTO myclouddrive.userdata (Number, UserName,Password, Email) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            psIns.setObject(1, FinalNum);
            psIns.setObject(2, testUsr);
            psIns.setObject(3, testPassword);
            psIns.setObject(4, testEmail);
            //InsUsr = String.join(",",String.valueOf(id),UserName,Password,email);
            psIns.executeUpdate(); // 1
            }
        }
            InsUsr = String.join("~~",String.valueOf(FinalNum),testUsr,testPassword,testEmail);
            if(isSignedUP){
                InsUsr+="~~Have Signed Once! Password have been changed!~~Old User";
            }else{
                InsUsr+="~~Signed UP success!Welcome!~~New User";
            }

        return InsUsr;
    }

    private static void Qurey(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("select * from userdata;")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("Number");
                    String UserName = rs.getString("UserName");
                    String Password = rs.getString("Password");
                    String email = rs.getString("Email");
                    System.out.println("Geted usr"+String.join(",",String.valueOf(id),UserName,Password,email));
                }

            }

        }
    }
}



