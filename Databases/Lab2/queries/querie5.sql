SELECT
    у.ГРУППА,
    ROUND(AVG(EXTRACT(YEAR FROM AGE(л.ДАТА_РОЖДЕНИЯ))), 1) AS СРЕДНИЙ_ВОЗРАСТ
FROM Н_УЧЕНИКИ у
JOIN Н_ЛЮДИ л ON у.ЧЛВК_ИД = л.ИД
GROUP BY у.ГРУППА
HAVING AVG(EXTRACT(YEAR FROM AGE(л.ДАТА_РОЖДЕНИЯ))) <
(
    SELECT AVG(EXTRACT(YEAR FROM AGE(л2.ДАТА_РОЖДЕНИЯ)))
    FROM Н_УЧЕНИКИ у2
    JOIN Н_ЛЮДИ л2 ON у2.ЧЛВК_ИД = л2.ИД
    WHERE у2.ГРУППА = '3100'
)
ORDER BY СРЕДНИЙ_ВОЗРАСТ;
