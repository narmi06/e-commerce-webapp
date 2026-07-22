import { createContext, useState, useEffect } from 'react';
import api from '../api/axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Check if user is logged in
    const token = localStorage.getItem('token');
    if (token) {
      // Decode token or fetch user info. For now, simple mock decode or rely on API
      // Since it's stateless JWT, we'd normally parse the JWT or hit a /me endpoint
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        setUser({ email: payload.sub, role: payload.role }); // Assumes JWT structure
      } catch (e) {
        localStorage.removeItem('token');
      }
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    const { token } = response.data;
    localStorage.setItem('token', token);
    const payload = JSON.parse(atob(token.split('.')[1]));
    setUser({ email: payload.sub, role: payload.role });
  };

  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
