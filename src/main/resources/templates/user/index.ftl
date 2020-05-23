<#include "/inc/layout.ftl" />

<@layout "用户中心">
  <div class="layui-container fly-marginTop fly-user-main">
    <@centerLeft level=1></@centerLeft>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <div class="site-tree-mobile layui-hide">
      <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>


    <div class="fly-panel fly-panel-user" pad20>
      <div class="layui-tab layui-tab-brief" lay-filter="user">
        <ul class="layui-tab-title" id="LAY_mine">
          <li data-type="mine-jie" lay-id="index" class="layui-this">我发的帖（<span>89</span>）</li>
          <li data-type="collection" data-url="/collection/find/" lay-id="collection">我收藏的帖（<span>16</span>）</li>
        </ul>
        <div class="layui-tab-content" style="padding: 20px 0;">
          <div class="layui-tab-item layui-show">
            <ul class="mine-view jie-row" id="fabu">
              <script id="tpl-fabu" type="text/html">
                <li>
                  <a class="jie-title" href="/post/{{d.id}}" target="_blank">{{ d.title }}</a>
                  <i>{{layui.util.toDateString(d.created, 'yyyy-MM-dd HH:mm:ss')}}</i>
                  <a class="mine-edit" href="/post/edit?id={{d.id}}">编辑</a>
                  <em>{{d.viewCount}}阅/{{d.commentCount}}答</em>
                </li>
              </script>
            </ul>
            <div id="LAY_page"></div>
          </div>
          <div class="layui-tab-item">
            <ul class="mine-view jie-row" id="collection">
              <script id="tpl-collection" type="text/html">
                <li>
                  <a class="jie-title" href="/post/{{d.id}}" target="_blank">{{d.title}}</a>
                  <i>收藏于{{layui.util.timeAgo(d.created, true)}}</i>
                </li>
              </script>
            </ul>
            <div id="LAY_page1"></div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script>
    layui.cache.page = 'user';

    // layui官网就有流加载实例
    layui.use(['laytpl', 'flow', 'util'], function() {
      var $ = layui.jquery;// 内置的jquery
      var laytpl = layui.laytpl;
      var flow = layui.flow;
      var util = layui.util;

      flow.load({
        elem: '#fabu' //指定列表容器
        ,isAuto: false // 取消自动加载
        ,done: function(page, next){// page是从第二页开始的
          var lis = [];
          $.get('/user/public?pn='+page, function(res){
            layui.each(res.data.records, function(index, item){
              var tpl = $("#tpl-fabu").html();// 获取tpl的内容
              // laytpl(tpl)确定要传入的模板，render：渲染的数据
              laytpl(tpl).render(item, function (html) {
                // 在 加载更多class 之前追加html元素 或者 使用官方那种写法
                $("#fabu .layui-flow-more").before(html);
              });
            });
            next(lis.join(''), page < res.data.pages);
          });
        }
      });

      flow.load({
        elem: '#collection'
        ,isAuto: false
        ,done: function(page, next){
          var lis = [];
          $.get('/user/collection?pn='+page, function(res){
            layui.each(res.data.records, function(index, item){
              var tpl = $("#tpl-collection").html();
              laytpl(tpl).render(item, function (html) {
                $("#collection .layui-flow-more").before(html);
              });
            });
            next(lis.join(''), page < res.data.pages);
          });
        }
      });
    });

  </script>
</@layout>