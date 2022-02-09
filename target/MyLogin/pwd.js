/*
 * @Description: 
 * @Author: MALossov
 * @Date: 2022-02-08 13:19:53
 * @LastEditTime: 2022-02-08 13:19:54
 * @LastEditors: MALossov
 * @Reference: 
 */

var password_login = document.getElementById('password_login');
var anniu_login = document.getElementById('conceal_login');
anniu_login.addEventListener('click', function() {
    if (password_login.type === 'password') {
        password_login.setAttribute('type', 'text');
        anniu_login.classList.add('yincang');
    } else {
        password_login.setAttribute('type', 'password');
        anniu_login.classList.remove('yincang');
    }
})

var password_sign = document.getElementById('password_sign');
var anniu_sign = document.getElementById('conceal_sign');
anniu_sign.addEventListener('click', function() {
    if (password_sign.type === 'password') {
        password_sign.setAttribute('type', 'text');
        anniu_sign.classList.add('yincang');
    } else {
        password_sign.setAttribute('type', 'password');
        anniu_sign.classList.remove('yincang');
    }
})