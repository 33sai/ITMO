
SELECT 
    Н_ЛЮДИ.ИМЯ,               
    Н_ЛЮДИ.ФАМИЛИЯ,              
    Н_ЛЮДИ.ОТЧЕСТВО,               
    Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ          
FROM 
    Н_ЛЮДИ
WHERE 

    Н_ЛЮДИ.ИД IN (SELECT Н_УЧЕНИКИ.ЧЛВК_ИД FROM Н_УЧЕНИКИ)

    AND Н_ЛЮДИ.ИМЯ IS NOT NULL
    AND Н_ЛЮДИ.ИМЯ != ''
    AND Н_ЛЮДИ.ИМЯ != '.'
    AND Н_ЛЮДИ.ИМЯ != ' '

    AND Н_ЛЮДИ.ИМЯ IN (
        SELECT Inner_ЛЮДИ.ИМЯ
        FROM Н_ЛЮДИ AS Inner_ЛЮДИ
        WHERE Inner_ЛЮДИ.ИД IN (SELECT Н_УЧЕНИКИ.ЧЛВК_ИД FROM Н_УЧЕНИКИ) 

          AND Inner_ЛЮДИ.ИМЯ IS NOT NULL
          AND Inner_ЛЮДИ.ИМЯ != ''
          AND Inner_ЛЮДИ.ИМЯ != '.'
          AND Inner_ЛЮДИ.ИМЯ != ' '
        GROUP BY Inner_ЛЮДИ.ИМЯ
        HAVING COUNT(DISTINCT Inner_ЛЮДИ.ДАТА_РОЖДЕНИЯ) > 1 
    )
ORDER BY 
    Н_ЛЮДИ.ИМЯ,              
    Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ,     
    Н_ЛЮДИ.ФАМИЛИЯ;           