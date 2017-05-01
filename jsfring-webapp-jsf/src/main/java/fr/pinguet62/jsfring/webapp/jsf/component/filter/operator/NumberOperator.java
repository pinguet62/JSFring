package fr.pinguet62.jsfring.webapp.jsf.component.filter.operator;

import com.querydsl.core.types.dsl.NumberExpression;

public interface NumberOperator<T extends Number & Comparable<?>> extends Operator<NumberExpression<T>, T> {
}