import React, { useEffect, useState } from 'react';
import './Filter.css';
import { getAllCategories } from '../../services/CategoryService';
import { Category } from '../../services/Models';

interface FilterValues {
  numPlayers: string;
  minDuration: string;
  maxDuration: string;
  categories: Array<Category>;
}

interface FilterProps {
  onFilterApplied: (numPlayers: number, minDuration: number, maxDuration: number, categories: Array<Category>) => void;
}

const Filter: React.FC<FilterProps> = ({ onFilterApplied }) => {
  const [categories, setCategories] = useState<Array<Category>>([]);

  useEffect(() => {
    const getCategpries = async () => {
      try {
        const alllCategories = await getAllCategories();
        setCategories(alllCategories);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };
    getCategpries();
  }, [setCategories]);

  const [filters, setFilters] = useState<FilterValues>({
    numPlayers: '',
    minDuration: '',
    maxDuration: '',
    categories: Array<Category>(),
  });
  

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFilters(prevFilters => ({
      ...prevFilters,
      [name]: value
    }));
  };


  const handleCategoryChange = (categoryName: string) => {
    setFilters((prevFilters) => {
      const isCategoryIncluded = prevFilters.categories.some(category => category.name === categoryName);
  
      let updatedCategories;
      if (!isCategoryIncluded) {
        // If the category is not already included, add it to the list
        updatedCategories = [...prevFilters.categories, { name: categoryName }];
      } else {
        // If the category is already included, remove it from the list
        updatedCategories = prevFilters.categories.filter(category => category.name !== categoryName);
      }
  
      return {
        ...prevFilters,
        categories: updatedCategories,
      };
    });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const numPlayers = parseInt(filters.numPlayers) || 0;
    const minDuration = parseInt(filters.minDuration) || 0;
    const maxDuration = parseInt(filters.maxDuration) || 100;
    // No parsing needed for categories as it's already in the required format

    onFilterApplied(numPlayers, minDuration, maxDuration, filters.categories);
  };

  return (
    <form className="filter-form" onSubmit={handleSubmit}>

      <div className="filter-section">
        <label>Antall spillere:</label>
        <input type="number" name="numPlayers" value={filters.numPlayers} onChange={handleChange} />
      </div>
      <div className="filter-section">
        <label>Min varighet (minutter):</label>
        <input type="number" name="minDuration" value={filters.minDuration} onChange={handleChange} />
      </div>
      <div className="filter-section">
        <label>Max varighet (minutter):</label>
        <input type="number" name="maxDuration" value={filters.maxDuration} onChange={handleChange} />
      </div>
      <div className="filter-section categories">
        <label>Categories:</label>
        {categories.map((category, index) => {
          const isChecked = filters.categories.some((c) => c.name === category.name);
          return (
            <div className={`checkbox_item ${isChecked ? "isActive" : ""}`} key={index}
            onClick={() => handleCategoryChange(category.name)}>
              <input
                type="checkbox"
                id={`category-${index}`}
                value={category.name}
                onChange={(e) => {
                  e.stopPropagation();
                  handleCategoryChange(category.name);
                }}

                checked={isChecked}
              />
              <label htmlFor={`category-${index}`} onClick={() => handleCategoryChange}>{category.name}</label>
            </div>
          );
        })}
      </div>
      <button type="submit" className="apply-filter-button">Bruk filtre</button>
    </form>
  );
};

export default Filter;
