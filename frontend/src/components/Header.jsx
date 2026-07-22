import { Link } from 'react-router-dom';
import { ShoppingCart, User } from 'lucide-react';
import { useContext } from 'react';
import { CartContext } from '../context/CartContext';
import { AuthContext } from '../context/AuthContext';

export default function Header() {
  const { cart } = useContext(CartContext);
  const { user } = useContext(AuthContext);

  const cartItemCount = cart?.items?.reduce((total, item) => total + item.quantity, 0) || 0;

  return (
    <header className="sticky top-0 z-50 bg-primary-bg/90 backdrop-blur-md border-b border-secondary-bg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-20">
          <div className="flex-shrink-0">
            <Link to="/" className="font-display font-extrabold text-3xl tracking-tighter hover:text-accent-1 transition-colors">
              SILKSONG
            </Link>
          </div>
          <nav className="hidden md:flex space-x-8">
            <Link to="/collection/work" className="text-text-main hover:text-accent-1 font-medium transition-colors">Work Collection</Link>
            <Link to="/collection/party" className="text-text-main hover:text-accent-1 font-medium transition-colors">Party Collection</Link>
            <Link to="/collection/bridal" className="text-text-main hover:text-accent-1 font-medium transition-colors">Bridal Collection</Link>
            <Link to="/collection/special" className="text-text-main hover:text-accent-1 font-medium transition-colors">Special Occasions</Link>
            <Link to="/contact" className="text-text-main hover:text-accent-1 font-medium transition-colors">Contact Us</Link>
          </nav>
          <div className="flex items-center space-x-6">
            <Link to={user ? "/profile" : "/login"} className="text-text-main hover:text-accent-1 transition-colors">
              <User className="h-6 w-6" />
            </Link>
            <Link to="/cart" className="text-text-main hover:text-accent-1 transition-colors relative">
              <ShoppingCart className="h-6 w-6" />
              {cartItemCount > 0 && (
                <span className="absolute -top-2 -right-2 bg-accent-1 text-text-main text-xs font-bold rounded-full h-5 w-5 flex items-center justify-center">
                  {cartItemCount}
                </span>
              )}
            </Link>
          </div>
        </div>
      </div>
    </header>
  );
}
