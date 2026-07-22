import { createContext, useState, useEffect, useContext } from 'react';
import api from '../api/axios';
import { AuthContext } from './AuthContext';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const { user } = useContext(AuthContext);
  const [cart, setCart] = useState({ items: [] });
  const [loading, setLoading] = useState(false);

  const fetchCart = async () => {
    if (!user) return;
    setLoading(true);
    try {
      const response = await api.get('/cart');
      setCart(response.data);
    } catch (error) {
      console.error("Failed to fetch cart", error);
      setCart({ items: [] });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCart();
  }, [user]);

  const addToCart = async (variantId, quantity = 1) => {
    if (!user) {
      alert("Please login first");
      return;
    }
    await api.post('/cart/add', { variantId, quantity });
    await fetchCart();
  };

  const removeFromCart = async (itemId) => {
    await api.delete(`/cart/item/${itemId}`);
    await fetchCart();
  };

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart, loading }}>
      {children}
    </CartContext.Provider>
  );
};
