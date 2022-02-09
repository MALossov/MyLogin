/*
 * @Description: 
 * @Author: MALossov
 * @Date: 2022-02-08 13:35:54
 * @LastEditTime: 2022-02-08 16:46:12
 * @LastEditors: MALossov
 * @Reference: 
 */

function IsEmail(str) {
    var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    return reg.test(str);
}


function SignIN() {

    signInStatus = document.getElementById("SignInStatus");
    var emailIN = document.getElementById("Email_login").value;
    var pwdIN = document.getElementById("password_login").value;

    signInStatus.style.color = "tan";
    signInStatus.innerHTML = "HaveRequested!<br>Time:" + Date();


    if (!IsEmail(emailIN)) {
        signInStatus.style.color = "red";
        signInStatus.innerHTML = "请输入正确的Email格式！<br>";
        return;
    }


    if (pwdIN.length == 0) {
        signInStatus.style.color = "red";
        signInStatus.innerHTML = "请输入有效的密码！";
        return;
    }

    var SignINRequest = "?Choice=SignIN&email=" + emailIN + '&pwd=' + pwdIN;
    signInStatus.innerHTML = "YouSignINFor:<br>" + SignINRequest;

    PostReq(SignINRequest);
}

function SignUP() {
    //$.post("UserSignUPRequest# " + signUpSummit);
    // document.write("SignUPRequest Posted\r\nNowTIME:" + Date());
    signUpStatus = document.getElementById("SignUpStatus");
    var emailSU = document.getElementById("Email_sign").value;
    var pwdSU = document.getElementById("password_sign").value;
    var usrNameSU = document.getElementById("UserName_sign").value;

    signUpStatus.style.color = "tan";
    signUpStatus.innerHTML = "HaveRequested!<br>Time:" + Date();


    if (!IsEmail(emailSU)) {
        signUpStatus.style.color = "red";
        signUpStatus.innerHTML = "请输入正确的Email格式！<br>";
        return;
    }


    if (pwdSU.length == 0 || usrNameSU.length == 0) {
        signUpStatus.style.color = "red";
        signUpStatus.innerHTML = "请输入有效的密码/用户名！";
        return;
    }
    var SignUPRequest = "?Choice=SignUP&email=" + emailSU + '&usrname=' + usrNameSU + '&pwd=' + pwdSU;
    signUpStatus.innerHTML = "YouSignFor:<br>" + SignUPRequest;

    PostReq(SignUPRequest);
}
//一些服务器返回的量，用来存入全局数据在下一次中打开
var usrname; //用户名
var loginStatus; //是否正常登录
var usrgroup; //新人组还是老人组
var usremail;   //用户的邮箱

function PostReq(rqst) {
    var xhr = new XMLHttpRequest(); //实例化XMLHttpRequest 对象
    xhr.open("GET", "/MyLogin/MyCloud"+rqst, false); //建立连接，要求同步响应
    xhr.send(); //发送请求
    var dataStr = xhr.responseText; //用变量接收数据
    console.log(dataStr);//打印调试输出

    //错误的情况
    if(dataStr == 'Wrong!'){
        document.getElementById("SignInStatus").innerHTML = "邮箱或密码错误！"
        document.getElementById("SignInStatus").style.color = "red";

        document.getElementById("SignUpStatus").innerHTML = "用户名和邮箱不对应，请联系邮箱管理员！"
        document.getElementById("SignInStatus").style.color = "red";
        return;
    }
    //跳转进用户页面
    location.href="/MyLogin/UsrPage.html?"+dataStr
}


function LoadData() {
    let dataStr=window.location.search;
    dataStr=decodeURI(dataStr);
    let dataStrArr = dataStr.split("~~");
    console.log(dataStrArr);    //打印测试分隔数据

    usrname = dataStrArr[1];
    loginStatus = dataStrArr[4];
    usrgroup = dataStrArr[5];
    usremail = dataStrArr[3];


    document.getElementById("UserName").innerHTML = usrname;
    document.getElementById("UserGroup").innerHTML = usrgroup;
    document.getElementById("LoginStatus").innerHTML = loginStatus;
    document.getElementById("usrEmail").innerHTML = "UserEmail:"+ usremail;
}