<#macro layout title>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta name="keywords" content="fly,layui,前端社区">
        <meta name="description" content="Fly社区是模块化前端UI框架Layui的官网社区，致力于为web开发提供强劲动力">
        <link rel="stylesheet" href="/res/layui/css/layui.css">
        <link rel="stylesheet" href="/res/css/global.css">
        <script src="/res/layui/layui.js"></script>
        <script src="/res/layui/js/jquery.min.js"></script>
        <#--<script src="/res/layui/js/sockjs.js"></script>-->
        <#--<script src="/res/layui/js/stomp.js"></script>-->
        <#--<script src="/res/layui/js/im.js"></script>-->
        <#--<script src="/res/layui/js/chat.js"></script>-->
    </head>
    <body>

    <#include "/inc/header.ftl" />
    <#--<#include "/inc/header-panel.ftl" />-->
    <#include "/inc/common.ftl" />

    <#-- 标签中间的数据可以引入nested -->
    <#nested />

    <#include "/inc/footer.ftl" />

    <script>
        // layui.cache.page = '';
        layui.cache.user = {
            username: '${profile.username!"游客"}'
            ,uid: ${profile.id!"-1"}
            ,avatar: '${profile.avatar!"/res/images/avatar/00.jpg"}'
            ,experience: 83
            ,sex: '${profile.sex!"男"}'
        };

        layui.config({
            version: "3.0.0"
            ,base: '/res/mods/' //这里实际使用时，建议改成绝对路径
        }).extend({
            fly: 'index'
        }).use('fly');
    </script>
    </body>
    </html>
</#macro>