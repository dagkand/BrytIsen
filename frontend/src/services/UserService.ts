import { User } from "./Models";

const baseUrl = 'http://localhost:8080';

function getHeaders() {
    const headers: Headers = new Headers();
    headers.set('Content-Type', 'application/json');
    headers.set('Accept', 'application/json');
    const token = sessionStorage.getItem('token');
    headers.set('Authorization', 'Bearer ' + token);
    return headers;
}

async function registerUser(user: User): Promise<Response> {
    const request: RequestInfo = new Request(baseUrl + '/register', {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(user)
    });

    return fetch(request);
}

async function getUsers(): Promise<Array<User>> {
    try {
        const response = await fetch(baseUrl + '/users', {headers: getHeaders()});
        const data = await response.json();
        const users: Array<User> = [];
        data.forEach((user: unknown) => {
            console.log(user);
            
            const parsedUser = user as User;
            users.push(parsedUser);
        });
        return users;
    } catch (error) {
        console.error('Error retrieving users:', error);
        throw error;
    }
}

async function getUser(): Promise<User> {
    try {
        const response = await fetch(baseUrl + '/users/myProfile', {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving user:', error);
        throw error;
    }
}

async function isLoggedIn(): Promise<boolean> {
    const request: RequestInfo = new Request(baseUrl + '/users/isLoggedIn', {
        method: 'GET',
        headers: getHeaders()
    });
    const response = await fetch(request);
    const data = await response.json();
    return data;
}

async function createUser(user: User): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/users/create', {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(user)
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function deleteUser(username: string): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/users/' + username, {
        method: 'DELETE',
        headers: getHeaders()
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}



export { registerUser, getUsers, getUser, isLoggedIn, createUser, deleteUser };
