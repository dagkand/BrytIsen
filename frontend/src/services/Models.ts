export interface Game {
    id?: number;
    name?: string;
    description?: string;
    rules?: string;
    emoji?: string;
    players_min: number;
    players_max: number;
    rating?: number;
    duration_min: number;
    duration_max: number;
    reviewCount: number;
    reportCount: number;
    categories?: Array<Category>;
}

export interface List {
    name?: string;
    id: number;
}

export interface User {
    userName?: string;
    email?: string;
}

export interface Review {
    game?: Game;
    gameId?: number;
    user?: User;
    userName?: string;
    description?: string;
    stars?: number;
    createdAt?: string;
 }

export interface Category {
    name: string;
}