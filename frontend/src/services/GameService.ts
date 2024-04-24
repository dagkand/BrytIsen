import { Game } from "./Models";

const baseUrl = 'http://localhost:8080';

function getHeaders() {
    const headers: Headers = new Headers();
    headers.set('Content-Type', 'application/json');
    headers.set('Accept', 'application/json');
    const token = sessionStorage.getItem('token');
    headers.set('Authorization', 'Bearer ' + token);
    return headers;
}

async function getGames(): Promise<Array<Game>> {
    try {
        const response = await fetch(baseUrl + '/games', {headers: getHeaders()});
        const data = await response.json();
        const games: Array<Game> = [];
        data.forEach((game: unknown) => {
            console.log(data)
            const parsedGame = game as Game;
            games.push(parsedGame);
        });
        return games;
    } catch (error) {
        console.error('Error retrieving games:', error);
        throw error;
    }
}

async function getMyGames(): Promise<Array<Game>> {
    try {
        const response = await fetch(baseUrl + '/users/myProfile/games', {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving games:', error);
        throw error;
    }
}

async function getGamesFromList(id: number): Promise<Array<Game>> {
    try {
        const response = await fetch(baseUrl + '/lists/' + id + '/games', {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving games:', error);
        throw error;
    }
}

async function getGame(id: string): Promise<Game> {
    try {
        const response = await fetch(baseUrl + '/games/' + id, {headers: getHeaders()});
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error retrieving game:', error);
        throw error;
    }
}

async function createGame(game: Game): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/games/create', {
        method: 'POST',
        headers: getHeaders(),
        body: JSON.stringify(game)
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function updateGame(game: Game): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/games/update', {
        method: 'PATCH',
        headers: getHeaders(),
        body: JSON.stringify(game)
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function deleteGame(id: string): Promise<void> {
    const request: RequestInfo = new Request(baseUrl + '/games/delete/' + id, {
        method: 'DELETE',
        headers: getHeaders()
    });

    return fetch(request)
    .then(res => {
      console.log("got response:", res)
    });
}

async function reportGame(id: number): Promise<boolean> {
    const request: RequestInfo = new Request(baseUrl + '/games/' + id + '/report', {
        method: 'POST',
        headers: getHeaders()
    });

    const response: Response = await fetch(request);
    console.log(response);

    return response.ok;
}

async function unReportGame(id: number): Promise<boolean> {
    const request: RequestInfo = new Request(baseUrl + '/games/' + id + '/report', {
        method: 'DELETE',
        headers: getHeaders()
    });

    const response: Response = await fetch(request);
    console.log(response);

    return response.ok;
}

async function hasReportedGame(id: number): Promise<boolean> {
    const request: RequestInfo = new Request(baseUrl + '/games/' + id + '/report', {
        method: 'GET',
        headers: getHeaders()
    });
    const response: Response = await fetch(request);
    const data = await response.json();
    return data;
}

export { getGames, getMyGames, getGamesFromList, getGame, createGame, updateGame, deleteGame, reportGame, unReportGame, hasReportedGame};
