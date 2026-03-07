<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message "chat.title"/></title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="<@spring.url '/js/chat.js'/>"></script>
    <link rel="stylesheet" href="<@spring.url '/css/chat.css'/>">
</head>
<body>
<div class="chat-container">
    <h3><@spring.message "chat.header"/>: ${vacancy.name}</h3>

    <div id="messages-box">
        <#list history as msg>
            <div class="message">
                <div class="avatar-wrapper">
                    <#if msg.authorAvatar?has_content>
                        <img src="/images/${msg.authorAvatar}" alt="avatar" class="avatar-circle">
                    <#else>
                        <img src="/images/gachi.jpg" alt="avatar" class="avatar-circle">
                    </#if>
                </div>
                <div class="message-body">
                    <div class="message-info">
                        <span class="author-name">${msg.authorName!""} ${msg.authorSurname!""}</span>
                        <span class="timestamp">${msg.timestamp}</span>
                    </div>
                    <div class="message-text">
                        <p>${msg.content! ""}</p>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <div class="input-group">

        <div class="input-group">
            <button class="info-btn" onclick="openModal()">ℹ️</button>
            <input type="text" id="message-input" placeholder="<@spring.message "chat.placeholder"/>">
            <button onclick="sendMessage(${app.id})"><@spring.message "common.send-btn"/></button>
        </div>

        <div id="infoModal" class="modal">
            <div class="modal-content">
                <span class="close-btn" onclick="closeModal()">&times;</span>
                <h2><@spring.message "chat.vacancy.details"/></h2>
                <p><strong><@spring.message "common.vacancy-title"/>: </strong> ${vacancy.name! ""}</p>
                <p><strong><@spring.message "common.description"/>: </strong> ${vacancy.description! "Нет описания"}</p>
                <p><strong><@spring.message "common.salary"/>: </strong> ${vacancy.salary}</p>
                <p><strong><@spring.message "common.category"/>: </strong> ${vacancy.category}</p>
                <p><strong><@spring.message "common.experience"/>: </strong> ${vacancy.expFrom!"N/A"} - ${vacancy.expTo!"N/A"}</p>
                <hr>
                <h2><@spring.message "chat.resume.details"/></h2>
                <p><strong><@spring.message "common.resume-title"/>: </strong> ${resume.name! "Не указано"}</p>
                <p><strong><@spring.message "common.category"/>: </strong> ${resume.category}</p>
                <p><strong><@spring.message "common.salary"/>: </strong> ${resume.salary}</p>
            </div>
        </div>
    </div>
</div>

<script>
    connect(${app.id});
    window.onload = function() {
        const box = document.getElementById("messages-box");
        box.scrollTop = box.scrollHeight;
    };
</script>
</body>
</html>