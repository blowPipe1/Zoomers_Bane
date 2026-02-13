<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="ru">
<body>
<h2><@spring.message "email-reset.header"/></h2>
<p><@spring.message "email-reset.greeting"/>, ${name}!</p>
<p><@spring.message "email-reset.instruction"/>:</p>
<a href="${resetUrl}" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">
    Reset
</a>
<p><@spring.message "email-reset.notice"/>.</p>
</body>
</html>