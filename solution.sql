WITH eligible AS (
  SELECT p.amount AS salary,
         (e.first_name || ' ' || e.last_name) AS name,
         DATE_PART('year', AGE(p.payment_time::date, e.dob::date))::int AS age,
         d.department_name,
         ROW_NUMBER() OVER (ORDER BY p.amount DESC) AS rn
  FROM payments p
  JOIN employee e ON e.emp_id = p.emp_id
  JOIN department d ON d.department_id = e.department
  WHERE EXTRACT(DAY FROM p.payment_time) <> 1
)
SELECT salary, name AS NAME, age AS AGE, department_name AS DEPARTMENT_NAME
FROM eligible
WHERE rn = 1;
