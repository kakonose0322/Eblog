if (typeof (tio) == "undefined") {
    tio = {};
}
tio.ws = {};
tio.ws = function ($, layim) {
    this.heartbeatTimeout = 5000; // 心跳超时时间，单位：毫秒
    this.heartbeatSendInterval = this.heartbeatTimeout / 2;

    var self = this;
    // 建立连接
    this.connect = function () {
        var url = "ws://localhost:9326?userId=" + self.userId;
        var socket = new WebSocket(url);
        self.socket = socket;

        socket.onopen = function () {
            console.log("tio ws 启动~");
            self.lastInteractionTime(new Date().getTime());
            //建立心跳
            self.ping();
        };

        socket.onclose = function () {
            console.log("tio ws 关闭~");
            //尝试重连
            self.reconn();
        }

        socket.onmessage = function (res) {
            console.log("接收到消息！！")
            console.log(res)
            var msgBody = eval('(' + res.data + ')');
            if(msgBody.emit === 'chatMessage'){
                layim.getMessage(msgBody.data);
            }
            self.lastInteractionTime(new Date().getTime());
        }
    };

    this.openChatWindow = function () {
        // 获取个人信息
        $.ajax({
            url: "/chat/getMineAndGroupData",
            async: false,// Ajax设为同步，不然异步请求，后续需要这里信息的会报错
            success: function (res) {
                self.group = res.data.group;
                self.mine = res.data.mine;
                self.userId = self.mine.id;
            }
        });
        console.log(self.group);
        console.log(self.mine);
        var cache =  layui.layim.cache();
        cache.mine = self.mine;

        layim.chat(self.group);// 打开窗口
        layim.setChatMin(); //收缩聊天面板
    };
    this.sendChatMessage = function (res) {
        self.socket.send(JSON.stringify({
            type: 'chatMessage'
            ,data: res
        }));
    }
    // 历史信息回显
    this.initHistoryMess = function () {
        localStorage.clear();
        $.ajax({
            url: '/chat/getGroupHistoryMsg',
            success: function (res) {
                var data = res.data;
                if(data.length < 1) {
                    return;
                }
                // 获得历史数据，遍历
                for (var i in data){
                    layim.getMessage(data[i]);
                }
            }
        });
    }


    //-----------重试机制---------------
    this.lastInteractionTime = function () {
        // debugger;
        if (arguments.length == 1) {
            this.lastInteractionTimeValue = arguments[0]
        }
        return this.lastInteractionTimeValue
    }

    this.ping = function () {
        console.log("------------->准备心跳中~");
        //建立一个定时器，定时心跳
        self.pingIntervalId = setInterval(function () {
            var iv = new Date().getTime() - self.lastInteractionTime(); // 已经多久没发消息了
            // debugger;
            // 单位：秒
            if ((self.heartbeatSendInterval + iv) >= self.heartbeatTimeout) {
                self.socket.send(JSON.stringify({
                    type: 'pingMessage'
                    ,data: 'ping'
                }))
                console.log("------------->心跳中~")
            }
        }, self.heartbeatSendInterval)
    };
    this.reconn = function () {
        // 先删除心跳定时器
        clearInterval(self.pingIntervalId);
        // 然后尝试重连
        self.connect();
    };
};