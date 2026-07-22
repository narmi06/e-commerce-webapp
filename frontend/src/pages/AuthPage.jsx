import { useState, useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';

export default function AuthPage() {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({ email: '', password: '', firstName: '', lastName: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isLogin) {
        await login(formData.email, formData.password);
      } else {
        await api.post('/auth/register', formData);
        await login(formData.email, formData.password);
      }
      navigate('/');
    } catch (error) {
      alert("Authentication failed");
    }
  };

  return (
    <div className="flex-1 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-secondary-bg/30">
      <div className="max-w-md w-full space-y-8 bg-white p-8 rounded-2xl shadow-sm border border-secondary-bg">
        <div>
          <h2 className="mt-6 text-center text-3xl font-extrabold text-text-main font-display">
            {isLogin ? 'Sign in to your account' : 'Create an account'}
          </h2>
        </div>
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="space-y-4">
            {!isLogin && (
              <div className="flex gap-4">
                <input
                  type="text"
                  required
                  placeholder="First Name"
                  className="w-full px-4 py-2 border border-secondary-bg rounded-md focus:ring-accent-1 focus:border-accent-1"
                  value={formData.firstName}
                  onChange={e => setFormData({...formData, firstName: e.target.value})}
                />
                <input
                  type="text"
                  required
                  placeholder="Last Name"
                  className="w-full px-4 py-2 border border-secondary-bg rounded-md focus:ring-accent-1 focus:border-accent-1"
                  value={formData.lastName}
                  onChange={e => setFormData({...formData, lastName: e.target.value})}
                />
              </div>
            )}
            <input
              type="email"
              required
              placeholder="Email address"
              className="w-full px-4 py-2 border border-secondary-bg rounded-md focus:ring-accent-1 focus:border-accent-1"
              value={formData.email}
              onChange={e => setFormData({...formData, email: e.target.value})}
            />
            <input
              type="password"
              required
              placeholder="Password"
              className="w-full px-4 py-2 border border-secondary-bg rounded-md focus:ring-accent-1 focus:border-accent-1"
              value={formData.password}
              onChange={e => setFormData({...formData, password: e.target.value})}
            />
          </div>

          <div>
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium bg-accent-1 hover:bg-accent-1/90 focus:outline-none"
            >
              {isLogin ? 'Sign in' : 'Sign up'}
            </button>
          </div>
        </form>
        
        <div className="text-center">
          <button 
            onClick={() => setIsLogin(!isLogin)} 
            className="text-sm font-medium text-text-main/70 hover:text-accent-1 transition-colors"
          >
            {isLogin ? "Don't have an account? Sign up" : "Already have an account? Sign in"}
          </button>
        </div>
      </div>
    </div>
  );
}
