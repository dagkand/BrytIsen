import React from "react";
import "./Search.css";

const Search: React.FC<{ onSearch: (searchTerm: string) => void }> = ({
  onSearch,
}) => {
  return (
    <div className="search-container">
      <input
        type="text"
        placeholder="Søk etter leker..."
        className="search-input"
        onChange={(e) => onSearch(e.target.value)}
      />
    </div>
  );
};

export default Search;
