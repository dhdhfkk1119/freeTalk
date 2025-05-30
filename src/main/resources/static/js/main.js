let stompClient = null ;

function connect(){
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({},function(frame)){
        // 접속 시 서버에 알림
        stompClient.send("/app/user/connected",{},"");

        // 실시간 접속자 수신
        stompClient.subscribe("/topic/online-users",function(message){
            const users = JSON.parse(message.body);
            document.getElementById("userList").innerHTML = users.map(u => `<li>${u}</li>`).join('');
        });

        // 창 닫을 때 접속 종료 알림
         window.addEventListener("beforeunload",() => {
            stompClient.send("/app/user/disconnected",{},"");
         });
    }
    connect();
}