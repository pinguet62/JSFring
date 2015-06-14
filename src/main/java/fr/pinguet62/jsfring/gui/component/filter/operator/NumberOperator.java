package fr.pinguet62.jsfring.gui.component.filter.operator;

import com.mysema.query.types.expr.NumberExpression;

public interface NumberOperator<T extends Number & Comparable<?>> extends
Operator<NumberExpression<T>, T> {}