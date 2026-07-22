import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { CartProvider } from './context/CartContext';
import Header from './components/Header';
import Footer from './components/Footer';

import Home from './pages/Home';
import ProductList from './pages/ProductList';
import ProductDetail from './pages/ProductDetail';
import CartCheckout from './pages/CartCheckout';
import AuthPage from './pages/AuthPage';
import AdminPortal from './pages/AdminPortal';

function App() {
  return (
    <AuthProvider>
      <CartProvider>
        <Router>
          <div className="min-h-screen flex flex-col font-sans bg-primary-bg text-text-main">
            {/* The Admin portal has its own layout, so we check routes inside. But for simplicity, we let the AdminPortal render full screen over everything since it has z-50 */}
            <Header />
            <main className="flex-1 flex flex-col">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/collection/:category" element={<ProductList />} />
                <Route path="/product/:id" element={<ProductDetail />} />
                <Route path="/cart" element={<CartCheckout />} />
                <Route path="/login" element={<AuthPage />} />
                <Route path="/admin/*" element={<AdminPortal />} />
              </Routes>
            </main>
            <Footer />
          </div>
        </Router>
      </CartProvider>
    </AuthProvider>
  );
}

export default App;
