export function booleanFormatter(boolean) {
	return boolean ? 'Yes' : 'No';
}

export function dateFormatter(date) {
	return new Intl.DateTimeFormat().format(new Date(date));
}

export function numberFormatter(number) {
	return new Intl.NumberFormat().format(number);
}
