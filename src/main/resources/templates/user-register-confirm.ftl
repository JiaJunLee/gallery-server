<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>

<style type="text/css">
    .email-table {
        width: 820px;
    }
    .email-table .header {
        background-color: #006098;
        color: white;
        padding: 12px;
    }
    .email-table .content {
        padding: 18px;
        line-height: 32px;
        font-size: 14px;
        border-left: 1px solid #bfbfbf;
        border-right: 1px solid #bfbfbf;
    }
    .email-table .content .tips {
        color: #bfbfbf;
    }
    .email-table .content .english-tips {
        color: #bfbfbf;
        font-size: 13px;
    }
    .email-table .footer {
        padding: 0 18px 18px;
        line-height: 26px;
        border-left: 1px solid #bfbfbf;
        border-right: 1px solid #bfbfbf;
        font-size: 13px;
        color: #bfbfbf;
    }
    .email-table .footer-bottom {
        padding: 0 18px 18px;
        line-height: 26px;
        border-left: 1px solid #bfbfbf;
        border-right: 1px solid #bfbfbf;
        border-bottom: 1px solid #bfbfbf;
        font-size: 12px;
        color: #bfbfbf;
    }
    .email-table .split-line-td {
        padding-bottom: 18px;
        border-left: 1px solid #bfbfbf;
        border-right: 1px solid #bfbfbf;
    }
    .email-table .split-line {
        width: 100%;
        height: 1px;
        background-color: #eaeaea;
    }
    .content-table,.content-table tr th, .content-table tr td { border:1px solid #7f7f7f; }
    .content-table {
        width: 100%;
        border-collapse: collapse;
    }
    .content-table tr {
        line-height: 22px;
    }
    .content-table tr th {
        font-size: 14px;
    }
    .content-table tr td, .content-table tr th {
        padding: 0 12px;
    }
    .content-table tr td {
        font-size: 12px;
    }
</style>

<body>
<div>
    <table cellspacing="0" cellpadding="0" class="email-table">
        <tr align="left">
            <th class="header"><h1>Document Center Solution</h1></th>
        </tr>
        <tr>
            <td class="content">
            ${header}<br/>
            ${body}<br/>
                <table cellspacing="0" cellpadding="0" class="content-table">
                    <tr align="left">
                        <#list tableHeaders as tableHeaderItem>
                            <th>${tableHeaderItem}</th>
                        </#list>
                    </tr>
                    <#list tableRows as tableRow>
                        <tr align="left">
                            <#list tableRow as tableRowItem>
                                <td>${tableRowItem}</td>
                            </#list>
                        </tr>
                    </#list>
                </table>
            ${footer}<br/>
                <span class="english-tips">${tips}</span>
            </td>
        </tr>
        <tr>
            <td class="split-line-td">
                <div class="split-line"></div>
            </td>
        </tr>
        <tr>
            <td class="footer">
                <span class="tips">如果无法打开连接，请联系技术支持。</span><br/>
                <span class="english-tips">If you cannot open the link, please contact support.</span>
            </td>
        </tr>
        <tr>
            <td class="split-line-td">
                <div class="split-line"></div>
            </td>
        </tr>
        <tr align="center">
            <td class="footer-bottom">
                <div class="copyright">Copyright © 2000 - 2019 CargoSmart Limited. All rights reserved.</div>
                <div><a>Terms of Use</a> | <a>Privacy and Security Statement</a> | <a>Copyright Infringement Policy</a></div>
            </td>
        </tr>
    </table>
</div>
</body>
</html>