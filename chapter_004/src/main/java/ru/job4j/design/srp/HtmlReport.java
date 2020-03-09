package ru.job4j.design.srp;

import com.google.common.base.Joiner;

import java.util.function.Predicate;

/**
 * Отдел программистов потребовал ответы в виде html.
 */
public class HtmlReport extends ReportEngine {
    private Store store;
    private Joiner joiner;

    public HtmlReport(Store store) {
        super(store);
        this.store = store;
    }

    private String getTag(String tag, String data) {
        return "<" + tag + ">" + data + "</" + tag + ">";
    }

    private String getTag(String tag, Object data) {
        return getTag(tag, data.toString());
    }

    @Override
    public String generate(Predicate<Employer> filter) {
        return getTag("table",
                getTag("tr",
                        getTag("td", "Name")
                                + getTag("td", "Hired")
                                + getTag("td", "Fired")
                                + getTag("td", "Salary")
                ) + store.findBy(filter).stream().map(employer ->
                        getTag("tr",
                                getTag("td", employer.getName())
                                        + getTag("td", employer.getHired())
                                        + getTag("td", employer.getFired())
                                        + getTag("td", employer.getSalary()
                                )
                        )
                ).reduce((s, s2) -> s + s).orElse("")
        );
    }

}
