# tinkoff-ryazan-test-task
Test task for interview

Heroku link: https://translation-app-tinkoff.herokuapp.com/
!!!На Heroku не работает h2 database (ограничения платформы)!!!

Задание: REST back, который переводит текст по словам
/translate - POST languageCode, text
/available - GET

Для тестирования по / развернут swagger-ui
/h2-console - консоль. Креды в application.yml

Два варианта внешнего API: спираченная с клиентского сайта duckduckgo и честная yandex cloud api

duckduckgo: dev profile, /available недоступен
yandex: prod profile, требуется обновлять iam token, креды для облака в application.yml, шедулер каждые 12 часов (не меньше). 
Креды для яндекс-облака я на heroku не заливал, так что через 12 часов отвалится.

Многопоточка - на стримах, используется Stream API join fork пул.

Для записи в БД применяется АоП, т.к. считаю, что в данной задаче это - задача мониторинга, и ее надо держать отдельно от бизнес-логике
