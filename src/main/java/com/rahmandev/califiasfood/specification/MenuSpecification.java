package com.rahmandev.califiasfood.specification;

import com.rahmandev.califiasfood.entity.Menu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MenuSpecification {
    public static Specification<Menu> getSpecification(String q) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (q != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%"));
            }
            predicates.add(cb.isNull(root.get("deleteAt")));
            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
