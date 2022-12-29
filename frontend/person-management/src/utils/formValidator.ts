export function cleanForm() {
  let inputReset: HTMLElement = document.querySelector(
    '#resetButton'
  ) as unknown as HTMLElement;
  inputReset.click();
}

export function validEmail(email: string): boolean {
  let regex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
  return regex.test(email);
}
