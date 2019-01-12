   //输入汉字
function isChinese(str) {
    var re = /^[\u4E00-\u9FA5]+$/;
    return re.test(str);
}
//输入电话
function isPhoneNum(str) {
    var re = /^(?:(?:\(0\d{2,3}\)[- ]?\d{7,8})|(?:(?:0\d{2,3}[- ]?)?\d{7,8})|(?:1\d{10}))$/;
    return re.test(str);
}
//输入email
function isEmail(str) {
    var re = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    return re.test(str);
}