package com.rahmandev.califiasfood.specification;

import com.rahmandev.califiasfood.entity.Discount;
import com.rahmandev.califiasfood.entity.Menu;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiscountSpecification {
    private static final Logger log = LoggerFactory.getLogger(DiscountSpecification.class);

    public static Specification<Discount> getSpecification(String q) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Discount, Menu> menuJoin = root.join("menu");
            // if (q != null) {
            //     predicates.add(cb.like(cb.lower(menuJoin.get("name")), "%" + q.toLowerCase() + "%"));
            // }

            predicates.add(cb.isNull(menuJoin.get("deleteAt")));
            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
