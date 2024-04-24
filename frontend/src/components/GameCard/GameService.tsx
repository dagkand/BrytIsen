import { Game } from "../../services/Models";

export async function getGames(): Promise<Game[]> {
    try {
      const response = await fetch('http://localhost:8080/games'); //fetch api
      const data = await response.json();
      return data.map((game: Game) => ({
        id: game.id,
        name: game.name,
        // Add more attributes TODO
      }));
    } catch (error) {
      throw new Error("Failed to fetch games");
    }
  }