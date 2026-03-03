<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<body>
<h2>${msgHeader}</h2>
<p>${msgGreeting}, ${name}!</p>
<p>${msgInstruction}:</p>
<a href="${resetUrl}" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">
    ${msgBtn}
</a>
<p>${msgNotice}.</p>
</body>
</html>