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
            "SELECT " +
                "s.year, s.institute, SUM(s.amount) " +
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
}
