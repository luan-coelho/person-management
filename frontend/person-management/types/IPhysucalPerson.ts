export default interface IPhysicalPerson {
  name: string;
  surname: string;
  cpf: string;
  email: string;
  password: string;
  confirmPassword: string;
  birthday: Date;
  gender: string;
  phones: [];
}
