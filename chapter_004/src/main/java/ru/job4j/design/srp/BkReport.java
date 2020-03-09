package ru.job4j.design.srp;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Отдел бухгалтерии попросил изменить вид зарплаты.
 */
public class BkReport extends ReportEngine {
    private Store store;
    private Function<Double, String> mapSalary;

    public BkReport(Store store) {
        this(store, "%.2f р.");
    }

    public BkReport(Store store, String format) {
        this(store, aDouble -> String.format(Locale.ENGLISH, format, aDouble));
    }

    public BkReport(Store store, Function<Double, String> mapSalary) {
        super(store);
        this.store = store;
        this.mapSalary = mapSalary;
    }


    @Override
    public String generate(Predicate<Employer> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator());
        for (Employer employer : store.findBy(filter)) {
            text.append(employer.getName()).append(";")
                    .append(employer.getHired()).append(";")
                    .append(employer.getFired()).append(";")
                    .append(
                            this.mapSalary.apply(employer.getSalary())
                    ).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
