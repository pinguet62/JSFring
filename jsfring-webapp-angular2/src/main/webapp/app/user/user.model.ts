export class User {
    login: string;
    password: string;
    email: string;
    active: boolean;
    lastConnection: Date;
    profiles: Array<number>;
}