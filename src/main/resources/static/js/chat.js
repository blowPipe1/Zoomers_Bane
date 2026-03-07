let stompClient = null;

function connect(appId) {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages/' + appId, function (response) {
            const message = JSON.parse(response.body);
            showAtScreen(message);
        });
    });
}

function sendMessage(appId) {
    const input = document.getElementById("message-input");
    const text = input.value;
    if (text.trim() === "" || !stompClient) return;

    stompClient.send("/app/chat/" + appId, {}, JSON.stringify({
        'content': text,
        'applicationId': appId
    }));
    input.value = "";
}

function showAtScreen(msg) {
    const box = document.getElementById("messages-box");
    const msgDiv = document.createElement("div");
    msgDiv.className = "message";

    const avatarName = msg.authorAvatar ? msg.authorAvatar : "gachi.jpg";
    const name = msg.authorName || "";
    const surname = msg.authorSurname || "";
    const content = msg.content || "";
    const time = msg.timestamp || "";

    msgDiv.innerHTML = `
        <div class="avatar-wrapper">
            <img src="/images/${avatarName}" alt="avatar" class="avatar-circle">
        </div>
        <div class="message-body">
            <div class="message-info">
                <span class="author-name">${name} ${surname}</span>
                <span class="timestamp">${time}</span>
            </div>
            <div class="message-text">
                <p>${content}</p>
            </div>
        </div>`;

    box.appendChild(msgDiv);
    box.scrollTop = box.scrollHeight;
}

function openModal() {
    document.getElementById("infoModal").style.display = "block";
}

function closeModal() {
    document.getElementById("infoModal").style.display = "none";
}

window.onclick = function(event) {
    const modal = document.getElementById("infoModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}