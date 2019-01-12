//JS Document 不能为空
function isBlank(str) {
    var re = /^\s*$/;
    return re.test(str);
}
//输入汉字
function isChinese(str) {
    var re = /^[\u4E00-\u9FA5]+$/;
    return re.test(str);
}
//输入英文
function isEnglish(str) {
    var re = /^[A-Za-z]+$/;
    return re.test(str);
}
//输入整数
function isInt(str) {
    var re = /^\d+/;
    return re.test(str);
}
//输入数字
function isNum(str) {
    var re = /^\d+(?:\.\d+)?/;
    return re.test(str);
}
//输入email
function isEmail(str) {
    var re = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    return re.test(str);
}
//输入电话
function isPhoneNum(str) {
    var re = /^(?:(?:\(0\d{2,3}\)[- ]?\d{7,8})|(?:(?:0\d{2,3}[- ]?)?\d{7,8})|(?:1\d{10}))$/;
    return re.test(str);
}
//输入18位身份证号
function isIdcardNum(str) {
    var re = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
    return re.test(str);
}
//输入IPv4地址
function isIPv4(str) {
    var re = /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/;
    return re.test(str);
}
//输入HTTP URL
function isUrl(str) {
    var re = /^(https?:\/\/)?[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?$/;
    return re.test(str);
}
//输入日期
function isDate(str) {
    var re = /^[1-2]\d{3}(-|\/)((0?[1-9]|1[0-2])(-|\/)(0?[1-9]|[1-2]\d))|((0?[13-9]|1[0-2])(-|\/)30)|((0?[13578]|1[02])(-|\/)31)$/;
    return re.test(str);
}

function $(id) {
    return document.getElementById(id);
}

function chkUsername() {
    if (isBlank($("inputText").value)) {
        $("usernamePrompt").style.color = "red";
        $("usernamePrompt").innerHTML = "*用户名必须填写！";
        return false;
    }
    else {
        $("usernamePrompt").style.color = "black";
        $("usernamePrompt").innerText = "*";
    }
    
    return true;
}
function chkPhonenum() {
    if (!isBlank($("inputPhone").value)) {
        if (!isPhoneNum($("inputPhone").value)) {
            $("phonenumPrompt").style.color = "red";
            $("phonenumPrompt").innerText = "电话号码格式不正确！";
            return false;
        }
    }
    $("phonenumPrompt").style.color = "black";
    $("phonenumPrompt").innerText = "*";
    return true;
}
function chkEmail() {
    if (!isBlank($("inputEmail").value)) {
        if (!isEmail($("inputEmail").value)) {
            $("emailPrompt").style.color = "red";
            $("emailPrompt").innerText = "*电子邮箱格式不正确！";
            return false;
        }
    }
    $("emailPrompt").style.color = "black";
    $("emailPrompt").innerText = "*";
    return true;
}

//function chkPasswords() {
//    var re = new RegExp(/^[a-zA-Z]\w{5,23}$/);
//    if (!re.test($("password").value)) {
//
//    }
//
//}
function chkPasswords() {
    if (isBlank($("inputPassword").value)) {
        $("passwordPrompt").style.color = "red";
        $("passwordPrompt").innerHTML = "*密码必须填写";
        return false;
    }

    else {
        $("passwordPrompt").style.color = "black";
        $("passwordPrompt").innerText = "*";
    }
    if ($("inputPassword").value != $("password2").value) {
        $("password2Prompt").style.color = "red";
        $("password2Prompt").innerHTML = "*两次密码输入不一致！";
        return false;
    }
    else {
        $("password2Prompt").style.color = "black";
        $("password2Prompt").innerText = "*";
    }
    return true;
}
function chkAll() {
    var msg = "";
    if (!chkUsername($("inputText").value)) {
        msg += "用户名输入不正确！\n";
    }
    if (!chkEmail($("inputEmail").value)) {
        msg += "电子邮箱输入不正确！\n";
    }
    if (!chkPasswords($("password2").value)) {
        msg += "密码输入不正确！\n";
    }
    //if (!chkPasswords2($("password2").value)) {
    //    msg += "请再次输入密码！\n";
    //}
    if (!chkPhonenum($("inputPhone").value)) {
        msg += "电话号码格式不正确！\n";
    }
    if (msg != "") {
        msg = msg.substr(0, msg.length - 1);
        alert(msg);
        return false;
    }
    return true;
}