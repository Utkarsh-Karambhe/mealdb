import React, { useState } from 'react';
import MealCard from './components/MealCard';
import { getSimplestMeal } from './services/api';
import './App.css';

function App() {
  const [searchTerm, setSearchTerm] = useState('');
  const [meal, setMeal]             = useState(null);
  const [loading, setLoading]       = useState(false);
  const [error, setError]           = useState('');

  const handleSearch = async () => {
    if (!searchTerm.trim()) return;
    setLoading(true);
    setError('');
    setMeal(null);
    try {
      const data = await getSimplestMeal(searchTerm);
      setMeal(data);
    } catch {
      setError('No meals found. Try: pasta, chicken, arrabiata');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <h1>🍽️ MealDB — Simplest Meal Finder</h1>
      <p className="subtitle">Find the meal with the fewest ingredients</p>

      <div className="search-bar">
        <input
          type="text"
          placeholder="Search meals (e.g. pasta, chicken, arrabiata)"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
        />
        <button onClick={handleSearch} disabled={loading}>
          {loading ? 'Searching...' : '🔍 Find'}
        </button>
      </div>

      {loading && (
        <div className="loading-container">
          <div className="spinner"></div>
          <p>Finding the simplest meal...</p>
        </div>
      )}

      {error && (
        <div className="error-container">
          <p className="error">
            <span className="error-icon">⚠️</span>
            {error}
          </p>
        </div>
      )}

      {meal && <MealCard meal={meal} />}
    </div>
  );
}

export default App;
