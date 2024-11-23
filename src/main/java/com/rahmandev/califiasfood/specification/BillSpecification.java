package com.rahmandev.califiasfood.specification;

import com.rahmandev.califiasfood.dto.request.search.SearchBillRequest;
import com.rahmandev.califiasfood.entity.Bill;
import com.rahmandev.califiasfood.entity.Customer;
import com.rahmandev.califiasfood.util.DateUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillSpecification {
    public static Specification<Bill> getSpecification(SearchBillRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Bill, Customer> customerJoin = root.join("customer");
            if (request.getQ() != null) {
                predicates.add(cb.equal(customerJoin.get("name"), request.getQ()));
            }

            if (request.getStartDate() != null || request.getEndDate() != null) {

                if (request.getStartDate() != null && request.getEndDate() != null) {

                    Date startDate = DateUtil.parseDate(request.getStartDate(), "yyyy-MM-dd");
                    Date endDate = DateUtil.parseDate(request.getEndDate(), "yyyy-MM-dd");

                    Predicate rangeDate = cb.between(root.get("billDate"), startDate, new Timestamp(endDate.getTime() + 86399000));
                    predicates.add(rangeDate);

                } else if (request.getStartDate() != null) {

                    Date date = DateUtil.parseDate(request.getStartDate(), "yyyy-MM-dd");
                    Predicate startDate = cb.greaterThanOrEqualTo(root.get("billDate"), date);
                    predicates.add(startDate);

                } else {

                    Date date = DateUtil.parseDate(request.getEndDate(), "yyyy-MM-dd");
                    Predicate startDate = cb.lessThanOrEqualTo(root.get("billDate"), date);
                    predicates.add(startDate);
                }
            }
            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
