import React from 'react';

const MealCard = ({ meal }) => {
  if (!meal) return null;

  return (
    <div className="meal-card">
      <div className="meal-header">
        <div className="meal-img-wrapper">
          <img src={meal.thumbnail} alt={meal.mealName} className="meal-img" />
        </div>
        <div className="meal-info">
          <h2>{meal.mealName}</h2>
          <p><strong>Category:</strong> {meal.category}</p>
          <p><strong>Cuisine:</strong> {meal.area}</p>
          <span className="ingredient-badge">🧂 {meal.ingredientCount} ingredients</span>
        </div>
      </div>

      <div className="meal-body">
        <h3>🥗 Ingredients</h3>
        <ul className="ingredient-list">
          {meal.ingredients.map((ing, idx) => (
            <li key={idx}>{ing}</li>
          ))}
        </ul>

        {meal.instructions && (
          <div className="instructions-section">
            <h3>🧾 Instructions</h3>
            <p className="instructions-text">{meal.instructions}</p>
          </div>
        )}

        <div className="card-actions">
          {meal.youtubeLink && (
            <a href={meal.youtubeLink} target="_blank" rel="noreferrer" className="yt-btn">
              ▶ Watch on YouTube
            </a>
          )}
        </div>
      </div>
    </div>
  );
};

export default MealCard;
