import axios from 'axios';

const BASE_URL = '/api/meals';

export const getSimplestMeal = async (name) => {
  const response = await axios.get(`${BASE_URL}/simplest`, {
    params: { name }
  });
  return response.data;
};
