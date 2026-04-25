SELECT
    Н_ЛЮДИ.ИМЯ,
    COUNT(*) AS число_студентов
FROM
    Н_ЛЮДИ
WHERE
    Н_ЛЮДИ.ИД IN (
        SELECT Н_УЧЕНИКИ.ЧЛВК_ИД
        FROM Н_УЧЕНИКИ
        WHERE Н_УЧЕНИКИ.ПЛАН_ИД IN (  
            SELECT Н_ПЛАНЫ.ИД
            FROM Н_ПЛАНЫ
            WHERE Н_ПЛАНЫ.ФО_ИД IN ( 
                SELECT Н_ФОРМЫ_ОБУЧЕНИЯ.ИД
                FROM Н_ФОРМЫ_ОБУЧЕНИЯ
                WHERE Н_ФОРМЫ_ОБУЧЕНИЯ.НАИМЕНОВАНИЕ LIKE '%заочн%'
            )
        )
    )
GROUP BY
    Н_ЛЮДИ.ИМЯ
HAVING
    COUNT(*) < 50
ORDER BY
    число_студентов DESC,
    Н_ЛЮДИ.ИМЯ;