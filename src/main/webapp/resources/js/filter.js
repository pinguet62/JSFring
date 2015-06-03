/*
 * Javascript for "StringFilter" component.
 */

/**
 * Get the HTML tag from JSF id.
 * 
 * @param jsfId
 *            The JSF id (with ":" separators).
 */
function getComponent(jsfId) {
	return $("[id='" + jsfId + "']")
}

/**
 * Show or Hide input according to the operator type.
 * 
 * @param operatorValue
 *            The operator value.
 * @param singleValueId
 *            The "singleValue" id.
 * @param leftValueId
 *            The "leftValue" id.
 * @param rigthValueId
 *            The "rigthValue" id.
 */
function onOperatorStringFilterChange(operatorValue, singleValueId,
		leftValueId, rigthValueId) {
	/* 0 parameter */
	if (operatorValue == "IS_NULL") {
		// 1 input
		getComponent(singleValueId).hide()
		// 2 inputs
		getComponent(leftValueId).hide()
		getComponent(rigthValueId).hide()
	}
	/* 1 parameter */
	else if (operatorValue == "STARTS_WITH" || operatorValue == "CONTAINS"
			|| operatorValue == "ENDS_WITH" || operatorValue == "LIKE") {
		// 1 input
		getComponent(singleValueId).show()
		// 2 inputs
		getComponent(leftValueId).hide()
		getComponent(rigthValueId).hide()
	}
	/* Error */
	else
		alert("Operator not supported!")
}

/**
 * Show or Hide input according to the operator type.
 * 
 * @param operatorValue
 *            The operator value.
 * @param singleValueId
 *            The "singleValue" id.
 * @param leftValueId
 *            The "leftValue" id.
 * @param rigthValueId
 *            The "rigthValue" id.
 */
function onOperatorNumberFilterChange(operatorValue, singleValueId,
		leftValueId, rigthValueId) {
	/* 0 parameter */
	if (operatorValue == "IS_NULL") {
		// 1 input
		getComponent(singleValueId).hide()
		// 2 inputs
		getComponent(leftValueId).hide()
		getComponent(rigthValueId).hide()
	}
	/* 1 parameter */
	else if (operatorValue == "EQUALS_TO" || operatorValue == "GREATER_THAN"
			|| operatorValue == "LESS_THAN") {
		// 1 input
		getComponent(singleValueId).show()
		// 2 inputs
		getComponent(leftValueId).hide()
		getComponent(rigthValueId).hide()
	}
	/* 2 parameter */
	else if (operatorValue == "BETWEEN") {
		// 1 input
		getComponent(singleValueId).hide()
		// 2 inputs
		getComponent(leftValueId).show()
		getComponent(rigthValueId).show()
	}
	/* Error */
	else
		alert("Operator not supported!")
}