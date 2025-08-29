
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class JavaTaskApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JavaTaskApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String finalSql =
            "WITH eligible AS (\n" +
            "  SELECT p.amount AS salary,\n" +
            "         (e.first_name || ' ' || e.last_name) AS name,\n" +
            "         DATE_PART('year', AGE(p.payment_time::date, e.dob::date))::int AS age,\n" +
            "         d.department_name,\n" +
            "         ROW_NUMBER() OVER (ORDER BY p.amount DESC) AS rn\n" +
            "  FROM payments p\n" +
            "  JOIN employee e ON e.emp_id = p.emp_id\n" +
            "  JOIN department d ON d.department_id = e.department\n" +
            "  WHERE EXTRACT(DAY FROM p.payment_time) <> 1\n" +
            ")\n" +
            "SELECT salary, name AS NAME, age AS AGE, department_name AS DEPARTMENT_NAME\n" +
            "FROM eligible\n" +
            "WHERE rn = 1;";

        System.out.println("Final SQL to submit:");
        System.out.println(finalSql);
    }
}
