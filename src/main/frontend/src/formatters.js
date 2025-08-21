export function booleanFormatter(boolean) {
	return boolean ? 'Yes' : 'No';
}

export function dateFormatter(date) {
	return new Intl.DateTimeFormat("da-DK").format(new Date(date));
}

export function numberFormatter(number) {
	return new Intl.NumberFormat("da-DK").format(number);
}
