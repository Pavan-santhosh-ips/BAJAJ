Bajaj Finserv Health ..... Qualifier 1 ..... JAVA

Name: I Pavan Santhosh

Reg No: 22BCE20064

Email: pavansanthoships@gmail.com

Task :

On startup, the Spring Boot app calls generateWebhook API with my details.

Gets a webhook URL and accessToken.

Submits the SQL Query (Question 2 as the regd number end witn even ) as final answer.

Final SQL Query:

SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME,
       COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT
FROM EMPLOYEE e1
JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID
LEFT JOIN EMPLOYEE e2
    ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB
GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME
ORDER BY e1.EMP_ID DESC;

How to Run:

.\mvnw.cmd clean package
java -jar target\bajajtest-0.0.1-SNAPSHOT.jar
