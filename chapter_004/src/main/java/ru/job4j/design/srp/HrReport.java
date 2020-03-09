package ru.job4j.design.srp;

import java.util.List;
import java.util.function.Predicate;

/**
 * Отдел HR попросил выводить сотрудников в порядке убывания зарплаты и убрать поля даты найма и увольнения.
 */
public class HrReport extends ReportEngine {
    private Store store;

    public HrReport(Store store) {
        super(store);
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employer> filter) {
        List<Employer> employers = store.findBy(filter);
        employers.sort((o1, o2) -> Double.compare(o2.getSalary(), o1.getSalary()));
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;")
                .append(System.lineSeparator());
        for (Employer employer : employers) {
            text.append(employer.getName()).append(";")
                    .append(employer.getSalary()).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }

}
