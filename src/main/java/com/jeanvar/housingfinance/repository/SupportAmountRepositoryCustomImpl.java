package com.jeanvar.housingfinance.repository;

import com.jeanvar.housingfinance.core.Institute;
import com.jeanvar.housingfinance.util.ObjUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportAmountRepositoryCustomImpl implements SupportAmountRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<Year, Map<Institute, Integer>> yearlySummary() {
        Query q = entityManager.createQuery(
            "SELECT s.year, s.institute, SUM(s.amount) " +
            "FROM SupportAmount s " +
            "GROUP BY s.year, s.institute"
        );

        List<Object[]> res = q.getResultList();

        Map<Year, Map<Institute, Integer>> ret = new HashMap<>();

        for (Object[] row: res) {
            Year year = (Year) row[0];
            Institute institute = (Institute) row[1];
            int amount = ObjUtil.toInt(row[2]);

            Map<Institute, Integer> insAmount = ret.getOrDefault(year, new HashMap<>());
            insAmount.put(institute, amount);

            ret.put(year, insAmount);
        }

        return ret;
    }

    @Override
    public YearAndInstitute highestAmountYearAndInstitute() {
        Query q = entityManager.createNativeQuery(
            "SELECT s.year, i.name, SUM(s.amount) sum_amount " +
            "FROM support_amount s " +
            "JOIN institute i ON i.id = s.institute_id " +
            "GROUP BY s.year, s.institute_id " +
            "ORDER BY sum_amount DESC " +
            "LIMIT 1"
        );
        List<Object[]> res = q.getResultList();

        YearAndInstitute yearAndInstitute = new YearAndInstitute();
        yearAndInstitute.setYear(Year.of((short) res.get(0)[0]));
        yearAndInstitute.setInstituteName((String) res.get(0)[1]);

        return yearAndInstitute;
    }

    @Override
    public HighLowSupportAmount highLowSupportAmount(Institute institute) {
        Query maxQ = entityManager.createNativeQuery(
            "SELECT s.year, ROUND(AVG(s.amount)) amt " +
                "FROM support_amount s " +
                "WHERE s.institute_id = ?1 " +
                "GROUP BY s.year " +
                "ORDER BY amt DESC " +
                "LIMIT 1"
        );
        maxQ.setParameter(1, institute.getId());

        Query minQ = entityManager.createNativeQuery(
            "SELECT s.year, ROUND(AVG(s.amount)) amt " +
                "FROM support_amount s " +
                "WHERE s.institute_id = ?1 " +
                "GROUP BY s.year " +
                "ORDER BY amt ASC " +
                "LIMIT 1"
        );
        minQ.setParameter(1, institute.getId());

        List<Object[]> maxRes = maxQ.getResultList();
        List<Object[]> minRes = minQ.getResultList();

        HighLowSupportAmount highLowSupportAmount = new HighLowSupportAmount();
        highLowSupportAmount.setInstitute(institute);

        YearAndAmount max = new YearAndAmount();
        max.setYear(Year.of((short) maxRes.get(0)[0]));
        max.setAmount(ObjUtil.toInt(maxRes.get(0)[1]));
        highLowSupportAmount.setHigh(max);

        YearAndAmount min = new YearAndAmount();
        min.setYear(Year.of((short) minRes.get(0)[0]));
        min.setAmount(ObjUtil.toInt(minRes.get(0)[1]));
        highLowSupportAmount.setLow(min);

        return highLowSupportAmount;
    }
}
