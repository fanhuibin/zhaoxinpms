//==本JS是加载Lodop插件或Web打印服务CLodop/Lodop7的综合示例，可直接使用，建议理解后融入自己程序==

import { MessageBox } from 'element-ui';
var CreatedOKLodopObject, CLodopIsLocal, CLodopJsState;

//==判断是否需要CLodop(那些不支持插件的浏览器):==
function needCLodop() {
    try {
        var ua = navigator.userAgent;
        if (ua.match(/Windows\sPhone/i)) return true;
        if (ua.match(/iPhone|iPod|iPad/i)) return true;
        if (ua.match(/Android/i)) return true;
        if (ua.match(/Edge\D?\d+/i)) return true;

        var verTrident = ua.match(/Trident\D?\d+/i);
        var verIE = ua.match(/MSIE\D?\d+/i);
        var verOPR = ua.match(/OPR\D?\d+/i);
        var verFF = ua.match(/Firefox\D?\d+/i);
        var x64 = ua.match(/x64/i);
        if (!verTrident && !verIE && x64) return true;
        else if (verFF) {
            verFF = verFF[0].match(/\d+/);
            if (verFF[0] >= 41 || x64) return true;
        } else if (verOPR) {
            verOPR = verOPR[0].match(/\d+/);
            if (verOPR[0] >= 32) return true;
        } else if (!verTrident && !verIE) {
            var verChrome = ua.match(/Chrome\D?\d+/i);
            if (verChrome) {
                verChrome = verChrome[0].match(/\d+/);
                if (verChrome[0] >= 41) return true;
            }
        }
        return false;
    } catch (err) {
        return true;
    }
}

//加载CLodop时用双端口(http是8000/18000,而https是8443/8444)以防其中某端口被占,
//主JS文件名“CLodopfuncs.js”是固定名称，其内容是动态的，与其链接的打印环境有关:
function loadCLodop() {
    if (CLodopJsState == 'loading' || CLodopJsState == 'complete') return;
    CLodopJsState = 'loading';
    var head = document.head || document.getElementsByTagName('head')[0] || document.documentElement;
    var JS1 = document.createElement('script');
    var JS2 = document.createElement('script');

    if (window.location.protocol == 'https:') {
        JS1.src = 'https://localhost.lodop.net:8443/CLodopfuncs.js';
        JS2.src = 'https://localhost.lodop.net:8444/CLodopfuncs.js';
    } else {
        JS1.src = 'http://localhost:8000/CLodopfuncs.js';
        JS2.src = 'http://localhost:18000/CLodopfuncs.js';
    }
    JS1.onload = JS2.onload = function () {
        CLodopJsState = 'complete';
    };
    JS1.onerror = JS2.onerror = function (evt) {
        CLodopJsState = 'complete';
    };
    head.insertBefore(JS1, head.firstChild);
    head.insertBefore(JS2, head.firstChild);
    CLodopIsLocal = !!(JS1.src + JS2.src).match(/\/\/localho|\/\/127.0.0./i);
}

function downloadLodop() {
    const lodopurl = '../lodop/CLodop_Setup_for_Win32NT.exe';
    window.location.href = lodopurl;
}

if (needCLodop()) {
    loadCLodop();
} //开始加载

