import { List } from "./Models";

const baseUrl = 'http://localhost:8080';

function getHeaders() {
    const headers: Headers = new Headers();
    headers.set('Content-Type', 'application/json');
    headers.set('Accept', 'application/json');
    const token = sessionStorage.getItem('token');
    headers.set('Authorization', 'Bearer ' + token);
    return headers;
}

async function getLists(): Promise<Array<List>> {
    try {
        const response = await fetch(baseUrl + '/lists', {headers: getHeaders()});
        const data = await response.json();
        const lists: Array<List> = [];
        data.forEach((list: unknown) => {
            console.log(list);
            
            const parsedList = list as List;
            lists.push(parsedList);
        });
        return lists;
    } catch (error) {
        console.error('Error retrieving lists:', error);
        throw error;
    }
}

async function getMyLists(): Promise<Array<List>> {
    try {
        const response = await fetch(baseUrl + '/users/myProfile/lists', {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving lists:', error);
        throw error;
    }
}

async function getList(id: number): Promise<List> {
    try {
        const response = await fetch(baseUrl + '/lists/' + id, {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving list:', error);
        throw error;
    }
}

async function viewList(id: number): Promise<List> {
    try {
        const request: RequestInfo = new Request(baseUrl + '/lists/' + id + "/view", {
            method: 'GET',
            headers: getHeaders(),
        });
        const response = await fetch(request);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving list:', error);
        throw error;
    }
}

async function createList(list: List): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/lists/create', {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(list)
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function updateList(list: List): Promise<void> {
    new Request(baseUrl + '/lists/' + list.id, {
        method: 'PATCH',
        headers: getHeaders(),
        body: JSON.stringify(list)
    });
}

async function deleteList(id: number): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/lists/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });

    const response = await fetch(request);
    if (response.status !== 200) {
        throw new Error('Cant delete this list');
    }

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function addGameToList(listId: number, gameId: number): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/lists/' + listId + '/' + gameId, {
        method: 'POST',
        headers: getHeaders()
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function updateListOrder(listId: number, gameIdsInOrder: Array<number>): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/lists/' + listId + '/order', {
        method: 'PUT',
        headers: getHeaders(),
        body: JSON.stringify(gameIdsInOrder)
    });
    fetch(request)
}

async function removeGameFromList(listId: number, gameId: number): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/lists/' + listId + '/' + gameId, {
        method: 'DELETE',
        headers: getHeaders()
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

export { getLists, getMyLists, getList, viewList, createList, updateList, deleteList, addGameToList, updateListOrder, removeGameFromList};