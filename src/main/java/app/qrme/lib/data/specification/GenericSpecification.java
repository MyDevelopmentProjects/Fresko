package app.qrme.lib.data.specification;


import app.qrme.lib.data.config.QueryCfg;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

import static app.qrme.lib.utils.BaseConstants.QueryOperation.EQUAL;
import static app.qrme.lib.utils.BaseConstants.QueryOperation.OR;

public class GenericSpecification {

    /**
     * @param queryCfgList
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Specification<T> query(List<QueryCfg> queryCfgList, Object... value) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (!queryCfgList.isEmpty()) {
                Predicate predicate = null;
                for (QueryCfg cfg : queryCfgList) {
                    if (cfg.getNextOperation() == null) {
                        predicate = buildPredicate(root, criteriaBuilder, cfg, value);
                    } else if (cfg.getNextOperation() == OR) {
                        if (predicate != null) {
                            predicate = criteriaBuilder.or(predicate, buildPredicate(root, criteriaBuilder, cfg, value));
                        }
                    } else if (cfg.getNextOperation() == EQUAL) {
                        predicate = criteriaBuilder.and(predicate, buildPredicate(root, criteriaBuilder, cfg, value));
                    }
                }
                if (predicate != null) {
                    return criteriaBuilder.and(predicate);
                }
            }
            return null;
        };
    }

    /**
     * ვეძებ ობიექტში პარამეტრებს მრავალი დონის საშუალებით
     *
     * @param root
     * @param fieldname
     * @param <T>
     * @return
     */
    private static <T> Path<T> fetchNestedPath(Path<T> root, String fieldname) {
        String[] fields = fieldname.split("\\.");
        Path<T> result = null;
        for (String field : fields) {
            if (result == null) {
                result = root.get(field);
            } else {
                result = result.get(field);
            }
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> Predicate buildPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, QueryCfg cfg, Object... values) {
        // Path of field
        Path<T> expr = (!cfg.getField().contains(".")) ? root.get(cfg.getField()) : fetchNestedPath(root, cfg.getField());
        switch (cfg.getOperation()) {
            case IN: {
                return expr.in(values);
            }
            case NOT_IN: {
                return criteriaBuilder.not(expr.in(values));
            }
            case OR: {
                return criteriaBuilder.or(criteriaBuilder.equal(expr, values));
            }
            case NOT_EQUAL: {
                if (values[0] == null) {
                    return criteriaBuilder.isNotNull(expr);
                } else {
                    return criteriaBuilder.not(criteriaBuilder.equal(expr, values));
                }
            }
            case EQUAL: {
                if (values[0] == null) {
                    return criteriaBuilder.isNull(expr);
                } else {
                    return criteriaBuilder.equal(expr, values);
                }
            }
            case LIKE: {
                return criteriaBuilder.like(
                        criteriaBuilder.lower((Expression) expr.as(String.class)),
                        "%" + values[0].toString().toLowerCase() + "%"
                );
            }
            case LESS_THAN: {
                return criteriaBuilder.lessThan((Expression) expr, (Comparable) values[0]);
            }
            case LESS_THAN_OR_EQUAL: {
                return criteriaBuilder.lessThanOrEqualTo((Expression) expr, (Comparable) values[0]);
            }
            case GREATER_THAN: {
                return criteriaBuilder.greaterThan((Expression) expr, (Comparable) values[0]);
            }
            case GREATER_THAN_OR_EQUAL: {
                return criteriaBuilder.greaterThanOrEqualTo((Expression) expr, (Comparable) values[0]);
            }
            case BETWEEN: {
                if (values.length == 2) {
                    return criteriaBuilder.between((Expression) expr, (Comparable) values[0], (Comparable) values[1]);
                }
            }
            default:
                return null;
        }
    }

}