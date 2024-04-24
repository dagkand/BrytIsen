import { Category } from "./Models";

const baseUrl = 'http://localhost:8080';

function getHeaders() {
    const headers: Headers = new Headers();
    headers.set('Content-Type', 'application/json');
    headers.set('Accept', 'application/json');
    const token = sessionStorage.getItem('token');
    headers.set('Authorization', 'Bearer ' + token);
    return headers;
}

async function getCategoriesFromGame(id: string): Promise<Array<Category>> {
    try {
        const response = await fetch(baseUrl + '/games/' + id + '/categories', {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving categories:', error);
        throw error;
    }
}

async function addCategoryToGame(gameId: string, categoryName: string): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/games/' + gameId + '/categories/' + categoryName, {
        method: 'POST',
        headers: getHeaders(),
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function getAllCategories(): Promise<Array<Category>> {
    try {
        const response = await fetch(baseUrl + '/games/categories', {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving game categories:', error);
        throw error;
    }
}

export { getCategoriesFromGame, addCategoryToGame, getAllCategories };