//==获取LODOP对象主过程,判断是否安装、需否升级:==
export function getLodop(oOBJECT, oEMBED) {
    var strFontTag = "<br><font color='#FF00FF'>打印控件";
    var strLodopInstall = strFontTag + "未安装!点击这里<a href='install_lodop32.exe' target='_self'>执行安装</a>";
    var strLodopUpdate = strFontTag + "需要升级!点击这里<a href='install_lodop32.exe' target='_self'>执行升级</a>";
    var strLodop64Install = strFontTag + "未安装!点击这里<a href='install_lodop64.exe' target='_self'>执行安装</a>";
    var strLodop64Update = strFontTag + "需要升级!点击这里<a href='install_lodop64.exe' target='_self'>执行升级</a>";
    var strCLodopInstallA =
        "<br><font color='#FF00FF'>Web打印服务CLodop未安装启动，点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>下载执行安装</a>";
    var strCLodopInstallB = "<br>（若此前已安装过，可<a href='CLodop.protocol:setup' target='_self'>点这里直接再次启动</a>）";
    var strCLodopUpdate = "<br><font color='#FF00FF'>Web打印服务CLodop需升级!点击这里<a href='CLodop_Setup_for_Win32NT.exe' target='_self'>执行升级</a>";
    var strLodop7FontTag = "<br><font color='#FF00FF'>Web打印服务Lodop7";
    var strLodop7HrefX86 = "点击这里<a href='Lodop7_Linux_X86_64.tar.gz' target='_self'>下载安装</a>(下载后解压，点击lodop文件开始执行)";
    var strLodop7HrefARM = "点击这里<a href='Lodop7_Linux_ARM64.tar.gz'  target='_self'>下载安装</a>(下载后解压，点击lodop文件开始执行)";
    var strLodop7Install_X86 = strLodop7FontTag + '未安装启动，' + strLodop7HrefX86;
    var strLodop7Install_ARM = strLodop7FontTag + '未安装启动，' + strLodop7HrefARM;
    var strLodop7Update_X86 = strLodop7FontTag + '需升级，' + strLodop7HrefX86;
    var strLodop7Update_ARM = strLodop7FontTag + '需升级，' + strLodop7HrefARM;
    var strInstallOK = '，成功后请刷新本页面或重启浏览器。</font>';
    var LODOP;
    try {
        var isWinIE = /MSIE/i.test(navigator.userAgent) || /Trident/i.test(navigator.userAgent);
        var isWinIE64 = isWinIE && /x64/i.test(navigator.userAgent);
        var isLinuxX86 = /Linux/i.test(navigator.platform) && /x86/i.test(navigator.platform);
        var isLinuxARM = /Linux/i.test(navigator.platform) && /aarch/i.test(navigator.platform);

        if (needCLodop() || isLinuxX86 || isLinuxARM) {
            try {
                LODOP = getCLodop();
            } catch (err) {
                MessageBox({
                    title: '温馨提示',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonText: '下载',
                    cancelButtonText: '取消',
                    message: '检测到您还未安装套打控件,请确认启用后再打印。或您可点击下载该套打控件，安装成功后刷新页面再进行打印',
                    callback: res => {
                        if (res === 'confirm') {
                            downloadLodop();
                        }
                    },
                });
            }
            if (!LODOP && CLodopJsState !== 'complete') {
              
                alert('打印控件没准备好，请稍后再试！')
                return;
            }
            var strAlertMessage;
            if (!LODOP) {
                MessageBox({
                    title: '温馨提示',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonText: '下载',
                    cancelButtonText: '取消',
                    message: '检测到您还未安装套打控件,请确认启用后再打印。或您可点击下载该套打控件，安装成功后刷新页面再进行打印',
                    callback: res => {
                        if (res === 'confirm') {
                            downloadLodop();
                        }
                    },
                });
                return;
            } else {
                if (isLinuxX86 && LODOP.CVERSION < '7.0.4.3') strAlertMessage = strLodop7Update_X86;
                else if (isLinuxARM && LODOP.CVERSION < '7.0.4.3') strAlertMessage = strLodop7Update_ARM;
                else if (CLODOP.CVERSION < '4.1.6.1') strAlertMessage = strCLodopUpdate;

                if (strAlertMessage) document.body.innerHTML = strAlertMessage + strInstallOK + document.body.innerHTML;
            }
        } else {
            //==如果页面有Lodop插件就直接使用,否则新建:==
            if (oOBJECT || oEMBED) {
                if (isWinIE) LODOP = oOBJECT;
                else LODOP = oEMBED;
            } else if (!CreatedOKLodopObject) {
                LODOP = document.createElement('object');
                LODOP.setAttribute('width', 0);
                LODOP.setAttribute('height', 0);
                LODOP.setAttribute('style', 'position:absolute;left:0px;top:-100px;width:0px;height:0px;');
                if (isWinIE) LODOP.setAttribute('classid', 'clsid:2105C259-1E0C-4534-8141-A753534CB4CA');
                else LODOP.setAttribute('type', 'application/x-print-lodop');
                document.documentElement.appendChild(LODOP);
                CreatedOKLodopObject = LODOP;
            } else LODOP = CreatedOKLodopObject;
            //==Lodop插件未安装时提示下载地址:==
            if (!LODOP || !LODOP.VERSION) {
                document.body.innerHTML = (isWinIE64 ? strLodop64Install : strLodopInstall) + strInstallOK + document.body.innerHTML;
                return LODOP;
            }
            if (LODOP.VERSION < '6.2.2.6') {
                document.body.innerHTML = (isWinIE64 ? strLodop64Update : strLodopUpdate) + strInstallOK + document.body.innerHTML;
            }
        }
        //===如下空白位置适合调用统一功能(如注册语句、语言选择等):=======================

        //===============================================================================
        return LODOP;
    } catch (err) {
        alert('getLodop出错:' + err);
    }
}
